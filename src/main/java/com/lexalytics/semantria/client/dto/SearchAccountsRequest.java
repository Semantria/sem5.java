package com.lexalytics.semantria.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchAccountsRequest {
    @JsonProperty("page_number")
    private int pageNumber = 0;
    @JsonProperty("page_size")
    private int pageSize = 10;
    @JsonProperty("regitered_after")
    private String registeredAfter;
    @JsonProperty("registered_before")
    private String registeredBefore;
    @JsonProperty("expired_after")
    private String expiredAfter;
    @JsonProperty("expired_before")
    private String expiredBefore;
    @JsonProperty("name_part")
    private String namePart;
    private String type;
    private String state;
    @JsonProperty("edition_ids")
    private String editionIds;
    private String sort;
    @JsonProperty("reverse_order")
    private boolean reverseOrder = false;

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getRegisteredAfter() {
        return registeredAfter;
    }

    public void setRegisteredAfter(String registeredAfter) {
        this.registeredAfter = registeredAfter;
    }

    public String getRegisteredBefore() {
        return registeredBefore;
    }

    public void setRegisteredBefore(String registeredBefore) {
        this.registeredBefore = registeredBefore;
    }

    public String getExpiredAfter() {
        return expiredAfter;
    }

    public void setExpiredAfter(String expiredAfter) {
        this.expiredAfter = expiredAfter;
    }

    public String getExpiredBefore() {
        return expiredBefore;
    }

    public void setExpiredBefore(String expiredBefore) {
        this.expiredBefore = expiredBefore;
    }

    public String getNamePart() {
        return namePart;
    }

    public void setNamePart(String namePart) {
        this.namePart = namePart;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public boolean isReverseOrder() {
        return reverseOrder;
    }

    public void setReverseOrder(boolean reverseOrder) {
        this.reverseOrder = reverseOrder;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getEditionIds() {
        return editionIds;
    }

    public void setEditionIds(String editionIds) {
        this.editionIds = editionIds;
    }
}
