package io.jenkins.plugins.oak9.model;

public class Result {
    private String policyId;
    private String requirementId;
    private String requirementName;
    private String attributeKey;
    private String attributeValue;
    private String oak9RecommendedValue;
    private String errorMessage;
    private String severity;
    private String oak9Severity;
    private String documentation;

    public Result(
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
        this.policyId = policyId;
        this.requirementId = requirementId;
        this.requirementName = requirementName;
        this.attributeKey = attributeKey;
        this.attributeValue = attributeValue;
        this.oak9RecommendedValue = oak9RecommendedValue;
        this.errorMessage = errorMessage;
        this.severity = severity;
        this.oak9Severity = oak9Severity;
        this.documentation = documentation;
    }

    public String getPolicyId() {
        return policyId;
    }

    public void setPolicyId(String policyId) {
        this.policyId = policyId;
    }

    public String getRequirementId() {
        return requirementId;
    }

    public void setRequirementId(String requirementId) {
        this.requirementId = requirementId;
    }

    public String getRequirementName() {
        return requirementName;
    }

    public void setRequirementName(String requirementName) {
        this.requirementName = requirementName;
    }

    public String getAttributeKey() {
        return attributeKey;
    }

    public void setAttributeKey(String attributeKey) {
        this.attributeKey = attributeKey;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public String getOak9RecommendedValue() {
        return oak9RecommendedValue;
    }

    public void setOak9RecommendedValue(String oak9RecommendedValue) {
        this.oak9RecommendedValue = oak9RecommendedValue;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getOak9Severity() {
        return oak9Severity;
    }

    public void setOak9Severity(String oak9Severity) {
        this.oak9Severity = oak9Severity;
    }

    public String getDocumentation() {
        return documentation;
    }

    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }

}
