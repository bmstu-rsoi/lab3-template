package com.example.paymentservice.dto.response;

public class ResourceNotFoundResponse {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResourceNotFoundResponse(String message) {
        this.message = message;
    }
}
