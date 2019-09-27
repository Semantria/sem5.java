package com.lexalytics.semantria.client.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.ArrayList;
import java.util.List;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Paged<T> {
    public static <T> Paged<T> createEmpty() {
        Paged<T> paged = new Paged<>();
        paged.setContent(new ArrayList<>());
        paged.setCurrentPage(0);
        paged.setTotalElements(0);
        paged.setTotalPages(1);
        return paged;
    }

    public static <T> Paged<T> createFromList(List<T> list) {
        Paged<T> paged = new Paged<>();
        paged.setContent(list);
        paged.setCurrentPage(0);
        paged.setTotalElements(list.size());
        paged.setTotalPages(1);
        return paged;
    }

    @JsonProperty("page")
    private int currentPage;

    private int pageSize;

    private int totalPages;

    private long totalElements;

    @JsonProperty("data")
    private List<T> content;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }
}
