package io.jenkins.plugins.oak9.model;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "policyId",
        "requirementId",
        "requirementName",
        "attributeKey",
        "attributeValue",
        "oak9RecommendedValue",
        "errorMessage",
        "severity",
        "oak9Severity",
        "documentation"
})

/**
 * This is generated code from the jsonschema2pojo library based upon the oak9 Swagger docs. It is not recommended to
 * make manual changes to this code.
 *
 * see https://github.com/joelittlejohn/jsonschema2pojo for usage instructions
 */
@Generated("jsonschema2pojo")
public class Violation {

    @JsonProperty("policyId")
    private Object policyId;
    @JsonProperty("requirementId")
    private String requirementId;
    @JsonProperty("requirementName")
    private String requirementName;
    @JsonProperty("attributeKey")
    private String attributeKey;
    @JsonProperty("attributeValue")
    private String attributeValue;
    @JsonProperty("oak9RecommendedValue")
    private Object oak9RecommendedValue;
    @JsonProperty("errorMessage")
    private String errorMessage;
    @JsonProperty("severity")
    private String severity;
    @JsonProperty("oak9Severity")
    private String oak9Severity;
    @JsonProperty("documentation")
    private String documentation;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public Violation() {
    }

    /**
     *
     * @param severity
     * @param requirementName
     * @param policyId
     * @param attributeValue
     * @param oak9RecommendedValue
     * @param documentation
     * @param errorMessage
     * @param oak9Severity
     * @param attributeKey
     * @param requirementId
     */
    public Violation(Object policyId, String requirementId, String requirementName, String attributeKey, String attributeValue, Object oak9RecommendedValue, String errorMessage, String severity, String oak9Severity, String documentation) {
        super();
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

    @JsonProperty("policyId")
    public Object getPolicyId() {
        return policyId;
    }

    @JsonProperty("policyId")
    public void setPolicyId(Object policyId) {
        this.policyId = policyId;
    }

    @JsonProperty("requirementId")
    public String getRequirementId() {
        return requirementId;
    }

    @JsonProperty("requirementId")
    public void setRequirementId(String requirementId) {
        this.requirementId = requirementId;
    }

    @JsonProperty("requirementName")
    public String getRequirementName() {
        return requirementName;
    }

    @JsonProperty("requirementName")
    public void setRequirementName(String requirementName) {
        this.requirementName = requirementName;
    }

    @JsonProperty("attributeKey")
    public String getAttributeKey() {
        return attributeKey;
    }

    @JsonProperty("attributeKey")
    public void setAttributeKey(String attributeKey) {
        this.attributeKey = attributeKey;
    }

    @JsonProperty("attributeValue")
    public String getAttributeValue() {
        return attributeValue;
    }

    @JsonProperty("attributeValue")
    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    @JsonProperty("oak9RecommendedValue")
    public Object getOak9RecommendedValue() {
        return oak9RecommendedValue;
    }

    @JsonProperty("oak9RecommendedValue")
    public void setOak9RecommendedValue(Object oak9RecommendedValue) {
        this.oak9RecommendedValue = oak9RecommendedValue;
    }

    @JsonProperty("errorMessage")
    public String getErrorMessage() {
        return errorMessage;
    }

    @JsonProperty("errorMessage")
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @JsonProperty("severity")
    public String getSeverity() {
        return severity;
    }

    @JsonProperty("severity")
    public void setSeverity(String severity) {
        this.severity = severity;
    }

    @JsonProperty("oak9Severity")
    public String getOak9Severity() {
        return oak9Severity;
    }

    @JsonProperty("oak9Severity")
    public void setOak9Severity(String oak9Severity) {
        this.oak9Severity = oak9Severity;
    }

    @JsonProperty("documentation")
    public String getDocumentation() {
        return documentation;
    }

    @JsonProperty("documentation")
    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}