package com.lexalytics.semantria.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Sentence {
    private Boolean isImperative;
    private List<Word> words;
    private Boolean isPolar;
    private Integer section;

    public static Sentence create(Boolean isImperative, List<Word> words, Boolean isPolar) {
        return create(isImperative, words, isPolar, 0);
    }

    public static Sentence create(Boolean isImperative, List<Word> words, Boolean isPolar, int section) {
        Sentence sentence = new Sentence();
        sentence.isImperative = isImperative;
        sentence.words = words;
        sentence.isPolar = isPolar;
        sentence.section = section;
        return sentence;
    }

    public Boolean getIsImperative() {
        return isImperative;
    }

    public void setIsImperative(Boolean imperative) {
        isImperative = imperative;
    }

    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }

    public Boolean getIsPolar() {
        return isPolar;
    }

    public void setIsPolar(Boolean polar) {
        isPolar = polar;
    }

    public Integer getSection() {
        return section;
    }

    public void setSection(Integer section) {
        this.section = section;
    }
}
