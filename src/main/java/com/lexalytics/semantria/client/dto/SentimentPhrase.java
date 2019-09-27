package com.lexalytics.semantria.client.dto;

public class SentimentPhrase extends AbstractConfigurationItem {
    private String name;
    private Float weight;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }
}
