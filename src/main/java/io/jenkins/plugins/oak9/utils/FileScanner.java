package io.jenkins.plugins.oak9.utils;

import groovy.json.internal.IO;
import hudson.FilePath;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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