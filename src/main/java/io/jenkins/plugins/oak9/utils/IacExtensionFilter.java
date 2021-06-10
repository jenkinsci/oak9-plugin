package io.jenkins.plugins.oak9.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public final class IacExtensionFilter
        implements FileFilter, Serializable
{
    private final List<String> allowedExtensionPatterns = Arrays.asList(".*\\.tf.*", ".*\\.json", ".*\\.yaml");

    @Override
    public boolean accept(File file) {
        // we don't want directories as individual entities
        if (file.isDirectory())
            return false;

        // we only want files with permitted extensions
        for (String extension_pattern : allowedExtensionPatterns) {
            if (file.getName().toLowerCase().matches(extension_pattern)) {
                return true;
            }
        }

        return false;
    }
}