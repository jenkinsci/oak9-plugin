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
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.QueryParameter;
import com.cloudbees.plugins.credentials.CredentialsMatchers;
import static com.cloudbees.plugins.credentials.domains.URIRequirementBuilder.fromUri;
import javax.servlet.ServletException;
import java.io.*;
import java.util.Collection;
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

    /**
     * Constructor is setup by Jenkins when it instantiates the plugin
     *
     * @param orgId
     * @param projectId
     * @param credentialsId
     * @param maxSeverity
     */
    @DataBoundConstructor
    public Oak9Builder(String orgId, String projectId, String credentialsId, int maxSeverity) {
        this.orgId = orgId;
        this.projectId = projectId;
        this.credentialsId = credentialsId;
        this.maxSeverity = maxSeverity;
    }

    @DataBoundSetter
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    @DataBoundSetter
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    @DataBoundSetter
    public void setCredentialsId(String credentialsId) {
        this.credentialsId = credentialsId;
    }

    @DataBoundSetter
    public void setMaxSeverity(int maxSeverity) {
        this.maxSeverity = maxSeverity;
    }

    public String getOrgId(){
        return this.orgId;
    }

    public String getProjectId() {
        return this.projectId;
    }

    public String getCredentialsId() {
        return this.credentialsId;
    }

    public int getMaxSeverity() {
        return this.maxSeverity;
    }

    public FilePath createZipFile(FilePath workspace) {
        return workspace;
    }

    /**
     * Jenkins plugin entry point.
     *
     * @param run
     * @param workspace
     * @param env
     * @param launcher
     * @param taskListener
     * @throws InterruptedException
     * @throws IOException
     */
    @Override
    public void perform(
                        @NonNull Run<?, ?> run,
                        @NonNull FilePath workspace,
                        @NonNull EnvVars env,
                        @NonNull Launcher launcher,
                        @NonNull TaskListener taskListener
    ) throws InterruptedException, IOException {
        // Find list of IaC files
        Collection<File> IacFiles = FileScanner.scanForIacFiles(workspace.absolutize(), new IacExtensionFilter());
        if (IacFiles.size() == 0) {
            throw new IOException("No IaC files could be found!\n");
        }

        // Zip Files
        long zipTimestamp = System.currentTimeMillis() / 1000L;
        String zipOutputFile = "oak9-" + zipTimestamp + ".zip";
        taskListener.getLogger().println("Packaging IaC files for oak9...\n");
        FileArchiver archiver = new FileArchiver(workspace.absolutize(), IacFiles, zipOutputFile);
        archiver.zipFiles(workspace.absolutize().toString());

        String zipFilePath = workspace.absolutize() + File.separator + zipOutputFile;
        File zipFile = new File(zipFilePath);
        if (!zipFile.exists() || zipFile.length() == 0) {
            throw new InterruptedException("Unable to generate zip file: " + zipFilePath +". Aborting.\n");
        } else {
            taskListener.getLogger().println("Zip file generated with size: " + zipFile.length() + "\n");
        }

        // Make request to oak9 API to push zip file
        taskListener.getLogger().print("Sending IaC files to oak9...\n");
        StringCredentials oak9key = getCredentials(run, this.getCredentialsId());
        oak9ApiClient client = new oak9ApiClient("https://devconsole-api.oak9.cloud/integrations", oak9key.getSecret().getPlainText(), this.orgId, this.projectId, taskListener);
        ValidationResult postFileResult = client.postFileValidation(zipFile);

        // Check status endpoint
        if (!postFileResult.getStatus().toLowerCase().equals("queued") && !postFileResult.getStatus().toLowerCase().equals("completed")){
            throw new InterruptedException("Unexpected status: " + postFileResult.getStatus() + " from oak9 API");
        }
        taskListener.getLogger().print("Files in status: " + postFileResult.getStatus() + " with requestId: " + postFileResult.getRequestId() + "\n");
        taskListener.getLogger().print("Waiting for oak9 analysis for Request ID " + postFileResult.getRequestId() + "...\n");
        ValidationResult statusResult = client.pollStatus(postFileResult);

        // Analyze Results
        taskListener.getLogger().print("Analyzing oak9 scan results for Request ID " + statusResult.getRequestId() + "...\n");
        if (maxSeverity > 0) {
            for (DesignGap designGap : statusResult.getDesignGaps()) {
                for (Violation violation : designGap.getViolations()) {
                    taskListener.getLogger().println("Scanning Design Gaps for severity `" + this.maxSeverity + "` or higher...\n");
                    if (Severity.exceedsSeverity(this.maxSeverity, violation.getSeverity())) {
                        throw new IOException("Design Gap found with severity at or above " + this.maxSeverity + "\n");
                    } else {
                        taskListener.getLogger().println("Design Gap with Severity " + violation.getOak9Severity() + "\n");
                    }
                }
            }
        }

        taskListener.getLogger().println("oak9 Runner Complete\n");
    }

    /**
     * Fetch Jenkins credentials by CredentialId
     *
     * @param run
     * @param credentialsId
     * @return
     */
    public static StringCredentials getCredentials(Run<?, ?> run, String credentialsId) {
        return CredentialsProvider.findCredentialById(credentialsId, StringCredentials.class, run);
    }

    @Extension
    public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {

        /**
         * Check to make sure the user has entered an Org Id
         *
         * @param value
         * @return
         * @throws IOException
         * @throws ServletException
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
         * @param value
         * @return
         * @throws IOException
         * @throws ServletException
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
         * @param value
         * @return
         * @throws IOException
         * @throws ServletException
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
         * @param serverUrl
         * @param credentialsId
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

        @Override
        public String getDisplayName() {
            return Messages.Oak9Builder_DescriptorImpl_DisplayName();
        }

    }

}