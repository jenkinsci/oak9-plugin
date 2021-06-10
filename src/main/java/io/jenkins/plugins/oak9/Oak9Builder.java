package io.jenkins.plugins.oak9;

import edu.umd.cs.findbugs.annotations.NonNull;
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
import io.jenkins.plugins.oak9.utils.FileArchiver;
import io.jenkins.plugins.oak9.utils.FileScanner;
import io.jenkins.plugins.oak9.utils.IacExtensionFilter;
import jenkins.model.Jenkins;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;
import com.cloudbees.plugins.credentials.CredentialsMatchers;
import com.cloudbees.plugins.credentials.common.StandardCredentials;
import com.cloudbees.plugins.credentials.common.StandardListBoxModel;
import static com.cloudbees.plugins.credentials.domains.URIRequirementBuilder.fromUri;
import javax.servlet.ServletException;
import java.io.*;
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
    private final String key;
    private final String maxSeverity;

    @DataBoundConstructor
    public Oak9Builder(String orgId, String projectId, String key, String maxSeverity) {
        this.orgId = orgId;
        this.projectId = projectId;
        this.key = key;
        this.maxSeverity = maxSeverity;
    }

    public FilePath createZipFile(FilePath workspace) {
        return workspace;
    }

    @Override
    public void perform(
                        @NonNull Run<?, ?> run,
                        @NonNull FilePath workspace,
                        @NonNull Launcher launcher,
                        @NonNull TaskListener taskListener
    ) throws InterruptedException, IOException {

        // Find list of IaC files
        //List<FilePath> workspaceItems = workspace.list(new IacExtensionFilter());
        Collection<File> IacFiles = FileScanner.scanForIacFiles(workspace.absolutize(), new IacExtensionFilter());
        if (IacFiles.size() == 0) {
            throw new IOException("No IaC files could be found!");
        }

        // Zip Files
        taskListener.getLogger().println("Packaging IaC files for oak9...\n");
        FileArchiver archiver = new FileArchiver(workspace.absolutize(), IacFiles, "oak9.zip");
        archiver.zipFiles();

        // Make request to oak9 API to push zip file
        taskListener.getLogger().print("Sending IaC files to oak9...\n");

        // Check status endpoint
        taskListener.getLogger().print("Waiting for oak9 analysis...\n");

        // Analyze Results
        taskListener.getLogger().print("Analyzing oak9 scan results...\n");

        taskListener.getLogger().println("oak9 Runner Complete\n");

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

        public FormValidation doCheckKey(@QueryParameter String value)
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