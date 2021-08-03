package io.jenkins.plugins.oak9.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

public class DesignGapCounterTest {
    @Test
    public void testConstructor() {
        DesignGapCounter actualDesignGapCounter = new DesignGapCounter();
        HashMap<String, Integer> stringIntegerMap = new HashMap<String, Integer>(1);
        actualDesignGapCounter.setDesignGapViolationCounter(stringIntegerMap);
        assertSame(stringIntegerMap, actualDesignGapCounter.getDesignGapViolationCounter());
    }

    @Test
    public void testConstructor2() {
        assertEquals(4, (new DesignGapCounter()).getDesignGapViolationCounter().size());
    }

    @Test
    public void testTrackDesignGapCounts() {
        DesignGapCounter designGapCounter = new DesignGapCounter();
        designGapCounter.trackDesignGapCounts(3);
        assertEquals(4, designGapCounter.getDesignGapViolationCounter().size());
    }

    @Test
    public void testTrackDesignGapCounts2() {
        DesignGapCounter designGapCounter = new DesignGapCounter();
        designGapCounter.trackDesignGapCounts(1);
        assertEquals(4, designGapCounter.getDesignGapViolationCounter().size());
    }

    @Test
    public void testGetCountForSeverity() {
        assertEquals(0, (new DesignGapCounter()).getCountForSeverity("S1").intValue());
    }
}

