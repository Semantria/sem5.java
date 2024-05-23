package com.lexalytics.semantria.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ModelSentiment {
    private String modelName = "";
    private Double mixedScore = 0.0;
    private Double negativeScore = 0.0;
    private Double neutralScore = 0.0;
    private Double positiveScore = 0.0;
    private String sentimentPolarity = "";

    public static ModelSentiment create(String modelName, Double mixedScore, Double negativeScore, Double neutralScore,
                                        Double positiveScore, String sentimentPolarity) {
        ModelSentiment ms = new ModelSentiment();

        ms.modelName = modelName;
        ms.mixedScore = mixedScore;
        ms.negativeScore = negativeScore;
        ms.neutralScore = neutralScore;
        ms.positiveScore = positiveScore;
        ms.sentimentPolarity = sentimentPolarity;

        return ms;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Double getMixedScore() {
        return mixedScore;
    }

    public void setMixedScore(Double mixedScore) {
        this.mixedScore = mixedScore;
    }

    public Double getNegativeScore() {
        return negativeScore;
    }

    public void setNegativeScore(Double negativeScore) {
        this.negativeScore = negativeScore;
    }

    public Double getNeutralScore() {
        return neutralScore;
    }

    public void setNeutralScore(Double neutralScore) {
        this.neutralScore = neutralScore;
    }

    public Double getPositiveScore() {
        return positiveScore;
    }

    public void setPositiveScore(Double positiveScore) {
        this.positiveScore = positiveScore;
    }

    public String getSentimentPolarity() {
        return sentimentPolarity;
    }

    public void setSentimentPolarity(String sentimentPolarity) {
        this.sentimentPolarity = sentimentPolarity;
    }
}

