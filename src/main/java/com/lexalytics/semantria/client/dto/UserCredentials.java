package com.lexalytics.semantria.client.dto;

public class UserCredentials {
    private String login;
    private String password;

    public UserCredentials() {
    }

    public UserCredentials(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
