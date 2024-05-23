package com.lexalytics.semantria.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Document {

    public Document() {
    }

    public Document(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public Document(String id, List<DocumentSection> sections) {
        this.id = id;
        this.sections = sections;
    }

    private String id;

    private String text;

    private List<DocumentSection> sections;

    private String languageId;

    private String tag;

    private Map<String, Object> metadata;

    public Document withMetadata(String key, Object value) {
        if (metadata == null) {
            metadata = new HashMap<>();
        }
        metadata.put(key, value);
        return this;
    }

    public Document withLanguageId(String languageId) {
        this.languageId = languageId;
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

    public List<DocumentSection> getSections() {
        return sections;
    }

    public void setSections(List<DocumentSection> sections) {
        this.sections = sections;
    }

    public String getLanguageId() {
        return languageId;
    }

    public Document setLanguageId(String languageId) {
        this.languageId = languageId;
        return this;
    }

    public String getTag() { return tag; }

    public void setTag(String tag) { this.tag = tag; }

    public Map<String, Object> getMetadata() { return metadata; }

    public void setMetadata(Map<String, Object> metadata) { this.metadata = metadata; }
}
