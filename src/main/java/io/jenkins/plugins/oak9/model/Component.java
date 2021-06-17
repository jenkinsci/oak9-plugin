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
        "id",
        "name",
        "type",
        "typeId",
        "x",
        "y",
        "connections",
        "collapsed"
})

/**
 * This is generated code from the jsonschema2pojo library based upon the oak9 Swagger docs. It is not recommended to
 * make manual changes to this code.
 *
 * see https://github.com/joelittlejohn/jsonschema2pojo for usage instructions
 */
@Generated("jsonschema2pojo")
public class Component {

    @JsonProperty("id")
    private Object id;
    @JsonProperty("name")
    private Object name;
    @JsonProperty("type")
    private Object type;
    @JsonProperty("typeId")
    private String typeId;
    @JsonProperty("x")
    private Integer x;
    @JsonProperty("y")
    private Integer y;
    @JsonProperty("connections")
    private List<Object> connections = null;
    @JsonProperty("collapsed")
    private Boolean collapsed;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public Component() {
    }

    /**
     *
     * @param collapsed
     * @param name
     * @param x
     * @param y
     * @param typeId
     * @param id
     * @param type
     * @param connections
     */
    public Component(Object id, Object name, Object type, String typeId, Integer x, Integer y, List<Object> connections, Boolean collapsed) {
        super();
        this.id = id;
        this.name = name;
        this.type = type;
        this.typeId = typeId;
        this.x = x;
        this.y = y;
        this.connections = connections;
        this.collapsed = collapsed;
    }

    @JsonProperty("id")
    public Object getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Object id) {
        this.id = id;
    }

    @JsonProperty("name")
    public Object getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(Object name) {
        this.name = name;
    }

    @JsonProperty("type")
    public Object getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(Object type) {
        this.type = type;
    }

    @JsonProperty("typeId")
    public String getTypeId() {
        return typeId;
    }

    @JsonProperty("typeId")
    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    @JsonProperty("x")
    public Integer getX() {
        return x;
    }

    @JsonProperty("x")
    public void setX(Integer x) {
        this.x = x;
    }

    @JsonProperty("y")
    public Integer getY() {
        return y;
    }

    @JsonProperty("y")
    public void setY(Integer y) {
        this.y = y;
    }

    @JsonProperty("connections")
    public List<Object> getConnections() {
        return connections;
    }

    @JsonProperty("connections")
    public void setConnections(List<Object> connections) {
        this.connections = connections;
    }

    @JsonProperty("collapsed")
    public Boolean getCollapsed() {
        return collapsed;
    }

    @JsonProperty("collapsed")
    public void setCollapsed(Boolean collapsed) {
        this.collapsed = collapsed;
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