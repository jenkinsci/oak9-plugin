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
        "resourceId",
        "resourceName",
        "lastScannedOn",
        "resourceType",
        "oak9ResourceTypeId",
        "blueprintId",
        "resourceConnections",
        "resourceLocation",
        "resourceSubscriptionId",
        "resourceTags",
        "configDictionary",
        "tags",
        "accessories",
        "context",
        "component",
        "violations"
})

/**
 * This is generated code from the jsonschema2pojo library based upon the oak9 Swagger docs. It is not recommended to
 * make manual changes to this code.
 *
 * see https://github.com/joelittlejohn/jsonschema2pojo for usage instructions
 */
@Generated("jsonschema2pojo")
public class Resource {

    @JsonProperty("resourceId")
    private Object resourceId;
    @JsonProperty("resourceName")
    private Object resourceName;
    @JsonProperty("lastScannedOn")
    private String lastScannedOn;
    @JsonProperty("resourceType")
    private String resourceType;
    @JsonProperty("oak9ResourceTypeId")
    private Object oak9ResourceTypeId;
    @JsonProperty("blueprintId")
    private Object blueprintId;
    @JsonProperty("resourceConnections")
    private List<Object> resourceConnections = null;
    @JsonProperty("resourceLocation")
    private Object resourceLocation;
    @JsonProperty("resourceSubscriptionId")
    private Object resourceSubscriptionId;
    @JsonProperty("resourceTags")
    private List<Object> resourceTags = null;
    @JsonProperty("configDictionary")
    private ConfigDictionary configDictionary;
    @JsonProperty("tags")
    private Tag tags;
    @JsonProperty("accessories")
    private Accessories accessories;
    @JsonProperty("context")
    private Object context;
    @JsonProperty("component")
    private Component component;
    @JsonProperty("violations")
    private List<Violation> violations = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public Resource() {
    }

    /**
     *
     * @param resourceId
     * @param accessories
     * @param violations
     * @param oak9ResourceTypeId
     * @param resourceSubscriptionId
     * @param resourceName
     * @param resourceLocation
     * @param blueprintId
     * @param tags
     * @param resourceTags
     * @param component
     * @param lastScannedOn
     * @param context
     * @param resourceConnections
     * @param configDictionary
     * @param resourceType
     */
    public Resource(Object resourceId, Object resourceName, String lastScannedOn, String resourceType, Object oak9ResourceTypeId, Object blueprintId, List<Object> resourceConnections, Object resourceLocation, Object resourceSubscriptionId, List<Object> resourceTags, ConfigDictionary configDictionary, Tag tags, Accessories accessories, Object context, Component component, List<Violation> violations) {
        super();
        this.resourceId = resourceId;
        this.resourceName = resourceName;
        this.lastScannedOn = lastScannedOn;
        this.resourceType = resourceType;
        this.oak9ResourceTypeId = oak9ResourceTypeId;
        this.blueprintId = blueprintId;
        this.resourceConnections = resourceConnections;
        this.resourceLocation = resourceLocation;
        this.resourceSubscriptionId = resourceSubscriptionId;
        this.resourceTags = resourceTags;
        this.configDictionary = configDictionary;
        this.tags = tags;
        this.accessories = accessories;
        this.context = context;
        this.component = component;
        this.violations = violations;
    }

    @JsonProperty("resourceId")
    public Object getResourceId() {
        return resourceId;
    }

    @JsonProperty("resourceId")
    public void setResourceId(Object resourceId) {
        this.resourceId = resourceId;
    }

    @JsonProperty("resourceName")
    public Object getResourceName() {
        return resourceName;
    }

    @JsonProperty("resourceName")
    public void setResourceName(Object resourceName) {
        this.resourceName = resourceName;
    }

    @JsonProperty("lastScannedOn")
    public String getLastScannedOn() {
        return lastScannedOn;
    }

    @JsonProperty("lastScannedOn")
    public void setLastScannedOn(String lastScannedOn) {
        this.lastScannedOn = lastScannedOn;
    }

    @JsonProperty("resourceType")
    public String getResourceType() {
        return resourceType;
    }

    @JsonProperty("resourceType")
    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    @JsonProperty("oak9ResourceTypeId")
    public Object getOak9ResourceTypeId() {
        return oak9ResourceTypeId;
    }

    @JsonProperty("oak9ResourceTypeId")
    public void setOak9ResourceTypeId(Object oak9ResourceTypeId) {
        this.oak9ResourceTypeId = oak9ResourceTypeId;
    }

    @JsonProperty("blueprintId")
    public Object getBlueprintId() {
        return blueprintId;
    }

    @JsonProperty("blueprintId")
    public void setBlueprintId(Object blueprintId) {
        this.blueprintId = blueprintId;
    }

    @JsonProperty("resourceConnections")
    public List<Object> getResourceConnections() {
        return resourceConnections;
    }

    @JsonProperty("resourceConnections")
    public void setResourceConnections(List<Object> resourceConnections) {
        this.resourceConnections = resourceConnections;
    }

    @JsonProperty("resourceLocation")
    public Object getResourceLocation() {
        return resourceLocation;
    }

    @JsonProperty("resourceLocation")
    public void setResourceLocation(Object resourceLocation) {
        this.resourceLocation = resourceLocation;
    }

    @JsonProperty("resourceSubscriptionId")
    public Object getResourceSubscriptionId() {
        return resourceSubscriptionId;
    }

    @JsonProperty("resourceSubscriptionId")
    public void setResourceSubscriptionId(Object resourceSubscriptionId) {
        this.resourceSubscriptionId = resourceSubscriptionId;
    }

    @JsonProperty("resourceTags")
    public List<Object> getResourceTags() {
        return resourceTags;
    }

    @JsonProperty("resourceTags")
    public void setResourceTags(List<Object> resourceTags) {
        this.resourceTags = resourceTags;
    }

    @JsonProperty("configDictionary")
    public ConfigDictionary getConfigDictionary() {
        return configDictionary;
    }

    @JsonProperty("configDictionary")
    public void setConfigDictionary(ConfigDictionary configDictionary) {
        this.configDictionary = configDictionary;
    }

    @JsonProperty("tags")
    public Tag getTags() {
        return tags;
    }

    @JsonProperty("tags")
    public void setTags(Tag tags) {
        this.tags = tags;
    }

    @JsonProperty("accessories")
    public Accessories getAccessories() {
        return accessories;
    }

    @JsonProperty("accessories")
    public void setAccessories(Accessories accessories) {
        this.accessories = accessories;
    }

    @JsonProperty("context")
    public Object getContext() {
        return context;
    }

    @JsonProperty("context")
    public void setContext(Object context) {
        this.context = context;
    }

    @JsonProperty("component")
    public Component getComponent() {
        return component;
    }

    @JsonProperty("component")
    public void setComponent(Component component) {
        this.component = component;
    }

    @JsonProperty("violations")
    public List<Violation> getViolations() {
        return violations;
    }

    @JsonProperty("violations")
    public void setViolations(List<Violation> violations) {
        this.violations = violations;
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