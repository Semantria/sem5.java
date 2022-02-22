package com.lexalytics.semantria.client;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lexalytics.semantria.client.dto.AccessToken;
import com.lexalytics.semantria.client.dto.UserCredentials;

public class SemantriaClientConfiguration  implements Cloneable {
    private static final String PROD_ENDPOINT = "https://api5.semantria.com";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private String apiEndpoint = PROD_ENDPOINT;
    private UserCredentials credentials;
    private String accessToken;
    private String apiVersion = "5.0";
    private String appName = "sem5-sdk";
    private String logfile = null;

    public SemantriaClientConfiguration() {
    }

    @Override
    public SemantriaClientConfiguration clone() {
        SemantriaClientConfiguration other = new SemantriaClientConfiguration();
        other.setApiEndpoint(apiEndpoint);
        other.setCredentials(credentials);
        other.setAccessToken(accessToken);
        other.setApiVersion(apiVersion);
        other.setAppName(appName);
        other.setLogfile(logfile);
        return other;
    }

    public static SemantriaClientConfiguration fromFile(String configFile) throws SemantriaClientError {
        return fromFile(configFile == null ? null : new File(configFile));
    }

    public static SemantriaClientConfiguration fromFile(File configFile) throws SemantriaClientError {
        if (configFile == null) {
            String sem5Config = System.getenv("SEM5_CONFIG");
            if (sem5Config != null && sem5Config.length() > 0) {
                configFile = new File(sem5Config);
            }
        }
        if (configFile != null) {
            try {
                return OBJECT_MAPPER.readValue(configFile, SemantriaClientConfiguration.class);
            } catch (IOException e) {
                throw new SemantriaClientError("Unable to read configuration from " + configFile.getAbsolutePath());
            }
        }
        return new SemantriaClientConfiguration();
    }

    public String getApiEndpoint() {
        return apiEndpoint;
    }

    public void setApiEndpoint(String apiEndpoint) {
        try {
            new URL(apiEndpoint);
        } catch (MalformedURLException e) {
            throw new SemantriaClientError("Environment should be correctly spelled URL");
        }

        this.apiEndpoint = apiEndpoint;
    }

    public UserCredentials getCredentials() {
        return credentials;
    }

    public void setCredentials(UserCredentials credentials) {
        this.credentials = credentials;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String sessionToken) {
        this.accessToken = sessionToken;
    }

    public void setAccessTokenFromToken(AccessToken accessToken) {
        this.accessToken = accessToken.getAccessToken();
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getLogfile() {
        return logfile;
    }

    public void setLogfile(String logfile) {
        this.logfile = logfile;
    }

    public SemantriaSDK connect() {
        return SemantriaSDK.connect(this);
    }
}
