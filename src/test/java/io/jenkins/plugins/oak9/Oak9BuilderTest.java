package io.jenkins.plugins.oak9;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import javax.servlet.ServletException;

import org.junit.jupiter.api.Test;

public class Oak9BuilderTest {
    @Test
    public void testConstructor() {
        Oak9Builder actualOak9Builder = new Oak9Builder("42", "myproject", "42", 3, "https://example.org/example");
        actualOak9Builder.setBaseUrl("https://example.org/example");
        actualOak9Builder.setCredentialsId("42");
        actualOak9Builder.setMaxSeverity(3);
        actualOak9Builder.setOrgId("42");
        actualOak9Builder.setProjectId("myproject");
        assertEquals("https://example.org/example", actualOak9Builder.getBaseUrl());
        assertEquals("42", actualOak9Builder.getCredentialsId());
        assertEquals(3, actualOak9Builder.getMaxSeverity());
        assertEquals("42", actualOak9Builder.getOrgId());
        assertEquals("myproject", actualOak9Builder.getProjectId());
    }
}

