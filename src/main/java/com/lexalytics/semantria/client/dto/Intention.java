package com.lexalytics.semantria.client.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Intention {
    private String type = "";
    private String who = "";
    private String what = "";
    private String evidencePhrase = "";

    public static Intention create(String type, String who, String what, String evidence) {
        Intention intention = new Intention();

        intention.type = type;
        intention.who = who;
        intention.what = what;
        intention.evidencePhrase = evidence;

        return intention;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public String getWhat() {
        return what;
    }

    public void setWhat(String what) {
        this.what = what;
    }

    public String getEvidencePhrase() {
        return evidencePhrase;
    }

    public void setEvidencePhrase(String evidencePhrase) {
        this.evidencePhrase = evidencePhrase;
    }
}
