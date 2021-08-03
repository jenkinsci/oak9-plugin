package io.jenkins.plugins.oak9.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class AccessoriesTest {
    @Test
    public void testSetAdditionalProperty() {
        Accessories accessories = new Accessories();
        accessories.setAdditionalProperty("Name", "Value");
        assertEquals(1, accessories.getAdditionalProperties().size());
    }

    @Test
    public void testConstructor() {
        assertTrue((new Accessories()).getAdditionalProperties().isEmpty());
    }
}

