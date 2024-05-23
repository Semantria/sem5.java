package com.lexalytics.semantria.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class StatisticsRequest {
    @JsonProperty("configuration_id")
    private String configurationId;

    private String interval;

    @JsonProperty("from")
    private String fromDate;

    @JsonProperty("to")
    private String toDate;

    @JsonProperty("filter_by")
    private Map<String, String> filterBy;

    @JsonProperty("group_by")
    private Map<String, String> groupBy;

    public String getConfigurationId() { return configurationId; }

    public void setConfigurationId(String configurationId) { this.configurationId = configurationId; }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public Map<String, String> getFilterBy() {
        return filterBy;
    }

    public void setFilterBy(Map<String, String> filterBy) {
        this.filterBy = filterBy;
    }

    public Map<String, String> getGroupBy() {
        return groupBy;
    }

    public void setGroupBy(Map<String, String> groupBy) {
        this.groupBy = groupBy;
    }
}
