package io.jenkins.plugins.oak9.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class ComponentTest {
    @Test
    public void testConstructor() {
        Component actualComponent = new Component();
        actualComponent.setCollapsed(true);
        ArrayList<Object> objectList = new ArrayList<Object>();
        actualComponent.setConnections(objectList);
        actualComponent.setId("Id");
        actualComponent.setName("Name");
        actualComponent.setType("Type");
        actualComponent.setTypeId("42");
        actualComponent.setX(2);
        actualComponent.setY(3);
        assertTrue(actualComponent.getCollapsed());
        assertSame(objectList, actualComponent.getConnections());
        assertEquals("42", actualComponent.getTypeId());
        assertEquals(2, actualComponent.getX().intValue());
        assertEquals(3, actualComponent.getY().intValue());
    }

    @Test
    public void testConstructor2() {
        ArrayList<Object> objectList = new ArrayList<Object>();
        Component actualComponent = new Component("Id", "Name", "Type", "42", 2, 3, objectList, true);
        actualComponent.setCollapsed(true);
        ArrayList<Object> objectList1 = new ArrayList<Object>();
        actualComponent.setConnections(objectList1);
        actualComponent.setId("Id");
        actualComponent.setName("Name");
        actualComponent.setType("Type");
        actualComponent.setTypeId("42");
        actualComponent.setX(2);
        actualComponent.setY(3);
        assertTrue(actualComponent.getCollapsed());
        List<Object> connections = actualComponent.getConnections();
        assertSame(objectList1, connections);
        assertEquals(objectList, connections);
        assertEquals("42", actualComponent.getTypeId());
        assertEquals(2, actualComponent.getX().intValue());
        assertEquals(3, actualComponent.getY().intValue());
    }

    @Test
    public void testSetAdditionalProperty() {
        Component component = new Component();
        component.setAdditionalProperty("Name", "Value");
        assertEquals(1, component.getAdditionalProperties().size());
    }
}

