package com.lexalytics.semantria.client.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Collection {
    private String id;

    private List<String> documents;

    private String tag;

    private String metadata;

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public List<String> getDocuments() { return documents; }

    public void setDocuments(List<String> documents) { this.documents = documents; }

    public String getTag() { return tag; }

    public void setTag(String tag) { this.tag = tag; }

    public String getMetadata() { return metadata; }

    public void setMetadata(String metadata) { this.metadata = metadata; }
}