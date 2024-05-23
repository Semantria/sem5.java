package com.lexalytics.semantria.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Relation {
    private String relationType;
    private String extra;
    private Float confidenceScore;
    private List<RelationEntity> entities;
    private String type;

    public static Relation create(String relationType, String extra, Float confidenceScore, String type) {
        Relation relation = new Relation();
        relation.relationType = relationType;
        relation.extra = extra;
        relation.confidenceScore = confidenceScore;
        relation.type = type;
        return relation;
    }

    public String getRelationType() {
        return relationType;
    }

    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public Float getConfidenceScore() {
        return confidenceScore;
    }

    public void setConfidenceScore(Float confidenceScore) {
        this.confidenceScore = confidenceScore;
    }

    public List<RelationEntity> getEntities() {
        return entities;
    }

    public void setEntities(List<RelationEntity> entities) {
        this.entities = entities;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
