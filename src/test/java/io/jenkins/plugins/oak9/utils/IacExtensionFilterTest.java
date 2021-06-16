package io.jenkins.plugins.oak9.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;

class IacExtensionFilterTest {

    private IacExtensionFilter iacExtensionFilterUnderTest;

    @BeforeEach
    void setUp() {
        iacExtensionFilterUnderTest = new IacExtensionFilter();
    }

    @Test
    void testAccept1() {
        // Setup
        final File file = new File("filename.txt");

        // Run the test
        final boolean result = iacExtensionFilterUnderTest.accept(file);

        // Verify the results
        assertTrue(result);
    }

    @Test
    void testAccept2() {
        // Setup
        final File file = new File("filename.txt");

        // Run the test
        final boolean result = iacExtensionFilterUnderTest.accept(file, "s");

        // Verify the results
        assertTrue(result);
    }
}
