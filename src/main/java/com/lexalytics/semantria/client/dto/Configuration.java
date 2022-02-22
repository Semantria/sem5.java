package com.lexalytics.semantria.client.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Configuration {
    private String id;
    private String name;

    private List<String> settings;

    private String languageId;

    private String languageName;
    private String version;
    @JsonProperty("updated")
    private ZonedDateTime lastUpdated;
    @JsonProperty("is_autoresponse_enabled")
    private boolean autoResponseEnabled;

    private String callbackUrl;

    private boolean oneSentenceMode;

    private boolean processHtml;

    private boolean detectLanguage;

    private Integer alphanumericThreshold;

    private Float conceptTopicsThreshold;

    private Integer entitiesThreshold;

    private Integer summarySize;

    private String posTypes;

    private String templateConfig;

    private Set<String> features = new HashSet<>();

    private String notes;

    private Set<String> tags = new HashSet<>();

    private Set<ConfigurationRouteEntry> configRoutes = new HashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getSettings() {
        return settings;
    }

    public void setSettings(List<String> settings) {
        this.settings = settings;
    }

    public String getLanguageId() {
        return languageId;
    }

    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public ZonedDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = ZonedDateTime.parse(lastUpdated);
    }

    public boolean isAutoResponseEnabled() {
        return autoResponseEnabled;
    }

    public void setAutoResponseEnabled(boolean autoResponseEnabled) {
        this.autoResponseEnabled = autoResponseEnabled;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public boolean isOneSentenceMode() {
        return oneSentenceMode;
    }

    public void setOneSentenceMode(boolean oneSentenceMode) {
        this.oneSentenceMode = oneSentenceMode;
    }

    public boolean isProcessHtml() {
        return processHtml;
    }

    public void setProcessHtml(boolean processHtml) {
        this.processHtml = processHtml;
    }

    public boolean isDetectLanguage() {
        return detectLanguage;
    }

    public void setDetectLanguage(boolean detectLanguage) {
        this.detectLanguage = detectLanguage;
    }

    public Integer getAlphanumericThreshold() {
        return alphanumericThreshold;
    }

    public void setAlphanumericThreshold(Integer alphanumericThreshold) {
        this.alphanumericThreshold = alphanumericThreshold;
    }

    public Float getConceptTopicsThreshold() {
        return conceptTopicsThreshold;
    }

    public void setConceptTopicsThreshold(Float conceptTopicsThreshold) {
        this.conceptTopicsThreshold = conceptTopicsThreshold;
    }

    public Integer getEntitiesThreshold() {
        return entitiesThreshold;
    }

    public void setEntitiesThreshold(Integer entitiesThreshold) {
        this.entitiesThreshold = entitiesThreshold;
    }

    public Integer getSummarySize() {
        return summarySize;
    }

    public void setSummarySize(Integer summarySize) {
        this.summarySize = summarySize;
    }

    public String getPosTypes() {
        return posTypes;
    }

    public void setPosTypes(String posTypes) {
        this.posTypes = posTypes;
    }

    public Set<String> getFeatures() {
        return features;
    }

    public void setFeatures(Set<String> features) {
        this.features = features;
    }

    public String getTemplateConfig() {
        return templateConfig;
    }

    public void setTemplateConfig(String templateConfig) {
        this.templateConfig = templateConfig;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public Set<ConfigurationRouteEntry> getConfigRoutes() {
        return configRoutes;
    }

    public void setConfigRoutes(Set<ConfigurationRouteEntry> configRoutes) {
        this.configRoutes = configRoutes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Configuration that = (Configuration) o;
        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
