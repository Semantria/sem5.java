package com.lexalytics.semantria.client.dto;

import java.time.ZonedDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class JobBrief {
    private String id;
    private String projectId;
    private String accountId;
    private String source;
    private String jobTemplateId;
    private String jobTemplateName;
    private Integer jobTemplateVersion;
    private JsonNode jobTemplateParameters;
    private String name;
    private String notes;
    private Boolean debug;
    private String machineType;
    private String status;
    private ZonedDateTime statusModificationDate;
    private List<String> messages;
    private ZonedDateTime creationDate;
    private ZonedDateTime queueDate;
    private ZonedDateTime startDate;
    private ZonedDateTime finishDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getJobTemplateId() {
        return jobTemplateId;
    }

    public void setJobTemplateId(String jobTemplateId) {
        this.jobTemplateId = jobTemplateId;
    }

    public String getJobTemplateName() {
        return jobTemplateName;
    }

    public void setJobTemplateName(String jobTemplateName) {
        this.jobTemplateName = jobTemplateName;
    }

    public Integer getJobTemplateVersion() {
        return jobTemplateVersion;
    }

    public void setJobTemplateVersion(Integer jobTemplateVersion) {
        this.jobTemplateVersion = jobTemplateVersion;
    }

    public JsonNode getJobTemplateParameters() {
        return jobTemplateParameters;
    }

    public void setJobTemplateParameters(JsonNode jobTemplateParameters) {
        this.jobTemplateParameters = jobTemplateParameters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Boolean getDebug() {
        return debug;
    }

    public void setDebug(Boolean debug) {
        this.debug = debug;
    }

    public String getMachineType() {
        return machineType;
    }

    public void setMachineType(String machineType) {
        this.machineType = machineType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ZonedDateTime getStatusModificationDate() {
        return statusModificationDate;
    }

    public void setStatusModificationDate(ZonedDateTime statusModificationDate) {
        this.statusModificationDate = statusModificationDate;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public ZonedDateTime getQueueDate() {
        return queueDate;
    }

    public void setQueueDate(ZonedDateTime queueDate) {
        this.queueDate = queueDate;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(ZonedDateTime finishDate) {
        this.finishDate = finishDate;
    }
}
