package com.lexalytics.semantria.client.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DocumentResult {
    private String id;
    private String configId;
    private String languageId;
    private String accountId;
    private String userId;
    private String jobId;
    private String tag;
    private JsonNode metadata;
    private String creationDate;
    private String status;
    private String summary;

    @JsonProperty("taxonomy")
    @JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
    private List<TaxonomyNodeObject> taxonomies;

    private String sourceText;

    @JsonProperty("sentiment_score")
    private Float score;
    private String sentimentPolarity;
    private String language;
    private Float languageScore;

    private ModelSentiment modelSentiment;

    @JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
    private List<Topic> topics;
    private List<Entity> entities;
    private List<Theme> themes;
    private List<Phrase> phrases;
    private List<Sentence> details;
    private List<Relation> relations;
    private List<Opinion> opinions;
    private List<Topic> autoCategories;
    private List<Intention> intentions;

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public String getSentimentPolarity() {
        return sentimentPolarity;
    }

    public void setSentimentPolarity(String sentimentPolarity) {
        this.sentimentPolarity = sentimentPolarity;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

    public List<Theme> getThemes() {
        return themes;
    }

    public void setThemes(List<Theme> themes) {
        this.themes = themes;
    }

    public List<Phrase> getPhrases() {
        return phrases;
    }

    public void setPhrases(List<Phrase> phrases) {
        this.phrases = phrases;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Float getLanguageScore() {
        return languageScore;
    }

    public void setLanguageScore(Float languageScore) {
        this.languageScore = languageScore;
    }

    public List<Sentence> getDetails() {
        return details;
    }

    public void setDetails(List<Sentence> details) {
        this.details = details;
    }

    public List<Relation> getRelations() {
        return relations;
    }

    public void setRelations(List<Relation> relations) {
        this.relations = relations;
    }

    public String getSourceText() {
        return sourceText;
    }

    public void setSourceText(String sourceText) {
        this.sourceText = sourceText;
    }

    public List<Opinion> getOpinions() {
        return opinions;
    }

    public void setOpinions(List<Opinion> opinions) {
        this.opinions = opinions;
    }

    public List<Topic> getAutoCategories() {
        return autoCategories;
    }

    public void setAutoCategories(List<Topic> autoCategories) {
        this.autoCategories = autoCategories;
    }

    public List<Intention> getIntentions() {
        return intentions;
    }

    public void setIntentions(List<Intention> intentions) {
        this.intentions = intentions;
    }

    public ModelSentiment getModelSentiment() {
        return modelSentiment;
    }

    public void setModelSentiment(ModelSentiment modelSentiment) {
        this.modelSentiment = modelSentiment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    public String getLanguageId() {
        return languageId;
    }

    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public JsonNode getMetadata() {
        return metadata;
    }

    public void setMetadata(JsonNode metadata) {
        this.metadata = metadata;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<TaxonomyNodeObject> getTaxonomies() {
        return taxonomies;
    }

    public void setTaxonomies(List<TaxonomyNodeObject> taxonomies) {
        this.taxonomies = taxonomies;
    }
}
