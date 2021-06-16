package io.jenkins.plugins.oak9.utils;

import hudson.FilePath;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileScannerTest {

    @Test
    void testScanForIacFiles() throws Exception {
        // Setup
        final FilePath path = new FilePath(new File("filename.txt"));
        final FileFilter filter = null;
        final Collection<File> expectedResult = Arrays.asList(new File("filename.txt"));

        // Run the test
        final Collection<File> result = FileScanner.scanForIacFiles(path, filter);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testScanForIacFiles_ThrowsIOException() {
        // Setup
        final FilePath path = new FilePath(new File("filename.txt"));
        final FileFilter filter = null;

        // Run the test
        assertThrows(IOException.class, () -> FileScanner.scanForIacFiles(path, filter));
    }

    @Test
    void testScanForIacFiles_ThrowsInterruptedException() {
        // Setup
        final FilePath path = new FilePath(new File("filename.txt"));
        final FileFilter filter = null;

        // Run the test
        assertThrows(InterruptedException.class, () -> FileScanner.scanForIacFiles(path, filter));
    }
}
