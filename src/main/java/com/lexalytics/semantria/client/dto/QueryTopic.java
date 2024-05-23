package com.lexalytics.semantria.client.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class QueryTopic extends AbstractConfigurationItem {
    private String name;
    private String query;
    private boolean hidden;

    @JsonCreator
    public QueryTopic(@JsonProperty("name") String name, @JsonProperty("query") String query, @JsonProperty("hidden") boolean hidden) {
        this.name = name;
        this.query = query;
        this.hidden = hidden;
    }

    public QueryTopic(@JsonProperty("name") String name, @JsonProperty("query") String query) {
        this.name = name;
        this.query = query;
        this.hidden = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }
}
