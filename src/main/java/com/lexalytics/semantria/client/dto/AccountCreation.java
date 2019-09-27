package com.lexalytics.semantria.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class AccountCreation {
    @JsonProperty("account_name")
    private String accountName;
    private String email;
    private String edition;

    @JsonProperty("user_name")
    private String userName;
    private String password;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("company_name")
    private String companyName;
    private String title;
    private String phone;
    private String country;

    @JsonProperty("advertisement_params")
    private Map<String, String> advertisementParams;

    @JsonProperty("with_user")
    private Boolean withUser;
    @JsonProperty("with_confirmation")
    private Boolean withConfirmation;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Map<String, String> getAdvertisementParams() {
        return advertisementParams;
    }

    public void setAdvertisementParams(Map<String, String> advertisementParams) {
        this.advertisementParams = advertisementParams;
    }

    public Boolean getWithUser() {
        return withUser;
    }

    public void setWithUser(Boolean withUser) {
        this.withUser = withUser;
    }

    public Boolean getWithConfirmation() {
        return withConfirmation;
    }

    public void setWithConfirmation(Boolean withConfirmation) {
        this.withConfirmation = withConfirmation;
    }

    @JsonProperty("send_confirmation")
    public boolean isSendConfirmation() {
        return !Boolean.FALSE.equals(withConfirmation);
    }

    @JsonProperty("create_user")
    public boolean isCreateUser() {
        return !Boolean.FALSE.equals(withUser);
    }}
