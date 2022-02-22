package com.lexalytics.semantria.client.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConfigurationRouteEntry {
    private String configRouteId;
    private String configRouteName;

    public String getConfigRouteId() {
        return configRouteId;
    }

    public void setConfigRouteId(String configRouteId) {
        this.configRouteId = configRouteId;
    }

    public String getConfigRouteName() {
        return configRouteName;
    }

    public void setConfigRouteName(String configRouteName) {
        this.configRouteName = configRouteName;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        if (!super.equals(object)) {
            return false;
        }
        ConfigurationRouteEntry that = (ConfigurationRouteEntry) object;
        return java.util.Objects.equals(configRouteId, that.configRouteId) &&
                java.util.Objects.equals(configRouteName, that.configRouteName);
    }

    public int hashCode() {
        return configRouteId != null? configRouteId.hashCode() : 0;
    }
}
