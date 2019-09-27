package com.lexalytics.semantria.client.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    private String userId;
    private String login;

    private String accountId;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String title;
    private String companyName;
    private String freshbooksId;
    private String eloquaId;
    private String packageCode;
    private LocalDate passwordModified;
    private ZonedDateTime lastLogin;

    private java.util.Collection<Group> userGroups = new LinkedHashSet<>();
    private List<Permission> permissions = new ArrayList<>();

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public java.util.Collection<Group> getUserGroups() {
        return userGroups;
    }

    public void setUserGroups(Collection<Group> userGroups) {
        this.userGroups = userGroups;
    }

    public String getFreshbooksId() {
        return freshbooksId;
    }

    public void setFreshbooksId(String freshbooksId) {
        this.freshbooksId = freshbooksId;
    }

    public String getEloquaId() {
        return eloquaId;
    }

    public void setEloquaId(String eloquaId) {
        this.eloquaId = eloquaId;
    }

    public String getPackageCode() {
        return packageCode;
    }

    public void setPackageCode(String packageCode) {
        this.packageCode = packageCode;
    }

    public LocalDate getPasswordModified() {
        return passwordModified;
    }

    public void setPasswordModified(LocalDate passwordModified) {
        this.passwordModified = passwordModified;
    }

    public ZonedDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(ZonedDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
