package com.lexalytics.semantria.client.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DocumentsRequest {
    private String using;

    @JsonProperty("job_id")
    private String jobId;

    @JsonProperty("limit")
    private int requestLimit;

    @JsonProperty("language_id")
    private String languageId;

    public String getUsing() {
        return using;
    }

    public void setUsing(String using) {
        this.using = using;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public int getRequestLimit() {
        return requestLimit;
    }

    public void setRequestLimit(int requestLimit) {
        this.requestLimit = requestLimit;
    }

    public String getLanguageId() {
        return languageId;
    }

    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }
}
