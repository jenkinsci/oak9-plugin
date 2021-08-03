package io.jenkins.plugins.oak9.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ConfigDictionaryTest {
    @Test
    public void testSetAdditionalProperty() {
        ConfigDictionary configDictionary = new ConfigDictionary();
        configDictionary.setAdditionalProperty("Name", "Value");
        assertEquals(1, configDictionary.getAdditionalProperties().size());
    }

    @Test
    public void testConstructor() {
        assertTrue((new ConfigDictionary()).getAdditionalProperties().isEmpty());
    }
}

