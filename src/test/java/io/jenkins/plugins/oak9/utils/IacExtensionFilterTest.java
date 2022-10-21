package io.jenkins.plugins.oak9.utils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

public class IacExtensionFilterTest {
    @Test
    public void testAcceptTxtFalse() {
        IacExtensionFilter iacExtensionFilter = new IacExtensionFilter();
        assertFalse(iacExtensionFilter.accept(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile()));
    }

    @Test
    public void testAcceptTfuTrue() {
        IacExtensionFilter iacExtensionFilter = new IacExtensionFilter();
        assertTrue(iacExtensionFilter.accept(Paths.get(System.getProperty("java.io.tmpdir"), "U.tfU").toFile()));
    }

    @Test
    public void testAcceptJsonTrue() {
        IacExtensionFilter iacExtensionFilter = new IacExtensionFilter();
        assertTrue(iacExtensionFilter.accept(Paths.get(System.getProperty("java.io.tmpdir"), "U.json").toFile()));
    }

    @Test
    public void testAcceptYamlTrue() {
        IacExtensionFilter iacExtensionFilter = new IacExtensionFilter();
        assertTrue(iacExtensionFilter.accept(Paths.get(System.getProperty("java.io.tmpdir"), "U.yaml").toFile()));
    }

    @Test
    public void testAcceptEmptyFalse() {
        IacExtensionFilter iacExtensionFilter = new IacExtensionFilter();
        assertFalse(iacExtensionFilter.accept(Paths.get(System.getProperty("java.io.tmpdir"), "").toFile()));
    }

    @Test
    public void testAcceptYmlTrue() {
        IacExtensionFilter iacExtensionFilter = new IacExtensionFilter();
        assertTrue(iacExtensionFilter.accept(Paths.get(System.getProperty("java.io.tmpdir"), "U.yml").toFile()));
    }

    @Test
    public void testAcceptTfTrue() {
        IacExtensionFilter iacExtensionFilter = new IacExtensionFilter();
        assertTrue(iacExtensionFilter.accept(Paths.get(System.getProperty("java.io.tmpdir"), "test.tf").toFile()));
    }

    @Test
    public void testAcceptTfVarsTrue() {
        IacExtensionFilter iacExtensionFilter = new IacExtensionFilter();
        assertTrue(iacExtensionFilter.accept(Paths.get(System.getProperty("java.io.tmpdir"), "U.tfvars").toFile()));
    }

    @Test
    public void testAcceptTfJsonTrue() {
        IacExtensionFilter iacExtensionFilter = new IacExtensionFilter();
        assertTrue(iacExtensionFilter.accept(Paths.get(System.getProperty("java.io.tmpdir"), "test.tf.json").toFile()));
    }

    @Test
    public void testAcceptYaml1False() {
        IacExtensionFilter iacExtensionFilter = new IacExtensionFilter();
        assertFalse(iacExtensionFilter.accept(Paths.get(System.getProperty("java.io.tmpdir"), "U.yaml1").toFile()));
    }
}

