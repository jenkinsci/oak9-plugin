package io.jenkins.plugins.oak9.model;

import java.util.HashMap;
import java.util.List;
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
        "repositoryName",
        "timeStamp",
        "resources",
        "status",
        "errorMessage",
        "source",
        "designGaps",
        "resultRef",
        "results",
        "orgId",
        "projectId",
        "version",
        "entityType"
})
@Generated("jsonschema2pojo")
public class Result {

    @JsonProperty("requestId")
    private String requestId;
    @JsonProperty("repositoryName")
    private Object repositoryName;
    @JsonProperty("timeStamp")
    private String timeStamp;
    @JsonProperty("resources")
    private List<Resource> resources = null;
    @JsonProperty("status")
    private String status;
    @JsonProperty("errorMessage")
    private Object errorMessage;
    @JsonProperty("source")
    private Object source;
    @JsonProperty("designGaps")
    private List<DesignGap> designGaps = null;
    @JsonProperty("resultRef")
    private ResultRef resultRef;
    @JsonProperty("results")
    private List<Object> results = null;
    @JsonProperty("orgId")
    private String orgId;
    @JsonProperty("projectId")
    private String projectId;
    @JsonProperty("version")
    private String version;
    @JsonProperty("entityType")
    private String entityType;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public Result() {
    }

    /**
     *
     * @param designGaps
     * @param entityType
     * @param errorMessage
     * @param resources
     * @param source
     * @param repositoryName
     * @param version
     * @param orgId
     * @param timeStamp
     * @param requestId
     * @param resultRef
     * @param results
     * @param projectId
     * @param status
     */
    public Result(String requestId, Object repositoryName, String timeStamp, List<Resource> resources, String status, Object errorMessage, Object source, List<DesignGap> designGaps, ResultRef resultRef, List<Object> results, String orgId, String projectId, String version, String entityType) {
        super();
        this.requestId = requestId;
        this.repositoryName = repositoryName;
        this.timeStamp = timeStamp;
        this.resources = resources;
        this.status = status;
        this.errorMessage = errorMessage;
        this.source = source;
        this.designGaps = designGaps;
        this.resultRef = resultRef;
        this.results = results;
        this.orgId = orgId;
        this.projectId = projectId;
        this.version = version;
        this.entityType = entityType;
    }

    @JsonProperty("requestId")
    public String getRequestId() {
        return requestId;
    }

    @JsonProperty("requestId")
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @JsonProperty("repositoryName")
    public Object getRepositoryName() {
        return repositoryName;
    }

    @JsonProperty("repositoryName")
    public void setRepositoryName(Object repositoryName) {
        this.repositoryName = repositoryName;
    }

    @JsonProperty("timeStamp")
    public String getTimeStamp() {
        return timeStamp;
    }

    @JsonProperty("timeStamp")
    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    @JsonProperty("resources")
    public List<Resource> getResources() {
        return resources;
    }

    @JsonProperty("resources")
    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("errorMessage")
    public Object getErrorMessage() {
        return errorMessage;
    }

    @JsonProperty("errorMessage")
    public void setErrorMessage(Object errorMessage) {
        this.errorMessage = errorMessage;
    }

    @JsonProperty("source")
    public Object getSource() {
        return source;
    }

    @JsonProperty("source")
    public void setSource(Object source) {
        this.source = source;
    }

    @JsonProperty("designGaps")
    public List<DesignGap> getDesignGaps() {
        return designGaps;
    }

    @JsonProperty("designGaps")
    public void setDesignGaps(List<DesignGap> designGaps) {
        this.designGaps = designGaps;
    }

    @JsonProperty("resultRef")
    public ResultRef getResultRef() {
        return resultRef;
    }

    @JsonProperty("resultRef")
    public void setResultRef(ResultRef resultRef) {
        this.resultRef = resultRef;
    }

    @JsonProperty("results")
    public List<Object> getResults() {
        return results;
    }

    @JsonProperty("results")
    public void setResults(List<Object> results) {
        this.results = results;
    }

    @JsonProperty("orgId")
    public String getOrgId() {
        return orgId;
    }

    @JsonProperty("orgId")
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    @JsonProperty("projectId")
    public String getProjectId() {
        return projectId;
    }

    @JsonProperty("projectId")
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    @JsonProperty("version")
    public String getVersion() {
        return version;
    }

    @JsonProperty("version")
    public void setVersion(String version) {
        this.version = version;
    }

    @JsonProperty("entityType")
    public String getEntityType() {
        return entityType;
    }

    @JsonProperty("entityType")
    public void setEntityType(String entityType) {
        this.entityType = entityType;
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