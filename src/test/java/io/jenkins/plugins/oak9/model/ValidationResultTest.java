package io.jenkins.plugins.oak9.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class ValidationResultTest {
    @Test
    public void testConstructor() {
        ValidationResult actualValidationResult = new ValidationResult();
        ArrayList<DesignGap> designGapList = new ArrayList<DesignGap>();
        actualValidationResult.setDesignGaps(designGapList);
        actualValidationResult.setEntityType("Entity Type");
        actualValidationResult.setErrorMessage("Error Message");
        actualValidationResult.setOrgId("42");
        actualValidationResult.setProjectId("myproject");
        actualValidationResult.setRepositoryName("Repository Name");
        actualValidationResult.setRequestId("42");
        ArrayList<Object> objectList = new ArrayList<Object>();
        actualValidationResult.setResources(objectList);
        ResultRef resultRef = new ResultRef("Entity Type", "Ref Type", "S3 Ref Key", "Doc Ref Primary Key",
                "Doc Ref Entity Key");

        actualValidationResult.setResultRef(resultRef);
        ArrayList<Object> objectList1 = new ArrayList<Object>();
        actualValidationResult.setResults(objectList1);
        actualValidationResult.setSource("Source");
        actualValidationResult.setStatus("Status");
        actualValidationResult.setTimeStamp("Time Stamp");
        actualValidationResult.setVersion("1.0.2");
        List<DesignGap> designGaps = actualValidationResult.getDesignGaps();
        assertSame(designGapList, designGaps);
        List<Object> results = actualValidationResult.getResults();
        assertEquals(results, designGaps);
        List<Object> resources = actualValidationResult.getResources();
        assertEquals(resources, designGaps);
        assertEquals("Entity Type", actualValidationResult.getEntityType());
        assertEquals("42", actualValidationResult.getOrgId());
        assertEquals("myproject", actualValidationResult.getProjectId());
        assertEquals("42", actualValidationResult.getRequestId());
        assertSame(objectList, resources);
        assertEquals(results, resources);
        assertEquals(designGapList, resources);
        assertSame(resultRef, actualValidationResult.getResultRef());
        assertSame(objectList1, results);
        assertEquals(objectList, results);
        assertEquals(designGapList, results);
        assertEquals("Status", actualValidationResult.getStatus());
        assertEquals("Time Stamp", actualValidationResult.getTimeStamp());
        assertEquals("1.0.2", actualValidationResult.getVersion());
    }

    @Test
    public void testConstructor2() {
        ArrayList<Object> objectList = new ArrayList<Object>();
        ArrayList<DesignGap> designGapList = new ArrayList<DesignGap>();
        ResultRef resultRef = new ResultRef("Entity Type", "Ref Type", "S3 Ref Key", "Doc Ref Primary Key",
                "Doc Ref Entity Key");

        ArrayList<Object> objectList1 = new ArrayList<Object>();
        ValidationResult actualValidationResult = new ValidationResult("42", "Repository Name", "Time Stamp", objectList,
                "Status", "Error Message", "Source", designGapList, resultRef, objectList1, "42", "myproject", "1.0.2",
                "Entity Type");
        ArrayList<DesignGap> designGapList1 = new ArrayList<DesignGap>();
        actualValidationResult.setDesignGaps(designGapList1);
        actualValidationResult.setEntityType("Entity Type");
        actualValidationResult.setErrorMessage("Error Message");
        actualValidationResult.setOrgId("42");
        actualValidationResult.setProjectId("myproject");
        actualValidationResult.setRepositoryName("Repository Name");
        actualValidationResult.setRequestId("42");
        ArrayList<Object> objectList2 = new ArrayList<Object>();
        actualValidationResult.setResources(objectList2);
        ResultRef resultRef1 = new ResultRef("Entity Type", "Ref Type", "S3 Ref Key", "Doc Ref Primary Key",
                "Doc Ref Entity Key");

        actualValidationResult.setResultRef(resultRef1);
        ArrayList<Object> objectList3 = new ArrayList<Object>();
        actualValidationResult.setResults(objectList3);
        actualValidationResult.setSource("Source");
        actualValidationResult.setStatus("Status");
        actualValidationResult.setTimeStamp("Time Stamp");
        actualValidationResult.setVersion("1.0.2");
        List<DesignGap> designGaps = actualValidationResult.getDesignGaps();
        assertSame(designGapList1, designGaps);
        assertEquals(objectList, designGaps);
        List<Object> resources = actualValidationResult.getResources();
        assertEquals(resources, designGaps);
        assertEquals(designGapList, designGaps);
        List<Object> results = actualValidationResult.getResults();
        assertEquals(results, designGaps);
        assertEquals(objectList1, designGaps);
        assertEquals("Entity Type", actualValidationResult.getEntityType());
        assertEquals("42", actualValidationResult.getOrgId());
        assertEquals("myproject", actualValidationResult.getProjectId());
        assertEquals("42", actualValidationResult.getRequestId());
        assertSame(objectList2, resources);
        assertEquals(objectList, resources);
        assertEquals(designGapList, resources);
        assertEquals(designGapList1, resources);
        assertEquals(results, resources);
        assertEquals(objectList1, resources);
        assertSame(resultRef1, actualValidationResult.getResultRef());
        assertSame(objectList3, results);
        assertEquals(objectList, results);
        assertEquals(objectList2, results);
        assertEquals(designGapList, results);
        assertEquals(designGapList1, results);
        assertEquals(objectList1, results);
        assertEquals("Status", actualValidationResult.getStatus());
        assertEquals("Time Stamp", actualValidationResult.getTimeStamp());
        assertEquals("1.0.2", actualValidationResult.getVersion());
    }

    @Test
    public void testSetAdditionalProperty() {
        ValidationResult validationResult = new ValidationResult();
        validationResult.setAdditionalProperty("Name", "Value");
        assertEquals(1, validationResult.getAdditionalProperties().size());
    }
}

