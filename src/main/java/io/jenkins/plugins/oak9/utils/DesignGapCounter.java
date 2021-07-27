package io.jenkins.plugins.oak9.utils;

import java.util.HashMap;
import java.util.Map;

public class DesignGapCounter {

    private Map<String, Integer> designGapViolationCounter = new HashMap<String, Integer>();

    public DesignGapCounter() {
        Map<String, Integer> newCounter = new HashMap<String, Integer>();
        for(Map.Entry<String, Integer> severity : Severity.severities.entrySet()) {
            String key = severity.getKey();
            newCounter.put(key, 0);
        }
        setDesignGapViolationCounter(newCounter);
    }

    public void setDesignGapViolationCounter(Map<String, Integer> designGapViolationCounter) {
        this.designGapViolationCounter = designGapViolationCounter;
    }

    /**
     * Returns the current map of design gap counts
     *
     * @return design gap count hash map
     */
    public Map<String, Integer> getDesignGapViolationCounter() {
        return designGapViolationCounter;
    }

    /**
     * Keeps counts of different severity violations from an Oak9 scan result
     *
     * @param severityLevel the Violation object from which we are analyzing the severity
     */
    public void trackDesignGapCounts(int severityLevel) {
        String key = Severity.getTextForSeverityLevel(severityLevel).toLowerCase();
        designGapViolationCounter.replace(
                key,
                designGapViolationCounter.get(key) + 1
        );
    }

    /**
     * Get the current count for a given severity. Returns 0 if there is no set entry for the provided severity
     *
     * @param severity the severity for which you are requesting a count of design gaps
     * @return the count of design gaps for the given severity
     */
    public Integer getCountForSeverity(String severity) {
        return this.designGapViolationCounter.containsKey(severity) ? designGapViolationCounter.get(severity) : 0;
    }
}
