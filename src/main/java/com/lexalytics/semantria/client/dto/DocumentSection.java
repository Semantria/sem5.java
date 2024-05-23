package com.lexalytics.semantria.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.lexalytics.semantria.client.SemantriaClientError;

import java.nio.file.Files;
import java.util.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DocumentSection {

    public DocumentSection() {
    }

    public DocumentSection(String name, String value) {
        this.name = name;
        this.value = value;
    }

    private String name;
    private Set<String> aliases;
    private String value;
    private boolean processAsText = true;
    private Map<String, MetadataValue> metadata;
    private List<DocumentSection> sections;

    public DocumentSection withAlias(String alias) {
        if (aliases == null) {
            aliases = new HashSet<>();
        }
        aliases.add(alias);
        return this;
    }

    public DocumentSection withProcessAsText(boolean value) {
        processAsText = value;
        return this;
    }

    public DocumentSection withMetadata(String key, Object value) {
        if (metadata == null) {
            metadata = new HashMap<>();
        }
        if (value == null) {
            metadata.put(key, null);
        } else if (value instanceof String) {
            metadata.put(key, MetadataValue.of((String)value));
        } else if (value instanceof Integer) {
            metadata.put(key, MetadataValue.of((Integer)value));
        } else if (value instanceof Long) {
            metadata.put(key, MetadataValue.of((Long)value));
        } else if (value instanceof Float) {
            metadata.put(key, MetadataValue.of((Float) value));
        } else if (value instanceof Double) {
            metadata.put(key, MetadataValue.of((Double) value));
        } else if (value instanceof Boolean) {
            metadata.put(key, MetadataValue.of((Boolean)value));
        } else {
            throw new SemantriaClientError(String.format("Invalid type for section metadata %s: %s",
                    key, value.getClass().getCanonicalName()));
        }
        return this;
    }

    public DocumentSection withSection(DocumentSection section) {
        if (sections == null) {
            sections = new ArrayList<>();
        }
        sections.add(section);
        return this;
    }

    public String getName() {
        return name;
    }

    public DocumentSection setName(String name) {
        this.name = name;
        return this;
    }

    public Set<String> getAliases() {
        return aliases;
    }

    public DocumentSection setAliases(Set<String> aliases) {
        this.aliases = aliases;
        return this;
    }

    public String getValue() {
        return value;
    }

    public DocumentSection setValue(String value) {
        this.value = value;
        return this;
    }

    public boolean isProcessAsText() {
        return processAsText;
    }

    public DocumentSection setProcessAsText(boolean processAsText) {
        this.processAsText = processAsText;
        return this;
    }

    public Map<String, MetadataValue> getMetadata() {
        return metadata;
    }

    public DocumentSection setMetadata(Map<String, MetadataValue> metadata) {
        this.metadata = metadata;
        return this;
    }

    public List<DocumentSection> getSections() {
        return sections;
    }

    public DocumentSection setSections(List<DocumentSection> sections) {
        this.sections = sections;
        return this;
    }

}
