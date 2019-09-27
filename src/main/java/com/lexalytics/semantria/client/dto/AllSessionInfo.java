package com.lexalytics.semantria.client.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;
import java.util.Map;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AllSessionInfo {
    int liveSessionCount;
    Map<String, List<String>> userSessions;
    public int getLiveSessionCount() {
        return liveSessionCount;
    }

    public void setLiveSessionCount(int liveSessionCount) {
        this.liveSessionCount = liveSessionCount;
    }

    public Map<String, List<String>> getUserSessions() {
        return userSessions;
    }

    public void setUserSessions(Map<String, List<String>> userSessions) {
        this.userSessions = userSessions;
    }

}
