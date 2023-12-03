package com.example.paymentservice.dto.request;

import com.example.paymentservice.enums.PaymentStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class PaymentRequest {
    @NotNull
    private PaymentStatus status;
    @Min(0)
    @NotNull
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
