package io.jenkins.plugins.oak9;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import javax.servlet.ServletException;

import org.junit.jupiter.api.Test;

public class Oak9BuilderTest {
    @Test
    public void testConstructor() {
        Oak9Builder actualOak9Builder = new Oak9Builder("42", "myproject", "12345", 3, "https://example.org/example", 50);

        assertEquals("https://example.org/example", actualOak9Builder.getBaseUrl());
        assertEquals("12345", actualOak9Builder.getCredentialsId());
        assertEquals(3, actualOak9Builder.getMaxSeverity());
        assertEquals("42", actualOak9Builder.getOrgId());
        assertEquals("myproject", actualOak9Builder.getProjectId());
        assertEquals(50, actualOak9Builder.getPollingTimeoutSeconds());
    }

    @Test
    public void testGettersAndSetters() {
        Oak9Builder actualOak9Builder = new Oak9Builder("X", "X", "X", 0, "X", 0);

        actualOak9Builder.setBaseUrl("https://example.org/example");
        actualOak9Builder.setCredentialsId("12345");
        actualOak9Builder.setMaxSeverity(3);
        actualOak9Builder.setOrgId("42");
        actualOak9Builder.setProjectId("myproject");
        actualOak9Builder.setPollingTimeoutSeconds(50);

        assertEquals("https://example.org/example", actualOak9Builder.getBaseUrl());
        assertEquals("12345", actualOak9Builder.getCredentialsId());
        assertEquals(3, actualOak9Builder.getMaxSeverity());
        assertEquals("42", actualOak9Builder.getOrgId());
        assertEquals("myproject", actualOak9Builder.getProjectId());
        assertEquals(50, actualOak9Builder.getPollingTimeoutSeconds());
    }


    @Test
    public void testDefaultPollingTimeout() {
        Oak9Builder actualOak9Builder = new Oak9Builder("X", "X", "X", 0, "X", -100);

        // If polling timeout is set to 0 or below it gets defaulted to 30
        assertEquals(30, actualOak9Builder.getPollingTimeoutSeconds());
    }
}

