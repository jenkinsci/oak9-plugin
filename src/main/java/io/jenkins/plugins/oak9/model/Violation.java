package io.jenkins.plugins.oak9.model;

public class Violation extends Result {

    public Violation(
            String policyId,
            String requirementId,
            String requirementName,
            String attributeKey,
            String attributeValue,
            String oak9RecommendedValue,
            String errorMessage,
            String severity,
            String oak9Severity,
            String documentation) {
        super(policyId,
                requirementId,
                requirementName,
                attributeKey,
                attributeValue,
                oak9RecommendedValue,
                errorMessage,
                severity,
                oak9Severity,
                documentation);
    }
}
