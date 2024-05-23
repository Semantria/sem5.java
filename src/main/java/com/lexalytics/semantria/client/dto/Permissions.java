package com.lexalytics.semantria.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.ArrayList;
import java.util.List;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Permissions {

    private List<Permission> basePermissions = new ArrayList<>();
    private List<Permission> configurationPermissions = new ArrayList<>();
    private List<Permission> modelPermissions = new ArrayList<>();
    private List<Permission> externalPermissions = new ArrayList<>();

    public List<Permission> getBasePermissions() {
        return basePermissions;
    }

    public void setBasePermissions(List<Permission> basePermissions) {
        this.basePermissions = basePermissions;
    }

    public List<Permission> getConfigurationPermissions() {
        return configurationPermissions;
    }

    public void setConfigurationPermissions(List<Permission> configurationPermissions) {
        this.configurationPermissions = configurationPermissions;
    }

    public List<Permission> getModelPermissions() {
        return modelPermissions;
    }

    public void setModelPermissions(List<Permission> modelPermissions) {
        this.modelPermissions = modelPermissions;
    }

    public List<Permission> getExternalPermissions() {
        return externalPermissions;
    }

    public void setExternalPermissions(List<Permission> userExternalPermissions) {
        this.externalPermissions = userExternalPermissions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Permissions that = (Permissions) o;
        return (basePermissions != null ? basePermissions.equals(that.basePermissions) : that.basePermissions == null)
                && (configurationPermissions != null ? configurationPermissions.equals(that.configurationPermissions) : that.configurationPermissions == null)
                && (modelPermissions != null ? modelPermissions.equals(that.modelPermissions) : that.modelPermissions == null)
                && (externalPermissions != null ? externalPermissions.equals(that.externalPermissions) : that.externalPermissions == null);
    }

    @Override
    public int hashCode() {
        int result = basePermissions != null ? basePermissions.hashCode() : 0;
        result = 31 * result + (configurationPermissions != null ? configurationPermissions.hashCode() : 0);
        result = 31 * result + (modelPermissions != null ? modelPermissions.hashCode() : 0);
        result = 31 * result + (externalPermissions != null ? externalPermissions.hashCode() : 0);
        return result;
    }
}
