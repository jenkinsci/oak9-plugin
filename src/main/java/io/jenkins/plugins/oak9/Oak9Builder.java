package io.jenkins.plugins.oak9;

import com.cloudbees.plugins.credentials.CredentialsProvider;
import com.cloudbees.plugins.credentials.common.*;
import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.EnvVars;
import hudson.Launcher;
import hudson.Extension;
import hudson.FilePath;
import hudson.util.FormValidation;
import hudson.model.AbstractProject;
import hudson.model.Result;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.tasks.Builder;
import hudson.tasks.BuildStepDescriptor;
import hudson.util.ListBoxModel;
import hudson.security.ACL;
import io.jenkins.plugins.oak9.model.DesignGap;
import io.jenkins.plugins.oak9.model.ValidationResult;
import io.jenkins.plugins.oak9.model.Violation;
import io.jenkins.plugins.oak9.utils.*;
import jenkins.model.Jenkins;
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
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import jenkins.tasks.SimpleBuildStep;

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

    private final Map<String, Integer> designGapViolationCounter;


    /**
     * Constructor is setup by Jenkins when it instantiates the plugin
     *
     * @param orgId the oak9-provided org ID
     * @param projectId the oak9-provided project ID
     * @param credentialsId the ID to use to fetch the oak9 API Key from Jenkins secrets
     * @param maxSeverity the severity at which the job will fail (at or above)
     */
    @DataBoundConstructor
    public Oak9Builder(String orgId, String projectId, String credentialsId, int maxSeverity) {
        this.orgId = orgId;
        this.projectId = projectId;
        this.credentialsId = credentialsId;
        this.maxSeverity = maxSeverity;
        this.designGapViolationCounter = new HashMap<String, Integer>() {{
            put("critical", 0);
            put("high", 0);
            put("moderate", 0);
            put("low", 0);
        }};
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

        FilePath zipPath;
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
        StringCredentials oak9key = getCredentials(run, this.getCredentialsId());
        oak9ApiClient client = new oak9ApiClient(
                "https://devapi.oak9.cloud/integrations",
                oak9key.getSecret().getPlainText(),
                this.orgId,
                this.projectId,
                taskListener);
        ValidationResult postFileResult = client.postFileValidation(zipOutputFile, zipFile);

        // Check status endpoint
        if (!postFileResult.getStatus().toLowerCase().equals("queued") && !postFileResult.getStatus().toLowerCase().equals("completed")) {
            taskListener.error("Unexpected status: " + postFileResult.getStatus() + " from oak9 API");
            run.setResult(Result.FAILURE);
            throw new IOException();
        }
        taskListener.getLogger().print("Files in status: " + postFileResult.getStatus() + " with requestId: " + postFileResult.getRequestId() + "\n");
        taskListener.getLogger().print("Waiting for oak9 analysis for Request ID " + postFileResult.getRequestId() + "...\n");
        ValidationResult statusResult = client.pollStatus(postFileResult);

        // Analyze Results
        taskListener.getLogger().print("Analyzing oak9 scan results for Request ID " + statusResult.getRequestId() + "...\n");
        if (maxSeverity > 0) {
            taskListener.getLogger().println("Scanning Design Gaps for severity `" + Severity.getTextForSeverityLevel(maxSeverity) + "` or higher...\n");
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
                    trackDesignGapCounts(maxFoundSeverity);
                }
            }
        }

        if (run.getResult() == Result.FAILURE) {
            taskListener.error(
                    "Design Gap(s) found: " +
                            "Critical " + designGapViolationCounter.get("critical") + "; " +
                            "High " + designGapViolationCounter.get("high") + "; " +
                            "Moderate " + designGapViolationCounter.get("moderate") + "; " +
                            "Low " + designGapViolationCounter.get("low"));
            taskListener.getLogger().println("oak9 Runner Failed. Stopping Build Progress.\n");
        } else {
            run.setResult(Result.SUCCESS);
            taskListener.getLogger().println("oak9 Runner Complete\n");
        }
    }

    /**
     * Keeps counts of different severity violations from an Oak9 scan result
     *
     * @param severityLevel the Violation object from which we are analyzing the severity
     */
    private void trackDesignGapCounts(int severityLevel) {
        String key = Severity.getTextForSeverityLevel(severityLevel).toLowerCase();
        designGapViolationCounter.replace(
                key,
                designGapViolationCounter.get(key) + 1
        );
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
        public ListBoxModel doFillMaxSeverityItems() {
            ListBoxModel items = new ListBoxModel();
            for (Map.Entry<String,Integer> severity : Severity.severities.entrySet()) {
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

    }

}