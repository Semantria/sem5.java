package com.lexalytics.semantria.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {
    // Using simply "offset" and "length" for compatibility
    @JsonProperty("offset")
    private Integer byteOffset;
    @JsonProperty("length")
    private Integer byteLength;
    private Integer charOffset;
    private Integer charLength;
    private Integer section;
    private Integer index;
    private Integer sentence;
    private Integer tokenIndex;
    private Integer tokenCount;

    public static Location create(Integer byteOffset, Integer byteLength, Integer charOffset, Integer charLength) {
        return create(byteOffset, byteLength, charOffset, charLength, null, null, null, null);
    }

    public static Location create(Integer byteOffset, Integer byteLength, Integer charOffset, Integer charLength, Integer index, Integer sentence, Integer tokenIndex, Integer tokenCount) {
        Location location = new Location();
        location.byteOffset = byteOffset;
        location.byteLength = byteLength;
        location.charOffset = charOffset;
        location.charLength = charLength;
        location.index = index;
        location.sentence = sentence;
        location.tokenIndex = tokenIndex;
        location.tokenCount = tokenCount;
        return location;
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

    public Integer getCharOffset() {
        return charOffset;
    }

    public Location setCharOffset(Integer charOffset) {
        this.charOffset = charOffset;
        return this;
    }

    public Integer getCharLength() {
        return charLength;
    }

    public Location setCharLength(Integer charLength) {
        this.charLength = charLength;
        return this;
    }

    public Integer getSection() {
        return section;
    }

    public void setSection(Integer section) {
        this.section = section;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getSentence() {
        return sentence;
    }

    public void setSentence(Integer sentence) {
        this.sentence = sentence;
    }

    public Integer getTokenIndex() {
        return tokenIndex;
    }

    public void setTokenIndex(Integer tokenIndex) {
        this.tokenIndex = tokenIndex;
    }

    public Integer getTokenCount() {
        return tokenCount;
    }

    public void setTokenCount(Integer tokenCount) {
        this.tokenCount = tokenCount;
    }
}
