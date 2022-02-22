package com.lexalytics.semantria.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;

public class AbstractConfigurationItem {
    String id;
    @JsonProperty("updated")
    private ZonedDateTime lastUpdated;

    @JsonProperty("cloned_from")
    private String clonedFrom;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ZonedDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(ZonedDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getClonedFrom() {
        return clonedFrom;
    }

    public void setClonedFrom(String clonedFrom) {
        this.clonedFrom = clonedFrom;
    }
}
