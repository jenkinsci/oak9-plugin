package io.jenkins.plugins.oak9.model;

import java.util.List;

public class ValidationResult {
    private String requestId;
    private String repositoryName;
    private String timeStamp;
    private String[] resources;
    private String status;
    private String errorMessage;
    private String source;
    private List<DesignGap> designGaps;
    private ResultRef resultRef;
    private List<Result> results;
    private String orgId;
    private String projectId;
    private String version;
    private String entityType;

    public String[] getResources() {
        return resources;
    }

    public void setResources(String[] resources) {
        this.resources = resources;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<DesignGap> getDesignGaps() {
        return designGaps;
    }

    public void setDesignGaps(List<DesignGap> designGaps) {
        this.designGaps = designGaps;
    }

    public ResultRef getResultRef() {
        return resultRef;
    }

    public void setResultRef(ResultRef resultRef) {
        this.resultRef = resultRef;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public ValidationResult() {
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getRequestId() {
        return this.requestId;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
