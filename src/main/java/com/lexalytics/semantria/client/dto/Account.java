package com.lexalytics.semantria.client.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Account extends AccountBrief {
    private Set<Language> languages;

    private Set<String> features;

    private Map<String, Long> limits;

    @JsonProperty("industry_packs")
    private Set<IndustryPack> industryPacks;

    @JsonProperty("application_seats")
    private Set<String> applicationSeats = new HashSet<>();

    @JsonProperty("job_ids")
    private Set<String> jobIds = new HashSet<>();

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
}
