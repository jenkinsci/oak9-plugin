package io.jenkins.plugins.oak9;

import com.cloudbees.plugins.credentials.CredentialsProvider;
import com.cloudbees.plugins.credentials.common.*;
import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.EnvVars;
import hudson.Launcher;
import hudson.Extension;
import hudson.FilePath;
import hudson.remoting.VirtualChannel;
import hudson.util.FormValidation;
import hudson.model.AbstractProject;
import hudson.model.Result;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.tasks.Builder;
import hudson.tasks.BuildStepDescriptor;
import hudson.util.ListBoxModel;
import hudson.security.ACL;
import io.jenkins.plugins.oak9.model.ApiResponse;
import io.jenkins.plugins.oak9.model.DesignGap;
import io.jenkins.plugins.oak9.model.ValidationResult;
import io.jenkins.plugins.oak9.model.Violation;
import io.jenkins.plugins.oak9.utils.*;
import jenkins.model.Jenkins;
import okhttp3.OkHttpClient;
import org.apache.commons.lang.WordUtils;
import org.jenkinsci.plugins.plaincredentials.StringCredentials;
import org.jetbrains.annotations.NotNull;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.QueryParameter;
import com.cloudbees.plugins.credentials.CredentialsMatchers;
import static com.cloudbees.plugins.credentials.domains.URIRequirementBuilder.fromUri;
import javax.servlet.ServletException;
import java.io.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import jenkins.tasks.SimpleBuildStep;
import org.kohsuke.stapler.verb.POST;
import org.w3c.dom.Document;

public class Oak9Builder extends Builder implements SimpleBuildStep {

    /**
     * Oak9 org ID
     */
    private String orgId;

    /**
     * Oak9 Project ID
     */
    private String projectId;

    /**
     * Jenkins credentials that store the Oak9 API Key
     */
    private String credentialsId;

    /**
     * Max severity before failure
     */
    private int maxSeverity;

    /**
     * Base URL to use for oak9 API
     */
    private String baseUrl;

    /**
     * Constructor is setup by Jenkins when it instantiates the plugin
     *
     * @param orgId the oak9-provided org ID
     * @param projectId the oak9-provided project ID
     * @param credentialsId the ID to use to fetch the oak9 API Key from Jenkins secrets
     * @param maxSeverity the severity at which the job will fail (at or above)
     */
    @DataBoundConstructor
    public Oak9Builder(String orgId, String projectId, String credentialsId, int maxSeverity, String baseUrl) {
        this.orgId = orgId;
        this.projectId = projectId;
        this.credentialsId = credentialsId;
        this.maxSeverity = maxSeverity;
        this.baseUrl = baseUrl;
    }

    /**
     * Sets the oak9 Organization ID for the runner
     * @param orgId the oak9-provided org ID
     */
    @DataBoundSetter
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    /**
     * Sets the oak9 project ID for the runner
     * @param projectId the oak9-provided project ID
     */
    @DataBoundSetter
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    /**
     * Sets the credentials ID to be used for the runner
     * @param credentialsId a string for the credentials ID
     */
    @DataBoundSetter
    public void setCredentialsId(String credentialsId) {
        this.credentialsId = credentialsId;
    }

    /**
     * Sets the severity at which the job will fail
     * @param maxSeverity an integer representing the max severity refer to io.jenkins.plugins.oak9.utils.Severity
     */
    @DataBoundSetter
    public void setMaxSeverity(int maxSeverity) {
        this.maxSeverity = maxSeverity;
    }

    /**
     * Sets the BaseURL for communication with the oak9 API
     * @param baseUrl the base URL to use
     */
    @DataBoundSetter
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * Returns the current base URL
     * @return
     */
    public String getBaseUrl() {
        if (this.baseUrl == null) {
            return "https://api.oak9.io/integrations";
        }
        return this.baseUrl;
    }

    /**
     * Fetches the user-specified oak9 Organization ID
     * @return the org ID
     */
    public String getOrgId(){
        return this.orgId;
    }

    /**
     * Fetches the Jenkins Project ID
     * @return the Jenkins Project ID
     */
    public String getProjectId() {
        return this.projectId;
    }

    /**
     * Fetches the credentials ID selected for the job
     * @return The credentials ID selected for the job
     */
    public String getCredentialsId() {
        return this.credentialsId;
    }

    /**
     * The severity at or above which the job will fail
     * @return the user-provided max severity
     */
    public int getMaxSeverity() {
        return this.maxSeverity;
    }

    /**
     * Generate an API client to inject
     *
     * @param run the Jenkins run
     * @param taskListener the Jenkins task listener for logging
     * @return a ready-to-use http client
     */
    public Oak9ApiClient generateHttpClient(Run<?, ?> run, TaskListener taskListener) {
        return new Oak9ApiClient(
                this.getBaseUrl(),
                getCredentials(run, this.getCredentialsId()).getSecret().getPlainText(),
                this.orgId,
                this.projectId,
                taskListener,
                new OkHttpClient.Builder()
                        .connectTimeout(5, TimeUnit.SECONDS)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .build());
    }

    /**
     * Jenkins plugin entry point.
     *
     * @param run the current Jenkins build
     * @param workspace FilePath representing the Jenkins workspace for this project
     * @param env Environment variables
     * @param launcher the Jenkins launcher
     * @param taskListener the Jenkins task listener, used primarily for logging and setting task status
     * @throws IOException Thrown in the event of a permanent error
     */
    @Override
    public void perform(
            @NonNull Run<?, ?> run,
            @NonNull FilePath workspace,
            @NonNull EnvVars env,
            @NonNull Launcher launcher,
            @NonNull TaskListener taskListener
    ) throws IOException, InterruptedException {
        long zipTimestamp = System.currentTimeMillis() / 1000L;
        String zipOutputFile = "oak9-" + zipTimestamp + ".zip";
        taskListener.getLogger().println("Packaging IaC files for oak9...\n");
        ByteArrayOutputStream zipFile = new ByteArrayOutputStream();
        workspace.zip(zipFile, new IacExtensionFilter());

        if (zipFile.size() == 0) {
            taskListener.error("Unable to generate zip file: " + zipOutputFile + ". Aborting.\n");
            run.setResult(Result.FAILURE);
            throw new IOException();
        } else {
            taskListener.getLogger().println("Zip file generated with size: " + zipFile.size() + "\n");
        }

        // Make request to oak9 API to push zip file
        taskListener.getLogger().print("Sending IaC files to oak9...\n");
        Oak9ApiClient client = generateHttpClient(run, taskListener);
        ValidationResult postFileResult;
        try {
            postFileResult = client.postFileValidation(zipOutputFile, zipFile);
        } catch (Exception e) {
            taskListener.error(e.getMessage());
            run.setResult(Result.FAILURE);
            return;
        }

        // Check status endpoint
        if (!postFileResult.getStatus().toLowerCase().equals("queued") && !postFileResult.getStatus().toLowerCase().equals("completed")) {
            taskListener.error("Unexpected status: " + postFileResult.getStatus() + " from oak9 API");
            run.setResult(Result.FAILURE);
            throw new IOException();
        }
        taskListener.getLogger().print("Files in status: " + postFileResult.getStatus() + " with requestId: " + postFileResult.getRequestId() + "\n");
        taskListener.getLogger().print("Waiting for oak9 analysis for Request ID " + postFileResult.getRequestId() + "...\n");

        ApiResponse apiResponse;
        ValidationResult statusResult;
        try {
            apiResponse = client.pollStatus(postFileResult);
            statusResult = apiResponse.getResult();
        } catch (Exception e) {
            taskListener.error(e.getMessage());
            run.setResult(Result.FAILURE);
            return;
        }

        // Analyze Results
        taskListener.getLogger().print("Analyzing oak9 scan results for Request ID " + statusResult.getRequestId() + "...\n");

        if (maxSeverity == 0) {
            taskListener.getLogger().println("Severity set to `" + Severity.getTextForSeverityLevel(maxSeverity) + "`, oak9 Runner will always succeed\n");
        } else {
            taskListener.getLogger().println("Scanning Design Gaps for severity `" + Severity.getTextForSeverityLevel(maxSeverity) + "` or higher...\n");
        }

        DesignGapCounter designGapCounter = new DesignGapCounter();
        if (statusResult.getDesignGaps().size() > 0) {
            for (DesignGap designGap : statusResult.getDesignGaps()) {
                int maxFoundSeverity = 0;
                for (Violation violation : designGap.getViolations()) {
                    try {
                        int currentFoundSeverity = Severity.getIntegerForSeverityText(violation.getSeverity().toLowerCase());
                        if (currentFoundSeverity > maxFoundSeverity) {
                            maxFoundSeverity = currentFoundSeverity;
                        }
                    } catch (Exception e) {
                        taskListener.error(e.getMessage());
                    }
                    if (Severity.exceedsSeverity(this.maxSeverity, violation.getSeverity())) {
                        run.setResult(Result.FAILURE);
                    }
                }
                if (maxFoundSeverity > 0) {
                    designGapCounter.trackDesignGapCounts(maxFoundSeverity);
                }
            }

            taskListener.getLogger().println("Writing Design Gaps to artifacts/design_gaps.xml\n");
            try {
                String designGapXmlDocument = ArtifactGenerator.generateDesignGapXmlDocument(statusResult.getDesignGaps());
                FilePath xmlOutputPath;
                if (workspace.isRemote()) {
                    VirtualChannel ch = workspace.getChannel();
                    xmlOutputPath = new FilePath(ch, workspace.getRemote() + "/artifacts/design_gaps.xml");
                } else {
                    xmlOutputPath = new FilePath(new File(workspace.getRemote() + "/artifacts/design_gaps.xml"));
                }
                xmlOutputPath.write(designGapXmlDocument, "utf-8");
            } catch (Exception e) {
                taskListener.error(e.getMessage());
            }
        }

        String designGapMessage = "Design Gap(s) found: " +
                "Critical " + designGapCounter.getCountForSeverity("critical") + "; " +
                "High " + designGapCounter.getCountForSeverity("high") + "; " +
                "Moderate " + designGapCounter.getCountForSeverity("moderate") + "; " +
                "Low " + designGapCounter.getCountForSeverity("low");

        if (run.getResult() == Result.FAILURE) {
            taskListener.error("Design Gap(s) found at severity `" + Severity.getTextForSeverityLevel(maxSeverity) + "` or higher.");
            taskListener.error(designGapMessage);
            taskListener.getLogger().println("For more details, browse to " + apiResponse.getResultsUrl() + "\n");
            taskListener.getLogger().println("oak9 Runner Failed. Stopping Build Progress.\n");
        } else {
            run.setResult(Result.SUCCESS);
            if (maxSeverity > 0) {
                taskListener.getLogger().println("No Design Gap(s) found at severity `" + Severity.getTextForSeverityLevel(maxSeverity) + "` or higher.");
            }
            taskListener.getLogger().println(designGapMessage);
            taskListener.getLogger().println("For more details, browse to " + apiResponse.getResultsUrl() + "\n");
            taskListener.getLogger().println("oak9 Runner Passed\n");
        }
    }

    /**
     * Fetch Jenkins credentials by CredentialId
     *
     * @param run The jenkins run
     * @param credentialsId the ID of the credentials to be fetched
     * @return StringCredentials
     */
    private static StringCredentials getCredentials(Run<?, ?> run, String credentialsId) {
        return CredentialsProvider.findCredentialById(credentialsId, StringCredentials.class, run);
    }

    @Extension
    public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {

        /**
         * Check to make sure the user has entered an Org Id
         *
         * @param value the string value of the input being checked
         * @return FormValidation
         * @throws IOException thrown by the Jenkins core
         * @throws ServletException thrown by the Jenkins core
         */
        public FormValidation doCheckOrgId(@QueryParameter String value)
                throws IOException, ServletException {
            if (value.length() == 0)
                return FormValidation.error(Messages.Oak9Builder_DescriptorImpl_errors_missingOrgId());
            return FormValidation.ok();
        }

        /**
         * Check to make sure the user has not unset the BaseURL
         *
         * @param value the string value of the input being checked
         * @return FormValidation
         * @throws IOException thrown by the Jenkins core
         * @throws ServletException thrown by the Jenkins core
         */
        public FormValidation doCheckBaseUrl(@QueryParameter String value)
                throws IOException, ServletException {
            if (value.length() == 0)
                return FormValidation.error(Messages.Oak9Builder_DescriptorImpl_errors_missingBaseUrl());
            return FormValidation.ok();
        }

        /**
         * Check to make sure the user has entered a Project Id
         *
         * @param value the string value of the input being checked
         * @return FormValidation
         * @throws IOException thrown by Jenkins core
         * @throws ServletException thrown by Jenkins core
         */
        public FormValidation doCheckProjectId(@QueryParameter String value)
                throws IOException, ServletException {
            if (value.length() == 0)
                return FormValidation.error(Messages.Oak9Builder_DescriptorImpl_errors_missingProjectId());
            return FormValidation.ok();
        }

        /**
         * Check to make sure the user has selected a credential
         *
         * @param value The string  value of the input being checked
         * @return FormValidation
         * @throws IOException thrown by Jenkins core
         * @throws ServletException thrown by Jenkins core
         */
        public FormValidation doCheckCredentialsId(@QueryParameter String value)
                throws IOException, ServletException {
            if (value.length() == 0)
                return FormValidation.error(Messages.Oak9Builder_DescriptorImpl_errors_missingKey());
            return FormValidation.ok();
        }

        /**
         * Fill the select list with available Oak9 Severities
         *
         * @return ListBoxModel
         */
        @POST
        public ListBoxModel doFillMaxSeverityItems() {
            // Sort items by severity (descending).
            List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(Severity.getSeverities().entrySet());
            Collections.sort(list, (i1, i2) -> i2.getValue().compareTo(i1.getValue()));

            ListBoxModel items = new ListBoxModel();
            for (Map.Entry<String,Integer> severity : list) {
                items.add(WordUtils.capitalize(severity.getKey()), severity.getValue().toString());
            }
            return items;
        }

        /**
         * Fill the select list with permissible credentials
         *
         * @param serverUrl the jenkins Server url
         * @param credentialsId - the credentials ID that is currently selected
         * @return
         */
        @POST
        public ListBoxModel doFillCredentialsIdItems(@QueryParameter String serverUrl,
                                                     @QueryParameter String credentialsId) {
            Jenkins jenkins = Jenkins.get();
            if (!jenkins.hasPermission(Jenkins.ADMINISTER)) {
                return new StandardListBoxModel().includeCurrentValue(credentialsId);
            }
            return new StandardListBoxModel()
                    .includeEmptyValue()
                    .includeMatchingAs(ACL.SYSTEM,
                            jenkins,
                            StandardCredentials.class,
                            fromUri(serverUrl).build(),
                            CredentialsMatchers.always()
                    );
        }

        @Override
        public boolean isApplicable(Class<? extends AbstractProject> aClass) {
            return true;
        }

        @NotNull
        @Override
        public String getDisplayName() {
            return Messages.Oak9Builder_DescriptorImpl_DisplayName();
        }

        public String defaultBaseUrl() {
            return "https://api.oak9.io/integrations";
        }

    }

}