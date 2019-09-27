package com.lexalytics.semantria.client.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.LinkedList;
import java.util.List;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaxonomyNode extends AbstractConfigurationItem {
    private String name;
    private Boolean enforceParentMatching = false;
    private List<TaxonomyNode> children = new LinkedList<>();
    private List<TaxonomyTopic> topics = new LinkedList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getEnforceParentMatching() {
        return enforceParentMatching;
    }

    public void setEnforceParentMatching(Boolean enforceParentMatching) {
        this.enforceParentMatching = enforceParentMatching;
    }

    public List<TaxonomyNode> getChildren() {
        return children;
    }

    public void setChildren(List<TaxonomyNode> children) {
        this.children = children;
    }

    public List<TaxonomyTopic> getTopics() {
        return topics;
    }

    public void setTopics(List<TaxonomyTopic> topics) {
        this.topics = topics;
    }
}
