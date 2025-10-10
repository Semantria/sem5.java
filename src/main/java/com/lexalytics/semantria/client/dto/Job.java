package com.lexalytics.semantria.client.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.node.ObjectNode;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Job extends JobBrief {

    private List<ObjectNode> datasourceProjects;

    public List<ObjectNode> getDatasourceProjects() {
        return datasourceProjects;
    }

    public void setDatasourceProjects(List<ObjectNode> datasourceProjects) {
        this.datasourceProjects = datasourceProjects;
    }
}
