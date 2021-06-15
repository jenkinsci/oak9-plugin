package io.jenkins.plugins.oak9;

import com.cloudbees.plugins.credentials.Credentials;
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
import io.jenkins.plugins.oak9.Messages;
import io.jenkins.plugins.oak9.model.ValidationResult;
import io.jenkins.plugins.oak9.utils.FileArchiver;
import io.jenkins.plugins.oak9.utils.FileScanner;
import io.jenkins.plugins.oak9.utils.IacExtensionFilter;
import io.jenkins.plugins.oak9.utils.oak9ApiClient;
import jenkins.model.Jenkins;
import org.jenkinsci.plugins.plaincredentials.StringCredentials;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;
import com.cloudbees.plugins.credentials.CredentialsMatchers;

import static com.cloudbees.plugins.credentials.domains.URIRequirementBuilder.fromUri;
import javax.servlet.ServletException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.zip.ZipOutputStream;

import jenkins.tasks.SimpleBuildStep;

public class Oak9Builder extends Builder implements SimpleBuildStep {

    private final String orgId;
    private final String projectId;
    private final String credentialsId;
    private final String maxSeverity;

    @DataBoundConstructor
    public Oak9Builder(String orgId, String projectId, String credentialsId, String maxSeverity) {
        this.orgId = orgId;
        this.projectId = projectId;
        this.credentialsId = credentialsId;
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

    public String getMaxSeverity() {
        return this.maxSeverity;
    }

    public FilePath createZipFile(FilePath workspace) {
        return workspace;
    }

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
            throw new IOException("No IaC files could be found!");
        }

        // Zip Files
        taskListener.getLogger().println("Packaging IaC files for oak9...\n");
        FileArchiver archiver = new FileArchiver(workspace.absolutize(), IacFiles, "oak9.zip");
        archiver.zipFiles(workspace.absolutize().toString());

        File zipFile = new File("oak9.zip");
        if (!zipFile.exists()) {
            throw new InterruptedException("Unable to generate zip file. Aborting.");
        }

        // Make request to oak9 API to push zip file
        taskListener.getLogger().print("Sending IaC files to oak9...\n");
        StringCredentials oak9key = getCredentials(run, this.getCredentialsId());
        oak9ApiClient client = new oak9ApiClient("https://devconsole-api.oak9.cloud/integrations", oak9key.getSecret().getPlainText(), this.orgId, this.projectId, taskListener);
        ValidationResult result = client.postFileValidation(zipFile);

        // Check status endpoint
        if (result.getStatus() != "Queued"){
            throw new InterruptedException("Unexpected status: " + result.getStatus() + " from oak9 API");
        }

        taskListener.getLogger().print("Waiting for oak9 analysis...\n");


        // Analyze Results
        taskListener.getLogger().print("Analyzing oak9 scan results...\n");

        taskListener.getLogger().println("oak9 Runner Complete\n");

    }

    public static StringCredentials getCredentials(Run<?, ?> run, String credentialsId) {
        return CredentialsProvider.findCredentialById(credentialsId, StringCredentials.class, run);
    }

    @Extension
    public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {

        public FormValidation doCheckOrgId(@QueryParameter String value)
                throws IOException, ServletException {
            if (value.length() == 0)
                return FormValidation.error(Messages.Oak9Builder_DescriptorImpl_errors_missingOrgId());
            return FormValidation.ok();
        }

        public FormValidation doCheckProjectId(@QueryParameter String value)
                throws IOException, ServletException {
            if (value.length() == 0)
                return FormValidation.error(Messages.Oak9Builder_DescriptorImpl_errors_missingProjectId());
            return FormValidation.ok();
        }

        public FormValidation doCheckCredentialsId(@QueryParameter String value)
                throws IOException, ServletException {
            if (value.length() == 0)
                return FormValidation.error(Messages.Oak9Builder_DescriptorImpl_errors_missingKey());
            return FormValidation.ok();
        }

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