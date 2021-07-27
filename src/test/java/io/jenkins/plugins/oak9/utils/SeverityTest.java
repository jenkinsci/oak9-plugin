package io.jenkins.plugins.oak9.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

public class SeverityTest {
    @Test
    public void testExceedsSeverity() {
        assertFalse(Severity.exceedsSeverity(3, "low"));
        assertTrue(Severity.exceedsSeverity(0, "low"));
    }

    @Test
    public void testGetIntegerForSeverityText() throws IOException {
        assertThrows(IOException.class, () -> Severity.getIntegerForSeverityText("S1"));
    }

    @Test
    public void testGetTextForSeverityLevel() {
        assertEquals("", Severity.getTextForSeverityLevel(42));
        assertEquals("Low", Severity.getTextForSeverityLevel(1));
    }
}

