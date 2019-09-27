package com.lexalytics.semantria.client.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Permission {
    private String userId;
    private String groupId;
    private String externalId;
    private String configId;
    private String modelId;
    private String permissionType;

    private String permissionId;

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(String permissionType) {
        this.permissionType = permissionType;
    }

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Permission that = (Permission) o;
        return (configId != null ? configId.equals(that.configId) : that.configId == null)
                && (modelId != null ? modelId.equals(that.modelId) : that.modelId == null)
                && (externalId != null ? externalId.equals(that.externalId) : that.externalId == null)
                && (groupId != null ? groupId.equals(that.groupId) : that.groupId == null)
                && (permissionId != null ? permissionId.equals(that.permissionId) : that.permissionId == null)
                && (permissionType != null ? permissionType.equals(that.permissionType) : that.permissionType == null)
                && (userId != null ? userId.equals(that.userId) : that.userId == null);
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (groupId != null ? groupId.hashCode() : 0);
        result = 31 * result + (externalId != null ? externalId.hashCode() : 0);
        result = 31 * result + (configId != null ? configId.hashCode() : 0);
        result = 31 * result + (modelId != null ? modelId.hashCode() : 0);
        result = 31 * result + (permissionType != null ? permissionType.hashCode() : 0);
        result = 31 * result + (permissionId != null ? permissionId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(this.getClass().getCanonicalName());
        builder.append(":");
        builder.append(permissionId);
        builder.append(":");
        builder.append(permissionType);
        builder.append(":");
        builder.append(configId != null ? configId : "null");
        builder.append(":");
        builder.append(modelId != null ? modelId : "null");
        builder.append(":");
        builder.append(externalId != null ? externalId : "null");
        return builder.toString();
    }
}
