package com.lexalytics.semantria.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Phrase {
    private String title;
    private Float sentimentScore;
    @JsonProperty("is_negated")
    private Boolean negated;
    private String supportingPhrase;
    private String type;
    @JsonProperty("is_intensified")
    private Boolean intensified;
    private String intensifyingPhrase;
    private Integer offset;
    private Integer length;
    private Integer section;
    private String sentimentPolarity;

    public static Phrase create(String title, Float score, Boolean negated, String supportingPhrase, Boolean intensified, String intensifyingPhrase,
                                String type, int offset, int length, String polarity) {
        return create(title, score, negated, supportingPhrase, intensified, intensifyingPhrase,
                type, offset, length, 0, polarity);
    }

    public static Phrase create(String title, Float score, Boolean negated, String supportingPhrase, Boolean intensified, String intensifyingPhrase,
                                String type, int offset, int length, int section, String polarity)
    {
        Phrase phrase = new Phrase();
        phrase.title = title;
        phrase.sentimentScore = score;
        phrase.negated = negated;
        phrase.supportingPhrase = supportingPhrase;
        phrase.intensified = intensified;
        phrase.intensifyingPhrase = intensifyingPhrase;
        phrase.offset = offset;
        phrase.length = length;
        phrase.section = section;
        phrase.type = type;
        phrase.sentimentPolarity = polarity;
        return phrase;
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

    public Boolean getNegated() {
        return negated;
    }

    public void setNegated(Boolean negated) {
        this.negated = negated;
    }

    public String getSupportingPhrase() {
        return supportingPhrase;
    }

    public void setSupportingPhrase(String supportingPhrase) {
        this.supportingPhrase = supportingPhrase;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getIntensified() {
        return intensified;
    }

    public void setIntensified(Boolean intensified) {
        this.intensified = intensified;
    }

    public String getIntensifyingPhrase() {
        return intensifyingPhrase;
    }

    public void setIntensifyingPhrase(String intensifyingPhrase) {
        this.intensifyingPhrase = intensifyingPhrase;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getSentimentPolarity() {
        return sentimentPolarity;
    }

    public void setSentimentPolarity(String sentimentPolarity) {
        this.sentimentPolarity = sentimentPolarity;
    }
}
