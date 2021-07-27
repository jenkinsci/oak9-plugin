package io.jenkins.plugins.oak9.utils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

public class IacExtensionFilterTest {
    @Test
    public void testAccept() {
        IacExtensionFilter iacExtensionFilter = new IacExtensionFilter();
        assertFalse(iacExtensionFilter.accept(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile()));
    }

    @Test
    public void testAccept2() {
        IacExtensionFilter iacExtensionFilter = new IacExtensionFilter();
        assertTrue(iacExtensionFilter.accept(Paths.get(System.getProperty("java.io.tmpdir"), "U.tfU").toFile()));
    }

    @Test
    public void testAccept3() {
        IacExtensionFilter iacExtensionFilter = new IacExtensionFilter();
        assertTrue(iacExtensionFilter.accept(Paths.get(System.getProperty("java.io.tmpdir"), "U.json").toFile()));
    }

    @Test
    public void testAccept4() {
        IacExtensionFilter iacExtensionFilter = new IacExtensionFilter();
        assertTrue(iacExtensionFilter.accept(Paths.get(System.getProperty("java.io.tmpdir"), "U.yaml").toFile()));
    }

    @Test
    public void testAccept5() {
        IacExtensionFilter iacExtensionFilter = new IacExtensionFilter();
        assertFalse(iacExtensionFilter.accept(Paths.get(System.getProperty("java.io.tmpdir"), "").toFile()));
    }

    @Test
    public void testAccept6() {
        IacExtensionFilter iacExtensionFilter = new IacExtensionFilter();
        assertFalse(iacExtensionFilter.accept(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile(), "foo"));
    }

    @Test
    public void testAccept7() {
        IacExtensionFilter iacExtensionFilter = new IacExtensionFilter();
        assertTrue(iacExtensionFilter.accept(Paths.get(System.getProperty("java.io.tmpdir"), "U.tfU").toFile(), "foo"));
    }

    @Test
    public void testAccept8() {
        IacExtensionFilter iacExtensionFilter = new IacExtensionFilter();
        assertTrue(iacExtensionFilter.accept(Paths.get(System.getProperty("java.io.tmpdir"), "U.json").toFile(), "foo"));
    }

    @Test
    public void testAccept9() {
        IacExtensionFilter iacExtensionFilter = new IacExtensionFilter();
        assertTrue(iacExtensionFilter.accept(Paths.get(System.getProperty("java.io.tmpdir"), "U.yaml").toFile(), "foo"));
    }
}

