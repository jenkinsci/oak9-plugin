package io.jenkins.plugins.oak9.utils;

import hudson.FilePath;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Collection;

public class FileScanner {

    /**
     * Scan for infrastructure as code files to ship to oak9
     *
     * @param path The path toscan for IaC files
     * @param filter A file filter to be used to filter out unwanted files
     * @return Collection of IaC files
     * @throws IOException thrown if the provided path does not exist
     * @throws IllegalArgumentException thrown if the path provided is not a directory
     */
    public static Collection<File> scanForIacFiles(FilePath path, FileFilter filter) throws IOException, IllegalArgumentException {

        // generate a Java path from the Jenkins FilePath object
        File workspace_path = new File(path.toString());

        if (!workspace_path.exists()) {
            throw new IOException("The specified path does not exist: " + workspace_path);
        }

        if (!workspace_path.isDirectory()) {
            throw new IllegalArgumentException("Path must be a directory.");
        }

        String[] extensions = {"tf", "tfvars", "tfstate", "json", "yaml"};
        Collection<File> files = FileUtils.listFiles(workspace_path, extensions , true);
        return files;
    }
}