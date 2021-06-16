package io.jenkins.plugins.oak9.utils;

import java.util.HashMap;
import java.util.Map;

public class Severity
{
    public static final Map<String, Integer> severities = new HashMap<String, Integer>() {{
        put("low", 1);
        put("moderate", 2);
        put("high", 3);
        put("critical", 4);
    }};

    public static boolean exceedsSeverity(int maxSeverity, String currentSeverity) {
        return Severity.severities.get(currentSeverity.trim().toLowerCase()) >= maxSeverity;
    }

}
