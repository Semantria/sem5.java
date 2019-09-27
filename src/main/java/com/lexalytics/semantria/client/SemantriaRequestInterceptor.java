package com.lexalytics.semantria.client;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class SemantriaRequestInterceptor implements RequestInterceptor {
    private final String accessToken;
    private final String apiVersion;
    private final String appName;

    public SemantriaRequestInterceptor(String accessToken, String apiVersion, String appName) {
        this.accessToken = accessToken;
        this.apiVersion = apiVersion;
        this.appName = appName;
    }

    @Override
    public void apply(RequestTemplate template) {
        if (accessToken != null) {
            template.header("Authorization", accessToken);
        }
        if (apiVersion != null) {
            template.header("x-api-version", apiVersion);
        }
        if (appName != null) {
            template.header("x-app-name", appName);
        }

    }
}
