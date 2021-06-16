package io.jenkins.plugins.oak9.utils;

import hudson.FilePath;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Collection;

public class FileScanner {

    public static Collection<File> scanForIacFiles(FilePath path, FileFilter filter) throws IOException, InterruptedException {

        // generate a Java path from the Jenkins FilePath object
        File workspace_path = new File(path.toString());

        if (!workspace_path.exists()) {
            throw new IOException("The specified path does not exist.");
        }

        if (!workspace_path.isDirectory()) {
            throw new IllegalArgumentException("Path must be a directory!");
        }
        String[] extensions = {"tf", "tfvars", "tfstate", "json", "yaml"};
        Collection files = FileUtils.listFiles(workspace_path, extensions , true);
        return files;
    }
}