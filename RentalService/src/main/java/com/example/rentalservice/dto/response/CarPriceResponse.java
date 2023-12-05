package com.example.rentalservice.dto.response;


public class CarPriceResponse {
    private Integer price;

    public CarPriceResponse() {
    }

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
