package com.example.gatewayservice.dto.resp;

public class AnswerResp {
    public AnswerResp(String message) {
        this.message = message;
    }
    private String message;
    private String details;


    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
