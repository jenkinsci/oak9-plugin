package io.jenkins.plugins.oak9.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class DesignGapTest {
    @Test
    public void testConstructor() {
        DesignGap actualDesignGap = new DesignGap();
        actualDesignGap.setOak9Guidance("1234");
        actualDesignGap.setRequirementId("42");
        actualDesignGap.setRequirementName("Requirement Name");
        actualDesignGap.setResourceId("42");
        actualDesignGap.setResourceName("Resource Name");
        actualDesignGap.setResourceType("Resource Type");
        actualDesignGap.setSource("Source");
        ArrayList<Violation> violationList = new ArrayList<Violation>();
        actualDesignGap.setViolations(violationList);
        assertEquals("1234", actualDesignGap.getOak9Guidance());
        assertEquals("42", actualDesignGap.getRequirementId());
        assertEquals("Requirement Name", actualDesignGap.getRequirementName());
        assertEquals("42", actualDesignGap.getResourceId());
        assertEquals("Resource Name", actualDesignGap.getResourceName());
        assertEquals("Resource Type", actualDesignGap.getResourceType());
        assertEquals("Source", actualDesignGap.getSource());
        assertSame(violationList, actualDesignGap.getViolations());
    }

    @Test
    public void testConstructor2() {
        ArrayList<Violation> violationList = new ArrayList<Violation>();
        DesignGap actualDesignGap = new DesignGap("42", "Requirement Name", "Source", "Resource Name", "42",
                "Resource Type", "1234", violationList);
        actualDesignGap.setOak9Guidance("1234");
        actualDesignGap.setRequirementId("42");
        actualDesignGap.setRequirementName("Requirement Name");
        actualDesignGap.setResourceId("42");
        actualDesignGap.setResourceName("Resource Name");
        actualDesignGap.setResourceType("Resource Type");
        actualDesignGap.setSource("Source");
        ArrayList<Violation> violationList1 = new ArrayList<Violation>();
        actualDesignGap.setViolations(violationList1);
        assertEquals("1234", actualDesignGap.getOak9Guidance());
        assertEquals("42", actualDesignGap.getRequirementId());
        assertEquals("Requirement Name", actualDesignGap.getRequirementName());
        assertEquals("42", actualDesignGap.getResourceId());
        assertEquals("Resource Name", actualDesignGap.getResourceName());
        assertEquals("Resource Type", actualDesignGap.getResourceType());
        assertEquals("Source", actualDesignGap.getSource());
        List<Violation> violations = actualDesignGap.getViolations();
        assertSame(violationList1, violations);
        assertEquals(violationList, violations);
    }

    @Test
    public void testSetAdditionalProperty() {
        DesignGap designGap = new DesignGap();
        designGap.setAdditionalProperty("Name", "Value");
        assertEquals(1, designGap.getAdditionalProperties().size());
    }
}

