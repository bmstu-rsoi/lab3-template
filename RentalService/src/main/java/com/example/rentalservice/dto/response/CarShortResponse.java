package com.example.rentalservice.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.UUID;

public class CarShortResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UUID carUid;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String brand;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String model;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String registrationNumber;

    public CarShortResponse() {
    }

    public CarShortResponse(CarResponse carResponse) {
        this.carUid = carResponse.getCarUid();
        this.brand = carResponse.getBrand();
        this.model = carResponse.getModel();
        this.registrationNumber = carResponse.getRegistrationNumber();
    }

    public UUID getCarUid() {
        return carUid;
    }

    public void setCarUid(UUID carUid) {
        this.carUid = carUid;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }
}
