package com.example.paymentservice.dto.response;

import com.example.paymentservice.dao.Payment;
import com.example.paymentservice.enums.PaymentStatus;

import java.util.UUID;

public class PaymentResponse {
    private UUID paymentUid;
    private PaymentStatus status;
    private Integer price;

    public PaymentResponse(UUID paymentUid, PaymentStatus status, Integer price) {
        this.paymentUid = paymentUid;
        this.status = status;
        this.price = price;
    }
    public PaymentResponse(Payment payment) {
        this.paymentUid = payment.getPaymentUid();
        this.status = payment.getStatus();
        this.price = payment.getPrice();
    }

    public UUID getPaymentUid() {
        return paymentUid;
    }

    public void setPaymentUid(UUID paymentUid) {
        this.paymentUid = paymentUid;
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
