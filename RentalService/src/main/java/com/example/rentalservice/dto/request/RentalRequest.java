package com.example.rentalservice.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public class RentalRequest {
    @NotNull
    private UUID carUid;
    private LocalDate dateFrom;
    private LocalDate dateTo;

    public RentalRequest(UUID carUid, LocalDate dateFrom, LocalDate dateTo) {
        this.carUid = carUid;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public UUID getCarUid() {
        return carUid;
    }

    public void setCarUid(UUID carUid) {
        this.carUid = carUid;
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
}
