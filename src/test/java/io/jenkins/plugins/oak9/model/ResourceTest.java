package io.jenkins.plugins.oak9.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class ResourceTest {
    @Test
    public void testConstructor() {
        Resource actualResource = new Resource();
        Accessories accessories = new Accessories();
        actualResource.setAccessories(accessories);
        actualResource.setBlueprintId("Blueprint Id");
        Component component = new Component();
        actualResource.setComponent(component);
        ConfigDictionary configDictionary = new ConfigDictionary();
        actualResource.setConfigDictionary(configDictionary);
        actualResource.setContext("Context");
        actualResource.setLastScannedOn("Last Scanned On");
        actualResource.setOak9ResourceTypeId("Oak9 Resource Type Id");
        ArrayList<Object> objectList = new ArrayList<Object>();
        actualResource.setResourceConnections(objectList);
        actualResource.setResourceId("Resource Id");
        actualResource.setResourceLocation("Resource Location");
        actualResource.setResourceName("Resource Name");
        actualResource.setResourceSubscriptionId("Resource Subscription Id");
        ArrayList<Object> objectList1 = new ArrayList<Object>();
        actualResource.setResourceTags(objectList1);
        actualResource.setResourceType("Resource Type");
        Tag tag = new Tag();
        actualResource.setTags(tag);
        ArrayList<Violation> violationList = new ArrayList<Violation>();
        actualResource.setViolations(violationList);
        assertSame(accessories, actualResource.getAccessories());
        assertSame(component, actualResource.getComponent());
        assertSame(configDictionary, actualResource.getConfigDictionary());
        assertEquals("Last Scanned On", actualResource.getLastScannedOn());
        List<Object> resourceConnections = actualResource.getResourceConnections();
        assertSame(objectList, resourceConnections);
        List<Violation> violations = actualResource.getViolations();
        assertEquals(violations, resourceConnections);
        assertEquals(objectList1, resourceConnections);
        List<Object> resourceTags = actualResource.getResourceTags();
        assertSame(objectList1, resourceTags);
        assertEquals(resourceConnections, resourceTags);
        assertEquals(violations, resourceTags);
        assertEquals("Resource Type", actualResource.getResourceType());
        assertSame(tag, actualResource.getTags());
        assertSame(violationList, violations);
        assertEquals(objectList, violations);
        assertEquals(objectList1, violations);
    }

    @Test
    public void testConstructor2() {
        ArrayList<Object> objectList = new ArrayList<Object>();
        ArrayList<Object> objectList1 = new ArrayList<Object>();
        ConfigDictionary configDictionary = new ConfigDictionary();
        Tag tags = new Tag();
        Accessories accessories = new Accessories();
        Component component = new Component();
        ArrayList<Violation> violationList = new ArrayList<Violation>();
        Resource actualResource = new Resource("Resource Id", "Resource Name", "Last Scanned On", "Resource Type",
                "Oak9 Resource Type Id", "Blueprint Id", objectList, "Resource Location", "Resource Subscription Id",
                objectList1, configDictionary, tags, accessories, "Context", component, violationList);
        Accessories accessories1 = new Accessories();
        actualResource.setAccessories(accessories1);
        actualResource.setBlueprintId("Blueprint Id");
        Component component1 = new Component();
        actualResource.setComponent(component1);
        ConfigDictionary configDictionary1 = new ConfigDictionary();
        actualResource.setConfigDictionary(configDictionary1);
        actualResource.setContext("Context");
        actualResource.setLastScannedOn("Last Scanned On");
        actualResource.setOak9ResourceTypeId("Oak9 Resource Type Id");
        ArrayList<Object> objectList2 = new ArrayList<Object>();
        actualResource.setResourceConnections(objectList2);
        actualResource.setResourceId("Resource Id");
        actualResource.setResourceLocation("Resource Location");
        actualResource.setResourceName("Resource Name");
        actualResource.setResourceSubscriptionId("Resource Subscription Id");
        ArrayList<Object> objectList3 = new ArrayList<Object>();
        actualResource.setResourceTags(objectList3);
        actualResource.setResourceType("Resource Type");
        Tag tag = new Tag();
        actualResource.setTags(tag);
        ArrayList<Violation> violationList1 = new ArrayList<Violation>();
        actualResource.setViolations(violationList1);
        assertSame(accessories1, actualResource.getAccessories());
        assertSame(component1, actualResource.getComponent());
        assertSame(configDictionary1, actualResource.getConfigDictionary());
        assertEquals("Last Scanned On", actualResource.getLastScannedOn());
        List<Object> resourceConnections = actualResource.getResourceConnections();
        assertSame(objectList2, resourceConnections);
        assertEquals(violationList, resourceConnections);
        assertEquals(objectList3, resourceConnections);
        assertEquals(objectList1, resourceConnections);
        assertEquals(objectList, resourceConnections);
        List<Violation> violations = actualResource.getViolations();
        assertEquals(violations, resourceConnections);
        List<Object> resourceTags = actualResource.getResourceTags();
        assertSame(objectList3, resourceTags);
        assertEquals(violationList, resourceTags);
        assertEquals(objectList1, resourceTags);
        assertEquals(violations, resourceTags);
        assertEquals(resourceConnections, resourceTags);
        assertEquals(objectList, resourceTags);
        assertEquals("Resource Type", actualResource.getResourceType());
        assertSame(tag, actualResource.getTags());
        assertSame(violationList1, violations);
        assertEquals(violationList, violations);
        assertEquals(objectList3, violations);
        assertEquals(objectList2, violations);
        assertEquals(objectList1, violations);
        assertEquals(objectList, violations);
    }

    @Test
    public void testSetAdditionalProperty() {
        Resource resource = new Resource();
        resource.setAdditionalProperty("Name", "Value");
        assertEquals(1, resource.getAdditionalProperties().size());
    }
}

