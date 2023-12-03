package com.example.carsservice.dto.response;
import com.example.carsservice.dao.Car;
import com.example.carsservice.enums.CarType;

import java.util.UUID;

public class CarResponse {
    private UUID carUid;
    private String brand;
    private String model;
    private String registrationNumber;
    private Integer power;
    private Integer price;
    private Boolean available;
    private CarType type;

    public CarResponse(Car car) {
        this.carUid = car.getUuid();
        this.brand = car.getBrand();
        this.model = car.getModel();
        this.registrationNumber = car.getRegistrationNumber();
        this.power = car.getPower();
        this.price = car.getPrice();
        this.available = car.getAvailability();
        this.type = car.getType();
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

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public CarType getType() {
        return type;
    }

    public void setType(CarType type) {
        this.type = type;
    }
}
