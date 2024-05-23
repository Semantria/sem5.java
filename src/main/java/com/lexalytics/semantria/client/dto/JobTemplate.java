package com.lexalytics.semantria.client.dto;

import java.time.ZonedDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class JobTemplate {
    private String id;
    private String name;
    private Integer version;
    private String notes;
    private Set<JobTemplateParameter> parameters = new LinkedHashSet<>();
    private JsonNode parametersExample;
    private ZonedDateTime creationDate;
    private ZonedDateTime modificationDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Set<JobTemplateParameter> getParameters() {
        return parameters;
    }

    public void setParameters(Set<JobTemplateParameter> parameters) {
        this.parameters = parameters;
    }

    public JsonNode getParametersExample() {
        return parametersExample;
    }

    public void setParametersExample(JsonNode parametersExample) {
        this.parametersExample = parametersExample;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public ZonedDateTime getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(ZonedDateTime modificationDate) {
        this.modificationDate = modificationDate;
    }
}
