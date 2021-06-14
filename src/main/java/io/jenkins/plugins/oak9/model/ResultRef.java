package io.jenkins.plugins.oak9.model;

public class ResultRef {
    private String entityType;
    private String refType;
    private String s3RefKey;
    private String docRefPrimaryKey;
    private String docRefEntityKey;

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getRefType() {
        return refType;
    }

    public void setRefType(String refType) {
        this.refType = refType;
    }

    public String getS3RefKey() {
        return s3RefKey;
    }

    public void setS3RefKey(String s3RefKey) {
        this.s3RefKey = s3RefKey;
    }

    public String getDocRefPrimaryKey() {
        return docRefPrimaryKey;
    }

    public void setDocRefPrimaryKey(String docRefPrimaryKey) {
        this.docRefPrimaryKey = docRefPrimaryKey;
    }

    public String getDocRefEntityKey() {
        return docRefEntityKey;
    }

    public void setDocRefEntityKey(String docRefEntityKey) {
        this.docRefEntityKey = docRefEntityKey;
    }
}
