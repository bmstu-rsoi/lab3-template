package com.example.gatewayservice.dto.resp;


import java.util.List;

public class GetCarsResponse {
    private Integer page;
    private Integer pageSize;
    private Long totalElements;
    private List<CarResponse> items;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public List<CarResponse> getItems() {
        return items;
    }

    public void setItems(List<CarResponse> items) {
        this.items = items;
    }

    public GetCarsResponse(Integer page, Integer pageSize, Long totalElements, List<CarResponse> items) {
        this.page = page;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.items = items;
    }
}
