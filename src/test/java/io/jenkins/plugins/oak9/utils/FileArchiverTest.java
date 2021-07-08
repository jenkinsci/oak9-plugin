package io.jenkins.plugins.oak9.utils;

import hudson.FilePath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertThrows;

class FileArchiverTest {

    private FileArchiver fileArchiverUnderTest;

    @BeforeEach
    void setUp() {
        //fileArchiverUnderTest = new FileArchiver(new FilePath(new File("filename.txt")), Arrays.asList(new File("filename.txt")), "test.zip");
    }

    @Test
    void testZipFiles1() throws Exception {
        // Setup


        // Run the test
        fileArchiverUnderTest.zipFiles();

        // Verify the results
    }

    @Test
    void testZipFiles1_ThrowsIOException() {
        // Run the test
        assertThrows(IOException.class, () -> fileArchiverUnderTest.zipFiles());
    }

    @Test
    void testZipFiles2() throws Exception {
        // Setup

        // Run the test
        //fileArchiverUnderTest.zipFiles("base_path");

        // Verify the results
    }

    @Test
    void testZipFiles2_ThrowsIOException() {
        // Setup

        // Run the test
        //assertThrows(IOException.class, () -> fileArchiverUnderTest.zipFiles("base_path"));
    }
}
