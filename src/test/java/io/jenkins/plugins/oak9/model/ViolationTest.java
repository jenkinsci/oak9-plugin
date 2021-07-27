package io.jenkins.plugins.oak9.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ViolationTest {
    @Test
    public void testConstructor() {
        Violation actualViolation = new Violation();
        actualViolation.setAttributeKey("Attribute Key");
        actualViolation.setAttributeValue("42");
        actualViolation.setDocumentation("Documentation");
        actualViolation.setErrorMessage("An error occurred");
        actualViolation.setOak9RecommendedValue("42");
        actualViolation.setOak9Severity("S1");
        actualViolation.setPolicyId("42");
        actualViolation.setRequirementId("42");
        actualViolation.setRequirementName("Requirement Name");
        actualViolation.setSeverity("S1");
        assertEquals("Attribute Key", actualViolation.getAttributeKey());
        assertEquals("42", actualViolation.getAttributeValue());
        assertEquals("Documentation", actualViolation.getDocumentation());
        assertEquals("An error occurred", actualViolation.getErrorMessage());
        assertEquals("42", actualViolation.getOak9RecommendedValue());
        assertEquals("S1", actualViolation.getOak9Severity());
        assertEquals("42", actualViolation.getPolicyId());
        assertEquals("42", actualViolation.getRequirementId());
        assertEquals("Requirement Name", actualViolation.getRequirementName());
        assertEquals("S1", actualViolation.getSeverity());
    }

    @Test
    public void testConstructor2() {
        Violation actualViolation = new Violation("42", "42", "Requirement Name", "Attribute Key", "42", "42",
                "An error occurred", "S1", "S1", "Documentation");
        actualViolation.setAttributeKey("Attribute Key");
        actualViolation.setAttributeValue("42");
        actualViolation.setDocumentation("Documentation");
        actualViolation.setErrorMessage("An error occurred");
        actualViolation.setOak9RecommendedValue("42");
        actualViolation.setOak9Severity("S1");
        actualViolation.setPolicyId("42");
        actualViolation.setRequirementId("42");
        actualViolation.setRequirementName("Requirement Name");
        actualViolation.setSeverity("S1");
        assertEquals("Attribute Key", actualViolation.getAttributeKey());
        assertEquals("42", actualViolation.getAttributeValue());
        assertEquals("Documentation", actualViolation.getDocumentation());
        assertEquals("An error occurred", actualViolation.getErrorMessage());
        assertEquals("42", actualViolation.getOak9RecommendedValue());
        assertEquals("S1", actualViolation.getOak9Severity());
        assertEquals("42", actualViolation.getPolicyId());
        assertEquals("42", actualViolation.getRequirementId());
        assertEquals("Requirement Name", actualViolation.getRequirementName());
        assertEquals("S1", actualViolation.getSeverity());
    }

    @Test
    public void testSetAdditionalProperty() {
        Violation violation = new Violation("42", "42", "Requirement Name", "Attribute Key", "42", "42",
                "An error occurred", "S1", "S1", "Documentation");
        violation.setAdditionalProperty("Name", "Value");
        assertEquals(1, violation.getAdditionalProperties().size());
    }
}

