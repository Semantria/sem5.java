package com.lexalytics.semantria.client.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.ArrayList;
import java.util.List;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Group {
    private String groupId;
    private String groupName;
    private String externalId;
    private String accountId;
    private List<Permission> permissions = new ArrayList<>();
    private List<UserBrief> members = new ArrayList<>();

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public List<UserBrief> getMembers() {
        return members;
    }

    public void setMembers(List<UserBrief> members) {
        this.members = members;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null) {
            return false;
        }

        if (this.getClass() != o.getClass()) {
            return false;
        }

        Group group = (Group) o;

        return (accountId != null ? accountId.equals(group.accountId) : group.accountId == null)
                && (groupId != null ? groupId.equals(group.groupId) : group.groupId == null)
                && (groupName != null ? groupName.equals(group.groupName) : group.groupName == null);
    }

    @Override
    public int hashCode() {
        int result = groupId != null ? groupId.hashCode() : 0;
        result = 31 * result + (groupName != null ? groupName.hashCode() : 0);
        result = 31 * result + (accountId != null ? accountId.hashCode() : 0);
        return result;
    }}
