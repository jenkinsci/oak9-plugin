package io.jenkins.plugins.oak9.model;

import java.util.List;

public class ApiResponse {
    public ApiResponse(String requestId, String status, String summary, String errorMessage, ValidationResult result, String resultsUrl) {
        this.requestId = requestId;
        this.status = status;
        this.summary = summary;
        this.errorMessage = errorMessage;
        this.result = result;
        this.resultsUrl = resultsUrl;
    }

    private String requestId;
    private String status;
    private String summary;
    private String errorMessage;
    private ValidationResult result;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ValidationResult getResult() {
        return result;
    }

    public void setResult(ValidationResult result) {
        this.result = result;
    }

    public String getResultsUrl() {
        return resultsUrl;
    }

    public void setResultsUrl(String resultsUrl) {
        this.resultsUrl = resultsUrl;
    }

    private String resultsUrl;




}
