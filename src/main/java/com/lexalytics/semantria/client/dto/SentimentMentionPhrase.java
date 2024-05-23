package com.lexalytics.semantria.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SentimentMentionPhrase {
    private String text;
    private int document;
    private int sentence;
    private int word;
    private int length;
    private int byteOffset;
    private int byteLength;
    private int charOffset;
    private int charLength;
    @JsonProperty("is_negated")
    private boolean negated;
    private String negator;
    private int type;
    private int section;

    public static SentimentMentionPhrase create(String text, int document, int sentence, int word, int length, int byteOffset,
                                                int byteLength, boolean isNegated, String negator, int type, int section, int charOffset, int charLength) {
        SentimentMentionPhrase phrase = new SentimentMentionPhrase();
        phrase.text = text;
        phrase.document = document;
        phrase.sentence = sentence;
        phrase.word = word;
        phrase.length = length;
        phrase.byteOffset = byteOffset;
        phrase.byteLength = byteLength;
        phrase.negated = isNegated;
        phrase.negator = negator;
        phrase.type = type;
        phrase.section = section;
        phrase.charLength = charLength;
        phrase.charOffset = charOffset;
        return phrase;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getDocument() {
        return document;
    }

    public void setDocument(int document) {
        this.document = document;
    }

    public int getSentence() {
        return sentence;
    }

    public void setSentence(int sentence) {
        this.sentence = sentence;
    }

    public int getWord() {
        return word;
    }

    public void setWord(int word) {
        this.word = word;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getByteOffset() {
        return byteOffset;
    }

    public void setByteOffset(int byteOffset) {
        this.byteOffset = byteOffset;
    }

    public int getByteLength() {
        return byteLength;
    }

    public void setByteLength(int byteLength) {
        this.byteLength = byteLength;
    }

    public boolean isNegated() {
        return negated;
    }

    public void setNegated(boolean negated) {
        this.negated = negated;
    }

    public String getNegator() {
        return negator;
    }

    public void setNegator(String negator) {
        this.negator = negator;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }

    public int getCharOffset() {
        return charOffset;
    }

    public void setCharOffset(int charOffset) {
        this.charOffset = charOffset;
    }

    public int getCharLength() {
        return charLength;
    }

    public void setCharLength(int charLength) {
        this.charLength = charLength;
    }
}
