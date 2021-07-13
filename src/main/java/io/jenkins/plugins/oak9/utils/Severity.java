package io.jenkins.plugins.oak9.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Severity
{
    /**
     * Mapping of severity terms to numerical values for comparison
     */
    public static final Map<String, Integer> severities = new HashMap<String, Integer>() {{
        put("low", 1);
        put("moderate", 2);
        put("high", 3);
        put("critical", 4);
    }};

    /**
     * Compares a max severity integer with a severity string
     *
     * @param maxSeverity the maximum severity allowed
     * @param currentSeverity the current severity being evaluated
     * @return boolean to indicate if this passes or fails
     */
    public static boolean exceedsSeverity(int maxSeverity, String currentSeverity) {
        return Severity.severities.get(currentSeverity.trim().toLowerCase()) >= maxSeverity;
    }

    /**
     * Fetches the integer value of a given severity
     *
     * @param severityText the severity text provided
     * @return
     */
    public static int getIntegerForSeverityText(String severityText) throws IOException {
        if (Severity.severities.containsKey(severityText)) {
            return Severity.severities.get(severityText);
        }
        throw new IOException("Unknown severity provided");
    }

    public static String getTextForSeverityLevel(int value) {
        String response = "";
        for(Map.Entry<String, Integer> entry : Severity.severities.entrySet()){
            String textValue = entry.getKey();
            response = (entry.getValue() == value) ? textValue.substring(0, 1).toUpperCase() + textValue.substring(1) : response;
        }

        return response;
    }

}
