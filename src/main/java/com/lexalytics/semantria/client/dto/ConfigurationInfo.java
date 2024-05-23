package com.lexalytics.semantria.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConfigurationInfo {
    private long blacklistCount = 0;
    private long conceptTopicsCount = 0;
    private long queryTopicCount = 0;
    private long modelsCount = 0;
    private long sentimentPhrasesCount = 0;
    private long taxonomyCount = 0;
    private long userEntitiesCount = 0;

    public long getBlacklistCount() {
        return blacklistCount;
    }

    public void setBlacklistCount(long blacklistCount) {
        this.blacklistCount = blacklistCount;
    }

    public long getConceptTopicsCount() {
        return conceptTopicsCount;
    }

    public void setConceptTopicsCount(long conceptTopicsCount) {
        this.conceptTopicsCount = conceptTopicsCount;
    }

    public long getQueryTopicCount() {
        return queryTopicCount;
    }

    public void setQueryTopicCount(long queryTopicCount) {
        this.queryTopicCount = queryTopicCount;
    }

    public long getModelsCount() {
        return modelsCount;
    }

    public void setModelsCount(long modelsCount) {
        this.modelsCount = modelsCount;
    }

    public long getSentimentPhrasesCount() {
        return sentimentPhrasesCount;
    }

    public void setSentimentPhrasesCount(long sentimentPhrasesCount) {
        this.sentimentPhrasesCount = sentimentPhrasesCount;
    }

    public long getTaxonomyCount() {
        return taxonomyCount;
    }

    public void setTaxonomyCount(long taxonomyCount) {
        this.taxonomyCount = taxonomyCount;
    }

    public long getUserEntitiesCount() {
        return userEntitiesCount;
    }

    public void setUserEntitiesCount(long userEntitiesCount) {
        this.userEntitiesCount = userEntitiesCount;
    }
}
