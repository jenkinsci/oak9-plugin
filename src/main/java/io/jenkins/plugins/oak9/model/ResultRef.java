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
        "entityType",
        "refType",
        "s3RefKey",
        "docRefPrimaryKey",
        "docRefEntityKey"
})

/**
 * This is generated code from the jsonschema2pojo library based upon the oak9 Swagger docs. It is not recommended to
 * make manual changes to this code.
 *
 * see https://github.com/joelittlejohn/jsonschema2pojo for usage instructions
 */
@Generated("jsonschema2pojo")
public class ResultRef {

    @JsonProperty("entityType")
    private String entityType;
    @JsonProperty("refType")
    private String refType;
    @JsonProperty("s3RefKey")
    private String s3RefKey;
    @JsonProperty("docRefPrimaryKey")
    private String docRefPrimaryKey;
    @JsonProperty("docRefEntityKey")
    private String docRefEntityKey;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public ResultRef() {
    }

    /**
     *
     * @param entityType
     * @param refType
     * @param docRefPrimaryKey
     * @param docRefEntityKey
     * @param s3RefKey
     */
    public ResultRef(String entityType, String refType, String s3RefKey, String docRefPrimaryKey, String docRefEntityKey) {
        super();
        this.entityType = entityType;
        this.refType = refType;
        this.s3RefKey = s3RefKey;
        this.docRefPrimaryKey = docRefPrimaryKey;
        this.docRefEntityKey = docRefEntityKey;
    }

    @JsonProperty("entityType")
    public String getEntityType() {
        return entityType;
    }

    @JsonProperty("entityType")
    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    @JsonProperty("refType")
    public String getRefType() {
        return refType;
    }

    @JsonProperty("refType")
    public void setRefType(String refType) {
        this.refType = refType;
    }

    @JsonProperty("s3RefKey")
    public String getS3RefKey() {
        return s3RefKey;
    }

    @JsonProperty("s3RefKey")
    public void setS3RefKey(String s3RefKey) {
        this.s3RefKey = s3RefKey;
    }

    @JsonProperty("docRefPrimaryKey")
    public String getDocRefPrimaryKey() {
        return docRefPrimaryKey;
    }

    @JsonProperty("docRefPrimaryKey")
    public void setDocRefPrimaryKey(String docRefPrimaryKey) {
        this.docRefPrimaryKey = docRefPrimaryKey;
    }

    @JsonProperty("docRefEntityKey")
    public String getDocRefEntityKey() {
        return docRefEntityKey;
    }

    @JsonProperty("docRefEntityKey")
    public void setDocRefEntityKey(String docRefEntityKey) {
        this.docRefEntityKey = docRefEntityKey;
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