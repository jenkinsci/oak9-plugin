package io.jenkins.plugins.oak9.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ResultRefTest {
    @Test
    public void testConstructor() {
        ResultRef actualResultRef = new ResultRef();
        actualResultRef.setDocRefEntityKey("Doc Ref Entity Key");
        actualResultRef.setDocRefPrimaryKey("Doc Ref Primary Key");
        actualResultRef.setEntityType("Entity Type");
        actualResultRef.setRefType("Ref Type");
        actualResultRef.setS3RefKey("S3 Ref Key");
        assertEquals("Doc Ref Entity Key", actualResultRef.getDocRefEntityKey());
        assertEquals("Doc Ref Primary Key", actualResultRef.getDocRefPrimaryKey());
        assertEquals("Entity Type", actualResultRef.getEntityType());
        assertEquals("Ref Type", actualResultRef.getRefType());
        assertEquals("S3 Ref Key", actualResultRef.getS3RefKey());
    }

    @Test
    public void testConstructor2() {
        ResultRef actualResultRef = new ResultRef("Entity Type", "Ref Type", "S3 Ref Key", "Doc Ref Primary Key",
                "Doc Ref Entity Key");
        actualResultRef.setDocRefEntityKey("Doc Ref Entity Key");
        actualResultRef.setDocRefPrimaryKey("Doc Ref Primary Key");
        actualResultRef.setEntityType("Entity Type");
        actualResultRef.setRefType("Ref Type");
        actualResultRef.setS3RefKey("S3 Ref Key");
        assertEquals("Doc Ref Entity Key", actualResultRef.getDocRefEntityKey());
        assertEquals("Doc Ref Primary Key", actualResultRef.getDocRefPrimaryKey());
        assertEquals("Entity Type", actualResultRef.getEntityType());
        assertEquals("Ref Type", actualResultRef.getRefType());
        assertEquals("S3 Ref Key", actualResultRef.getS3RefKey());
    }

    @Test
    public void testSetAdditionalProperty() {
        ResultRef resultRef = new ResultRef("Entity Type", "Ref Type", "S3 Ref Key", "Doc Ref Primary Key",
                "Doc Ref Entity Key");
        resultRef.setAdditionalProperty("Name", "Value");
        assertEquals(1, resultRef.getAdditionalProperties().size());
    }
}

