package com.lexalytics.semantria.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Account extends AccountBrief {
    private Set<Language> languages;

    private Set<String> features;

    private Map<String, Long> limits;

    @JsonProperty("industry_packs")
    private Set<IndustryPack> industryPacks;

    @JsonProperty("job_templates")
    private Set<JobTemplate> jobTemplates = new HashSet<>();

    @JsonProperty("application_seats")
    private Set<String> applicationSeats = new HashSet<>();

    @JsonProperty("job_ids")
    private Set<String> jobIds = new HashSet<>();

    private Map<String, Long> balances;

    @JsonProperty("balance_refresh")
    private Map<String, Long> balanceRefresh;

    @JsonProperty("external_id")
    private String externalId;

    @JsonProperty("alternate_email")
    private String alternateEmail;

    @JsonProperty("external_resources")
    private Set<ExternalResource> externalResources = new HashSet<>();

    public Set<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(Set<Language> languages) {
        this.languages = languages;
    }

    public Set<String> getFeatures() {
        return features;
    }

    public void setFeatures(Set<String> features) {
        this.features = features;
    }

    public Map<String, Long> getLimits() {
        return limits;
    }

    public void setLimits(Map<String, Long> limits) {
        this.limits = limits;
    }

    public Set<IndustryPack> getIndustryPacks() {
        return industryPacks;
    }

    public void setIndustryPacks(Set<IndustryPack> industryPacks) {
        this.industryPacks = industryPacks;
    }

    public Set<JobTemplate> getJobTemplates() {
        return jobTemplates;
    }

    public void setJobTemplates(Set<JobTemplate> jobTemplates) {
        this.jobTemplates = jobTemplates;
    }

    public Set<String> getApplicationSeats() {
        return applicationSeats;
    }

    public void setApplicationSeats(Set<String> applicationSeats) {
        this.applicationSeats = applicationSeats;
    }

    public Set<String> getJobIds() {
        return jobIds;
    }

    public void setJobIds(Set<String> jobIds) {
        this.jobIds = jobIds;
    }

    public Map<String, Long> getBalances() {
        return balances;
    }

    public void setBalances(Map<String, Long> balances) {
        this.balances = balances;
    }

    public Map<String, Long> getBalanceRefresh() {
        return balanceRefresh;
    }

    public void setBalanceRefresh(Map<String, Long> balanceRefresh) {
        this.balanceRefresh = balanceRefresh;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getAlternateEmail() {
        return alternateEmail;
    }

    public void setAlternateEmail(String alternateEmail) {
        this.alternateEmail = alternateEmail;
    }

    public Set<ExternalResource> getExternalResources() {
        return externalResources;
    }

    public void setExternalResources(Set<ExternalResource> externalResources) {
        this.externalResources = externalResources;
    }
}
