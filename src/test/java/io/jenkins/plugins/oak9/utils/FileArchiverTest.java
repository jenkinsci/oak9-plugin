package io.jenkins.plugins.oak9.utils;

import hudson.FilePath;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class FileArchiverTest {
    @Test
    public void testConstructor() {
        // TODO: This test is incomplete.
        //   Reason: Nothing to assert: the constructed class does not have observers (e.g. getters or public fields).
        //   Add observers (e.g. getters or public fields) to the class.
        //   See https://diff.blue/R002

        FilePath basePath = new FilePath(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile());
        new FileArchiver(basePath, new ArrayList<File>(), "foo.txt");

    }
}

