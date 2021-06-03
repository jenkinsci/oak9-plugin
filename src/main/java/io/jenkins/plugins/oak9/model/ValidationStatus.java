package io.jenkins.plugins.oak9.model;

import java.util.List;

public class ValidationStatus {
    private String requestId;
    private String status;
    private String errorMessage;
    private List<Result> results;
    private String resultsUrl;

    public ValidationStatus(String requestId, String status, String errorMessage, List<Result> results, String resultsUrl) {
        this.requestId = requestId;
        this.status = status;
        this.errorMessage = errorMessage;
        this.results = results;
        this.resultsUrl = resultsUrl;
    }

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

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public String getResultsUrl() {
        return resultsUrl;
    }

    public void setResultsUrl(String resultsUrl) {
        this.resultsUrl = resultsUrl;
    }
}
