package com.lexalytics.semantria.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Theme {
    private Integer evidence;
    private String title;
    private Float sentimentScore;
    private Float strengthScore;
    private Boolean isAbout;
    private List<Mention> mentions;
    private String stemmed;
    private String normalized;
    @JsonProperty("sentiment_polarity")
    private String polarity;

    public static Theme create(Integer evidence, float sentimentScore, float strengthScore, String title, Boolean about, List<Mention> mentions,
                               String stemmed, String normalized, String polarity) {
        Theme theme = new Theme();
        theme.evidence = evidence;
        theme.sentimentScore = sentimentScore;
        theme.strengthScore = strengthScore;
        theme.title = title;
        theme.isAbout = about;
        theme.mentions = mentions;
        theme.normalized = normalized;
        theme.stemmed = stemmed;
        theme.polarity = polarity;
        return theme;
    }

    public Integer getEvidence() {
        return evidence;
    }

    public void setEvidence(Integer evidence) {
        this.evidence = evidence;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Float getSentimentScore() {
        return sentimentScore;
    }

    public void setSentimentScore(Float sentimentScore) {
        this.sentimentScore = sentimentScore;
    }

    public Float getStrengthScore() {
        return strengthScore;
    }

    public void setStrengthScore(Float strengthScore) {
        this.strengthScore = strengthScore;
    }

    public Boolean getIsAbout() {
        return isAbout;
    }

    public void setIsAbout(Boolean about) {
        isAbout = about;
    }

    public List<Mention> getMentions() {
        return mentions;
    }

    public void setMentions(List<Mention> mentions) {
        this.mentions = mentions;
    }

    public String getStemmed() {
        return stemmed;
    }

    public void setStemmed(String stemmed) {
        this.stemmed = stemmed;
    }

    public String getNormalized() {
        return normalized;
    }

    public void setNormalized(String normalized) {
        this.normalized = normalized;
    }

    public String getPolarity() {
        return polarity;
    }

    public void setPolarity(String polarity) {
        this.polarity = polarity;
    }
}
