package com.lexalytics.semantria.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Word {
    private String type;
    private String tag;
    private String title;
    private String stemmed;
    private Float sentimentScore;
    private Boolean isNegated;

    public static Word create(String type, String title, String stemmed, String tag, Float sentimentScore, Boolean isNegated) {
        Word w = new Word();
        w.type = type;
        w.title = title;
        w.stemmed = stemmed;
        w.tag = tag;
        w.sentimentScore = sentimentScore;
        w.isNegated = isNegated;
        return w;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStemmed() {
        return stemmed;
    }

    public void setStemmed(String stemmed) {
        this.stemmed = stemmed;
    }

    public Float getSentimentScore() {
        return sentimentScore;
    }

    public void setSentimentScore(Float sentimentScore) {
        this.sentimentScore = sentimentScore;
    }

    public Boolean getIsNegated() {
        return isNegated;
    }

    public void setIsNegated(Boolean negated) {
        isNegated = negated;
    }
}
