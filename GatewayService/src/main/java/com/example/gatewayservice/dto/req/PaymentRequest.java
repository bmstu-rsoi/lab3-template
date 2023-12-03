package com.example.gatewayservice.dto.req;

import com.example.gatewayservice.enums.PaymentStatus;

public class PaymentRequest {
    private PaymentStatus status;
    private Integer price;

    public PaymentRequest(PaymentStatus status, Integer price) {
        this.status = status;
        this.price = price;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
