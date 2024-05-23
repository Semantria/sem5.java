package com.lexalytics.semantria.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SecurityPermission {
    private String permissionType;
    private String permissionId;
    private String description;
    private String configId;
    private String modelId;
    private String externalId;
    private String groupId;

    public String getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(String permissionType) {
        this.permissionType = permissionType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
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

        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }

        SecurityPermission that = (SecurityPermission) o;

        if (configId != null ? !configId.equals(that.configId) : that.configId != null) {
            return false;
        }
        if (modelId != null ? !modelId.equals(that.modelId) : that.modelId != null) {
            return false;
        }
        if (description != null ? !description.equals(that.description) : that.description != null) {
            return false;
        }
        if (externalId != null ? !externalId.equals(that.externalId) : that.externalId != null) {
            return false;
        }
        if (permissionId != null ? !permissionId.equals(that.permissionId) : that.permissionId != null) {
            return false;
        }

        if (groupId != null ? !groupId.equals(that.groupId) : that.groupId != null) {
            return false;
        }


        return permissionType != null ? permissionType.equals(that.permissionType) : that.permissionType == null;
    }

    @Override
    public int hashCode() {
        int result = permissionType != null ? permissionType.hashCode() : 0;
        result = 31 * result + (permissionId != null ? permissionId.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (configId != null ? configId.hashCode() : 0);
        result = 31 * result + (modelId != null ? modelId.hashCode() : 0);
        result = 31 * result + (externalId != null ? externalId.hashCode() : 0);
        result = 31 * result + (groupId != null ? groupId.hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("type");
        builder.append(permissionType);
        if (configId != null) {
            builder.append(":");
            builder.append("config");
            builder.append(configId);
        }
        if (modelId != null) {
            builder.append(":");
            builder.append("model");
            builder.append(modelId);
        }
        if (externalId != null) {
            builder.append(":");
            builder.append("external");
            builder.append(externalId);
        }
        return builder.toString();
    }
}
