package com.lexalytics.semantria.client.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.ArrayList;
import java.util.List;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Mention {
    private String label;
    @JsonProperty("is_negated")
    private Boolean negated;
    private String negatingPhrase;
    private List<Integer> indexes;
    private List<Location> locations;

    public static Mention create(String label, boolean isNegated, String negatingPhrase, Integer index) {
        return create(label, isNegated, negatingPhrase, index, null);
    }

    public static Mention create(String label, boolean isNegated, String negatingPhrase, Integer index, List<Location> locations) {
        Mention mention = new Mention();
        mention.label = label;
        mention.negated = isNegated;
        mention.negatingPhrase = negatingPhrase;
        if (index != null) {
            mention.indexes = new ArrayList<>();
            mention.indexes.add(index);
        }

        mention.locations = locations;
        return mention;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean getNegated() {
        return negated;
    }

    public void setNegated(Boolean negated) {
        this.negated = negated;
    }

    public String getNegatingPhrase() {
        return negatingPhrase;
    }

    public void setNegatingPhrase(String negatingPhrase) {
        this.negatingPhrase = negatingPhrase;
    }

    public List<Integer> getIndexes() {
        return indexes;
    }

    public void setIndexes(List<Integer> indexes) {
        this.indexes = indexes;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }
}
