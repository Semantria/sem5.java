package com.lexalytics.semantria.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Topic {
    private String id = "";
    @JsonProperty("hitcount")
    private Integer hitCount;
    private Float sentimentScore;
    private Float strengthScore;
    private String title = "";
    private String type = "";
    private List<Topic> categories;
    private List<Topic> topics;
    private List<Mention> mentions;
    private String sentimentPolarity;
    @JsonProperty("sentiment_phrases")
    private List<SentimentMention> sentimentMentions;
    private List<Theme> themes;
    private JsonNode purpose;

    public static Topic create(int hitCount, float score, String title, String type, float sentiment, String sentimentPolarity) {
        return create(hitCount, score, title, type, sentiment, sentimentPolarity, null, null, null);
    }

    public static Topic create(int hitCount, float score, String title, String type, float sentiment, String sentimentPolarity,
                               List<SentimentMention> sentimentMentions) {
        return create(hitCount, score, title, type, sentiment, sentimentPolarity, sentimentMentions, null, null);
    }

    public static Topic create(int hitCount, float score, String title, String type, float sentiment, String sentimentPolarity,
                               List<SentimentMention> sentimentMentions, List<Mention> mentions, JsonNode purpose) {
        Topic t = new Topic();
        t.hitCount = hitCount;
        t.type = type;
        t.strengthScore = score;
        t.sentimentScore = sentiment;
        t.title = title;
        t.sentimentPolarity = sentimentPolarity;
        t.sentimentMentions = sentimentMentions;
        t.mentions = mentions;
        t.purpose = purpose;
        return t;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getHitCount() {
        return hitCount;
    }

    public void setHitCount(Integer hitCount) {
        this.hitCount = hitCount;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Topic> getCategories() {
        return categories;
    }

    public void setCategories(List<Topic> categories) {
        this.categories = categories;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    public List<Mention> getMentions() {
        return mentions;
    }

    public void setMentions(List<Mention> mentions) {
        this.mentions = mentions;
    }

    public String getSentimentPolarity() {
        return sentimentPolarity;
    }

    public void setSentimentPolarity(String sentimentPolarity) {
        this.sentimentPolarity = sentimentPolarity;
    }

    public List<SentimentMention> getSentimentMentions() {
        return sentimentMentions;
    }

    public void setSentimentMentions(List<SentimentMention> sentimentMentions) {
        this.sentimentMentions = sentimentMentions;
    }

    public List<Theme> getThemes() {
        return themes;
    }

    public void setThemes(List<Theme> themes) {
        this.themes = themes;
    }

    public JsonNode getPurpose() {
        return purpose;
    }

    public void setPurpose(JsonNode purpose) {
        this.purpose = purpose;
    }
}
