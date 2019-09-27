package com.lexalytics.semantria.client.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class QueryTopic extends AbstractConfigurationItem {
    private String name;
    private String query;

    @JsonCreator
    public QueryTopic(@JsonProperty("name") String name, @JsonProperty("query") String query) {
        this.name = name;
        this.query = query;
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
}
