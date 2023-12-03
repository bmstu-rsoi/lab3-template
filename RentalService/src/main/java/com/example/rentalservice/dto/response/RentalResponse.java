package com.example.rentalservice.dto.response;

import com.example.rentalservice.dao.Rental;
import com.example.rentalservice.enums.RentalStatus;

import java.time.LocalDate;
import java.util.UUID;

public class RentalResponse {
    private UUID rentalUid;
    private RentalStatus status;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private UUID carUid;
    private PaymentResponse payment;

    public RentalResponse(Rental rental, PaymentResponse payment) {
        this.rentalUid = rental.getRentalUid();
        this.status = rental.getStatus();
        this.dateFrom = rental.getDateFrom().toLocalDate();
        this.dateTo = rental.getDateTo().toLocalDate();
        this.carUid = rental.getCarUid();
        this.payment = payment;
    }

    public UUID getRentalUid() {
        return rentalUid;
    }

    public void setRentalUid(UUID rentalUid) {
        this.rentalUid = rentalUid;
    }

    public RentalStatus getStatus() {
        return status;
    }

    public void setStatus(RentalStatus status) {
        this.status = status;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public UUID getCarUid() {
        return carUid;
    }

    public void setCarUid(UUID carUid) {
        this.carUid = carUid;
    }
    public PaymentResponse getPayment() {
        return payment;
    }

    public void setPayment(PaymentResponse payment) {
        this.payment = payment;
    }

}
