package com.lexalytics.semantria.client.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.ArrayList;
import java.util.List;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Entity {
    private Integer evidence;
    @JsonProperty("title")
    private String normalized;
    private Float sentimentScore;
    private String type;
    private String entityType;
    private Boolean isAbout;
    private Boolean confident;
    private List<Theme> themes;
    private List<Mention> mentions;
    private String label;
    private String sentimentPolarity;
    @JsonProperty("sentiment_phrases")
    private List<SentimentMention> sentimentMentions;

    public static Entity create(Integer evidence, String normalized, Float sentiment, String type, String entityType, Boolean isAbout,
                                Boolean confid, List<Mention> mentions, String label, String polarity, List<SentimentMention> sentimentMentions) {
        Entity entity = new Entity();

        entity.evidence = evidence;
        entity.normalized = normalized;
        entity.sentimentScore = sentiment;
        entity.type = type;
        entity.entityType = entityType;
        entity.isAbout = isAbout;
        entity.confident = confid;
        entity.mentions = mentions;
        entity.label = label;
        entity.sentimentPolarity = polarity;
        entity.sentimentMentions = sentimentMentions;

        return entity;
    }

    public Integer getEvidence() {
        return evidence;
    }

    public void setEvidence(Integer evidence) {
        this.evidence = evidence;
    }

    public String getNormalized() {
        return normalized;
    }

    public void setNormalized(String normalized) {
        this.normalized = normalized;
    }

    public Float getSentimentScore() {
        return sentimentScore;
    }

    public void setSentimentScore(Float sentimentScore) {
        this.sentimentScore = sentimentScore;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public Boolean getIsAbout() {
        return isAbout;
    }

    public void setIsAbout(Boolean about) {
        isAbout = about;
    }

    public Boolean getConfident() {
        return confident;
    }

    public void setConfident(Boolean confident) {
        this.confident = confident;
    }

    public List<Theme> getThemes() {
        return themes;
    }

    public void setThemes(List<Theme> themes) {
        this.themes = themes;
    }

    public List<Mention> getMentions() {
        return mentions;
    }

    public void setMentions(List<Mention> mentions) {
        this.mentions = mentions;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getSentimentPolarity() {
        return sentimentPolarity;
    }

    public void setSentimentPolarity(String sentimentPolarity) {
        this.sentimentPolarity = sentimentPolarity;
    }

    public void addTheme(Theme theme) {
        if (themes == null) {
            themes = new ArrayList<>();
        }

        themes.add(theme);
    }

    public List<SentimentMention> getSentimentMentions() {
        return sentimentMentions;
    }

    public void setSentimentMentions(List<SentimentMention> sentimentMentions) {
        this.sentimentMentions = sentimentMentions;
    }
}
