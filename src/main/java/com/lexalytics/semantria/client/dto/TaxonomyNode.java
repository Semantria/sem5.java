package com.lexalytics.semantria.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.LinkedList;
import java.util.List;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaxonomyNode extends AbstractConfigurationItem {
    private String name;
    private Boolean enforceParentMatching = false;
    private List<TaxonomyNode> children = new LinkedList<>();
    private List<TaxonomyElement> elements = new LinkedList<>();

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

    public List<TaxonomyElement> getElements() {
        return elements;
    }

    public void setElements(List<TaxonomyElement> elements) {
        this.elements = elements;
    }
}
