package com.example.paymentservice.dao;

import com.example.paymentservice.enums.PaymentStatus;
import jakarta.persistence.*;

import java.util.UUID;


@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "payment_uid", nullable = false)
    private UUID paymentUid;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Column(name = "price", nullable = false)
    private Integer price;

    public Payment(UUID paymentUid, PaymentStatus status, Integer price) {
        this.paymentUid = paymentUid;
        this.status = status;
        this.price = price;
    }

    public Payment() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", paymentUid='" + paymentUid + '\'' +
                ", status='" + status + '\'' +
                ", price=" + price +
                '}';
    }
}
