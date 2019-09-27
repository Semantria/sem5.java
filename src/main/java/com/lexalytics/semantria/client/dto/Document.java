package com.lexalytics.semantria.client.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.HashMap;
import java.util.Map;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Document {

    public Document() {
    }

    public Document(String id, String text) {
        this.id = id;
        this.text = text;
    }

    private String id;

    private String text;

    private String tag;

    private Map<String, Object> metadata;

    public Document withMetadata(String key, Object value) {
        if (metadata == null) {
            metadata = new HashMap<>();
        }
        metadata.put(key, value);
        return this;
    }

    public Document withTag(String tag) {
        this.tag = tag;
        return this;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getText() { return text; }

    public void setText(String text) { this.text = text; }

    public String getTag() { return tag; }

    public void setTag(String tag) { this.tag = tag; }

    public Map<String, Object> getMetadata() { return metadata; }

    public void setMetadata(Map<String, Object> metadata) { this.metadata = metadata; }
}