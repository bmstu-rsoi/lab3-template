package com.example.rentalservice.dto.response;

import com.example.rentalservice.dao.Rental;
import com.example.rentalservice.enums.RentalStatus;

import java.time.LocalDate;
import java.util.UUID;

public class GetRentalResponse {
    private UUID rentalUid;
    private RentalStatus status;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private PaymentResponse payment;
    private CarShortResponse car;

    public GetRentalResponse(Rental rental, PaymentResponse payment, CarShortResponse car) {
        this.rentalUid = rental.getRentalUid();
        this.status = rental.getStatus();
        this.dateFrom = rental.getDateFrom().toLocalDate();
        this.dateTo = rental.getDateTo().toLocalDate();
        this.car = car;
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


    public PaymentResponse getPayment() {
        return payment;
    }

    public void setPayment(PaymentResponse payment) {
        this.payment = payment;
    }

    public CarShortResponse getCar() {
        return car;
    }

    public void setCar(CarShortResponse car) {
        this.car = car;
    }
}
