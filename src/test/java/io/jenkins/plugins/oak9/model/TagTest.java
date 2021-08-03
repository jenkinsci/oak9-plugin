package io.jenkins.plugins.oak9.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TagTest {
    @Test
    public void testSetAdditionalProperty() {
        Tag tag = new Tag();
        tag.setAdditionalProperty("Name", "Value");
        assertEquals(1, tag.getAdditionalProperties().size());
    }

    @Test
    public void testConstructor() {
        assertTrue((new Tag()).getAdditionalProperties().isEmpty());
    }
}

