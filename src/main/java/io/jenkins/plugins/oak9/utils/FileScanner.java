package io.jenkins.plugins.oak9.utils;

import hudson.FilePath;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileScanner {

    private final List<String> allowedExtensionPatterns;
    private final FilePath basePath;

    public FileScanner(List<String> allowedExtensionPatterns, FilePath basePath) {
        this.allowedExtensionPatterns = allowedExtensionPatterns;
        this.basePath = basePath;
    }

    public List<Path> scanForIacFiles(Path path) throws IOException {

        if (!Files.exists(path)) {
            throw new IOException("The specified path does not exist.");
        }

        if (!Files.isDirectory(path)) {
            throw new IllegalArgumentException("Path must be a directory!");
        }

        List<Path> result;
        try (Stream<Path> pathStream = Files.find(path,
                Integer.MAX_VALUE,
                (p, basicFileAttributes) -> {
                    for (String pattern : this.allowedExtensionPatterns) {
                        if (p.getFileName().toString().toLowerCase().matches(pattern)) {
                            return true;
                        }
                    }
                    return false;
                })) {
            result = pathStream.collect(Collectors.toList());
        }
        return result;
    }
}