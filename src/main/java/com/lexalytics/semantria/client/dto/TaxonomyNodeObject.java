package com.lexalytics.semantria.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaxonomyNodeObject {
    @JsonProperty("id")
    private String id;
    @JsonProperty("title")
    private String name;
    @JsonIgnore
    private List<TaxonomyLeafObject> leafs = new ArrayList<>();
    @JsonIgnore
    private List<TaxonomyNodeObject> branches = new ArrayList<>();
    @JsonProperty("mentions")
    private List<Mention> mentions;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<TaxonomyLeafObject> getLeafs() {
        return leafs;
    }

    public List<TaxonomyNodeObject> getNodes() {
        return branches;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return "node";
    }

    public List<Mention> getMentions() {
        return mentions;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLeafs(List<TaxonomyLeafObject> leafs) {
        this.leafs = leafs;
    }

    @JsonProperty("elements")
    public List<TNOOutput> getBranches() {
        List<TNOOutput> res = new ArrayList<>();

        if (branches != null) {
            for (TaxonomyNodeObject t : branches) {
                res.add(new TNOOutput(t));
            }
        }

        if (leafs != null) {
            for (TaxonomyLeafObject l : leafs) {
                res.add(new TNOOutput(l));
            }
        }

        return res;
    }

    public void setBranches(List<TaxonomyNodeObject> branches) {
        this.branches = branches;
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class TaxonomyLeafObject {
        @JsonProperty("id")
        private String id;
        @JsonProperty("hitcount")
        private Integer hitCount;
        @JsonProperty("sentiment_score")
        private Float sentimentScore;
        @JsonProperty("strength_score")
        private Float strengthScore;
        @JsonProperty("title")
        private String title = "";
        @JsonProperty("type")
        private String type = "";
        @JsonProperty("mentions")
        private List<Mention> mentions;
        @JsonProperty("sentiment_polarity")
        private String sentimentPolarity;
        @JsonProperty("entity_type")
        private String entityType;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Integer getHitCount() {
            return hitCount;
        }

        public void setHitCount(Integer hitCount) {
            this.hitCount = hitCount;
        }

        public Float getSentimentScore() {
            return sentimentScore;
        }

        public void setSentimentScore(Float sentimentScore) {
            this.sentimentScore = sentimentScore;
        }

        public Float getStrengthScore() {
            return strengthScore;
        }

        public void setStrengthScore(Float strengthScore) {
            this.strengthScore = strengthScore;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<Mention> getMentions() {
            return mentions;
        }

        public void setMentions(List<Mention> mentions) {
            this.mentions = mentions;
        }

        public String getSentimentPolarity() {
            return sentimentPolarity;
        }

        public void setSentimentPolarity(String sentimentPolarity) {
            this.sentimentPolarity = sentimentPolarity;
        }

        public String getEntityType() {
            return entityType;
        }

        public void setEntityType(String entityType) {
            this.entityType = entityType;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class TNOOutput {
        @JsonProperty("id")
        private String id;
        @JsonProperty("topic")
        private List<TNOOutput> leafs;
        @JsonProperty("hitcount")
        private Integer hitCount;
        @JsonProperty("sentiment_score")
        private Float sentimentScore;
        @JsonProperty("strength_score")
        private Float strengthScore;
        @JsonProperty("title")
        private String title;
        @JsonProperty("type")
        private String type = "";
        @JsonProperty("mentions")
        private List<Mention> mentions;
        @JsonProperty("sentiment_polarity")
        private String sentimentPolarity;
        @JsonProperty("entity_type")
        private String entityType;

        public TNOOutput() {

        }

        public TNOOutput(TaxonomyNodeObject taxonomyNodeObject) {
            this.id = taxonomyNodeObject.getId();
            this.title = taxonomyNodeObject.getName();
            this.leafs = taxonomyNodeObject.getBranches();
            this.type = "node";
        }

        public TNOOutput(TaxonomyLeafObject taxonomyLeafObject) {
            this.id = taxonomyLeafObject.getId();
            this.title = taxonomyLeafObject.getTitle();
            this.hitCount = taxonomyLeafObject.getHitCount();
            this.sentimentScore = taxonomyLeafObject.getSentimentScore();
            this.strengthScore = taxonomyLeafObject.getStrengthScore();
            this.mentions = taxonomyLeafObject.getMentions();
            this.type = taxonomyLeafObject.getType();
            this.entityType = taxonomyLeafObject.getEntityType();
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<TNOOutput> getLeafs() {
            return leafs;
        }

        public void setLeafs(List<TNOOutput> leafs) {
            this.leafs = leafs;
        }

        public Integer getHitCount() {
            return hitCount;
        }

        public void setHitCount(Integer hitCount) {
            this.hitCount = hitCount;
        }

        public Float getSentimentScore() {
            return sentimentScore;
        }

        public void setSentimentScore(Float sentimentScore) {
            this.sentimentScore = sentimentScore;
        }

        public Float getStrengthScore() {
            return strengthScore;
        }

        public void setStrengthScore(Float strengthScore) {
            this.strengthScore = strengthScore;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<Mention> getMentions() {
            return mentions;
        }

        public void setMentions(List<Mention> mentions) {
            this.mentions = mentions;
        }

        public String getSentimentPolarity() {
            return sentimentPolarity;
        }

        public void setSentimentPolarity(String sentimentPolarity) {
            this.sentimentPolarity = sentimentPolarity;
        }

        public String getEntityType() {
            return entityType;
        }

        public void setEntityType(String entityType) {
            this.entityType = entityType;
        }
    }
}
