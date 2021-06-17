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
        "requestId",
        "status",
        "summary",
        "errorMessage",
        "result",
        "resultsUrl"
})

/**
 * This is generated code from the jsonschema2pojo library based upon the oak9 Swagger docs. It is not recommended to
 * make manual changes to this code.
 *
 * see https://github.com/joelittlejohn/jsonschema2pojo for usage instructions
 */
@Generated("jsonschema2pojo")
public class ApiResponse {

    @JsonProperty("requestId")
    private String requestId;
    @JsonProperty("status")
    private String status;
    @JsonProperty("summary")
    private Object summary;
    @JsonProperty("errorMessage")
    private Object errorMessage;
    @JsonProperty("result")
    private ValidationResult result;
    @JsonProperty("resultsUrl")
    private Object resultsUrl;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public ApiResponse() {
    }

    /**
     *
     * @param summary
     * @param result
     * @param resultsUrl
     * @param requestId
     * @param errorMessage
     * @param status
     */
    public ApiResponse(String requestId, String status, Object summary, Object errorMessage, ValidationResult result, Object resultsUrl) {
        this.requestId = requestId;
        this.status = status;
        this.summary = summary;
        this.errorMessage = errorMessage;
        this.result = result;
        this.resultsUrl = resultsUrl;
    }

    @JsonProperty("requestId")
    public String getRequestId() {
        return requestId;
    }

    @JsonProperty("requestId")
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("summary")
    public Object getSummary() {
        return summary;
    }

    @JsonProperty("summary")
    public void setSummary(Object summary) {
        this.summary = summary;
    }

    @JsonProperty("errorMessage")
    public Object getErrorMessage() {
        return errorMessage;
    }

    @JsonProperty("errorMessage")
    public void setErrorMessage(Object errorMessage) {
        this.errorMessage = errorMessage;
    }

    @JsonProperty("result")
    public ValidationResult getResult() {
        return result;
    }

    @JsonProperty("result")
    public void setResult(ValidationResult result) {
        this.result = result;
    }

    @JsonProperty("resultsUrl")
    public Object getResultsUrl() {
        return resultsUrl;
    }

    @JsonProperty("resultsUrl")
    public void setResultsUrl(Object resultsUrl) {
        this.resultsUrl = resultsUrl;
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