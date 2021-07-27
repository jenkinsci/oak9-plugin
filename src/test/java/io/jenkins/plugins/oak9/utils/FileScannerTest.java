package io.jenkins.plugins.oak9.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import hudson.FilePath;

import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

public class FileScannerTest {
    @Test
    public void testScanForIacFiles() throws IOException, IllegalArgumentException {
        assertThrows(IOException.class,
                () -> FileScanner.scanForIacFiles(
                        new FilePath(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile()),
                        mock(FileFilter.class)));
        assertEquals(0,
                FileScanner
                        .scanForIacFiles(new FilePath(Paths.get(System.getProperty("java.io.tmpdir"), "").toFile()),
                                mock(FileFilter.class))
                        .size());
        assertEquals(0,
                FileScanner
                        .scanForIacFiles(new FilePath(Paths.get(System.getProperty("java.io.tmpdir")).toFile()),
                                mock(FileFilter.class))
                        .size());
        assertThrows(IOException.class, () -> FileScanner
                .scanForIacFiles(new FilePath(Paths.get(System.getProperty("tf"), "").toFile()), mock(FileFilter.class)));
    }
}

