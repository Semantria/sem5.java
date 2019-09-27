package com.lexalytics.semantria.client.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SentimentMention {
    @JsonProperty("score")
    private float sentimentScore;
    private int modified;
    private int type;
    private List<SentimentMentionPhrase> supportingPhrases;
    private SentimentMentionPhrase phrase;

    public static SentimentMention create(float sentimentScore, int modified, int type, List<SentimentMentionPhrase> supportingPhrases,
                                          SentimentMentionPhrase phrase) {
        SentimentMention mention = new SentimentMention();
        mention.sentimentScore = sentimentScore;
        mention.modified = modified;
        mention.type = type;
        mention.supportingPhrases = supportingPhrases;
        mention.phrase = phrase;
        return mention;
    }

    public float getSentimentScore() {
        return sentimentScore;
    }

    public void setSentimentScore(float sentimentScore) {
        this.sentimentScore = sentimentScore;
    }

    public int getModified() {
        return modified;
    }

    public void setModified(int modified) {
        this.modified = modified;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<SentimentMentionPhrase> getSupportingPhrases() {
        return supportingPhrases;
    }

    public void setSupportingPhrases(List<SentimentMentionPhrase> supportingPhrases) {
        this.supportingPhrases = supportingPhrases;
    }

    public SentimentMentionPhrase getPhrase() {
        return phrase;
    }

    public void setPhrase(SentimentMentionPhrase phrase) {
        this.phrase = phrase;
    }
}
