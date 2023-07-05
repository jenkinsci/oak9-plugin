package io.jenkins.plugins.oak9.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class ResultTest {
    @Test
    public void testConstructor() {
        Result actualResult = new Result();
        ArrayList<DesignGap> designGapList = new ArrayList<DesignGap>();
        actualResult.setDesignGaps(designGapList);
        actualResult.setEntityType("Entity Type");
        actualResult.setErrorMessage("Error Message");
        actualResult.setOrgId("42");
        actualResult.setProjectId("myproject");
        actualResult.setProjectEnvironmentId("myprojectenvironment");
        actualResult.setRepositoryName("Repository Name");
        actualResult.setRequestId("42");
        ArrayList<Resource> resourceList = new ArrayList<Resource>();
        actualResult.setResources(resourceList);
        ResultRef resultRef = new ResultRef("Entity Type", "Ref Type", "S3 Ref Key", "Doc Ref Primary Key",
                "Doc Ref Entity Key");

        actualResult.setResultRef(resultRef);
        ArrayList<Object> objectList = new ArrayList<Object>();
        actualResult.setResults(objectList);
        actualResult.setSource("Source");
        actualResult.setStatus("Status");
        actualResult.setTimeStamp("Time Stamp");
        actualResult.setVersion("1.0.2");
        List<DesignGap> designGaps = actualResult.getDesignGaps();
        assertSame(designGapList, designGaps);
        List<Resource> resources = actualResult.getResources();
        assertEquals(resources, designGaps);
        List<Object> results = actualResult.getResults();
        assertEquals(results, designGaps);
        assertEquals("Entity Type", actualResult.getEntityType());
        assertEquals("42", actualResult.getOrgId());
        assertEquals("myproject", actualResult.getProjectId());
        assertEquals("myprojectenvironment", actualResult.getProjectEnvironmentId());
        assertEquals("42", actualResult.getRequestId());
        assertSame(resourceList, resources);
        assertEquals(designGapList, resources);
        assertEquals(results, resources);
        assertSame(resultRef, actualResult.getResultRef());
        assertSame(objectList, results);
        assertEquals(resourceList, results);
        assertEquals(designGapList, results);
        assertEquals("Status", actualResult.getStatus());
        assertEquals("Time Stamp", actualResult.getTimeStamp());
        assertEquals("1.0.2", actualResult.getVersion());
    }

    @Test
    public void testConstructor2() {
        ArrayList<Resource> resourceList = new ArrayList<Resource>();
        ArrayList<DesignGap> designGapList = new ArrayList<DesignGap>();
        ResultRef resultRef = new ResultRef("Entity Type", "Ref Type", "S3 Ref Key", "Doc Ref Primary Key",
                "Doc Ref Entity Key");

        ArrayList<Object> objectList = new ArrayList<Object>();
        Result actualResult = new Result("42", "Repository Name", "Time Stamp", resourceList, "Status", "Error Message",
                "Source", designGapList, resultRef, objectList, "42", "myproject", "myprojectenvironment", "1.0.2", "Entity Type");
        ArrayList<DesignGap> designGapList1 = new ArrayList<DesignGap>();
        actualResult.setDesignGaps(designGapList1);
        actualResult.setEntityType("Entity Type");
        actualResult.setErrorMessage("Error Message");
        actualResult.setOrgId("42");
        actualResult.setProjectId("myproject");
        actualResult.setProjectEnvironmentId("myprojectenvironment");
        actualResult.setRepositoryName("Repository Name");
        actualResult.setRequestId("42");
        ArrayList<Resource> resourceList1 = new ArrayList<Resource>();
        actualResult.setResources(resourceList1);
        ResultRef resultRef1 = new ResultRef("Entity Type", "Ref Type", "S3 Ref Key", "Doc Ref Primary Key",
                "Doc Ref Entity Key");

        actualResult.setResultRef(resultRef1);
        ArrayList<Object> objectList1 = new ArrayList<Object>();
        actualResult.setResults(objectList1);
        actualResult.setSource("Source");
        actualResult.setStatus("Status");
        actualResult.setTimeStamp("Time Stamp");
        actualResult.setVersion("1.0.2");
        List<DesignGap> designGaps = actualResult.getDesignGaps();
        assertSame(designGapList1, designGaps);
        List<Object> results = actualResult.getResults();
        assertEquals(results, designGaps);
        assertEquals(resourceList, designGaps);
        assertEquals(designGapList, designGaps);
        assertEquals(objectList, designGaps);
        List<Resource> resources = actualResult.getResources();
        assertEquals(resources, designGaps);
        assertEquals("Entity Type", actualResult.getEntityType());
        assertEquals("42", actualResult.getOrgId());
        assertEquals("myproject", actualResult.getProjectId());
        assertEquals("myprojectenvironment", actualResult.getProjectEnvironmentId());
        assertEquals("42", actualResult.getRequestId());
        assertSame(resourceList1, resources);
        assertEquals(results, resources);
        assertEquals(resourceList, resources);
        assertEquals(designGapList1, resources);
        assertEquals(designGapList, resources);
        assertEquals(objectList, resources);
        assertSame(resultRef1, actualResult.getResultRef());
        assertSame(objectList1, results);
        assertEquals(resourceList, results);
        assertEquals(designGapList1, results);
        assertEquals(designGapList, results);
        assertEquals(objectList, results);
        assertEquals(resourceList1, results);
        assertEquals("Status", actualResult.getStatus());
        assertEquals("Time Stamp", actualResult.getTimeStamp());
        assertEquals("1.0.2", actualResult.getVersion());
    }

    @Test
    public void testSetAdditionalProperty() {
        Result result = new Result();
        result.setAdditionalProperty("Name", "Value");
        assertEquals(1, result.getAdditionalProperties().size());
    }
}

