package io.jenkins.plugins.oak9.model;

import java.util.List;

public class DesignGap {
    private String requirementId;
    private String requirementName;
    private String source;
    private String resourceName;
    private String resourceId;
    private String resourceType;
    private String oak9Guidance;
    private List<Violation> violations;


    public DesignGap(String requirementId,
                     String requirementName,
                     String source,
                     String resourceName,
                     String resourceId,
                     String resourceType,
                     String oak9Guidance,
                     List<Violation> violations) {
        this.requirementId = requirementId;
        this.requirementName = requirementName;
        this.source = source;
        this.resourceName = resourceName;
        this.resourceId = resourceId;
        this.resourceType = resourceType;
        this.oak9Guidance = oak9Guidance;
        this.violations = violations;
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getOak9Guidance() {
        return oak9Guidance;
    }

    public void setOak9Guidance(String oak9Guidance) {
        this.oak9Guidance = oak9Guidance;
    }

    public List<Violation> getViolations() {
        return violations;
    }

    public void setViolations(List<Violation> violations) {
        this.violations = violations;
    }
}