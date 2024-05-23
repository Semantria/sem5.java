package com.lexalytics.semantria.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SectionResult {

    private Integer sectionId;
    private String name;
    private Set<String> aliases = new HashSet<>();
    private String value;
    private String redactedValue;
    private boolean processAsText = false;
    private Integer charOffset;
    private Integer charLength;
    private Integer byteOffset;
    private Integer byteLength;
    private Map<String, MetadataValue> metadata = null;
    private List<SectionResult> sections = new ArrayList<>();

    public SectionResult() {
    }

    public SectionResult(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public Integer getSectionId() {
        return sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getAliases() {
        return aliases;
    }

    public void setAliases(Set<String> aliases) {
        this.aliases = aliases;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRedactedValue() {
        return redactedValue;
    }

    public void setRedactedValue(String redactedValue) {
        this.redactedValue = redactedValue;
    }

    public boolean isProcessAsText() {
        return processAsText;
    }

    public void setProcessAsText(boolean processAsText) {
        this.processAsText = processAsText;
    }

    public Integer getCharOffset() {
        return charOffset;
    }

    public void setCharOffset(Integer charOffset) {
        this.charOffset = charOffset;
    }

    public Integer getCharLength() {
        return charLength;
    }

    public void setCharLength(Integer charLength) {
        this.charLength = charLength;
    }

    public Integer getByteOffset() {
        return byteOffset;
    }

    public void setByteOffset(Integer byteOffset) {
        this.byteOffset = byteOffset;
    }

    public Integer getByteLength() {
        return byteLength;
    }

    public void setByteLength(Integer byteLength) {
        this.byteLength = byteLength;
    }

    public Map<String, MetadataValue> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, MetadataValue> metadata) {
        this.metadata = metadata;
    }

    public List<SectionResult> getSections() {
        return sections;
    }

    public void setSections(List<SectionResult> sections) {
        this.sections = sections;
    }

}
