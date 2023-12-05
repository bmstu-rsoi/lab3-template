package com.example.gatewayservice.dto.resp;

public class CarPriceResponse {
    private Integer price;

    public CarPriceResponse(Integer price) {
        this.price = price;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
