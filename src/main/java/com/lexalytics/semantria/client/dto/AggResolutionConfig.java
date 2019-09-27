package com.lexalytics.semantria.client.dto;

public class AggResolutionConfig {
    private int limit;
    private String fallback = null;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getFallback() {
        return fallback;
    }

    public void setFallback(String fallback) {
        this.fallback = fallback;
    }
}
