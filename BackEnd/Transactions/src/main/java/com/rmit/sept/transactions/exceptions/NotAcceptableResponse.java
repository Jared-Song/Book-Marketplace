package com.rmit.sept.transactions.exceptions;

public class NotAcceptableResponse {

    private String message;

    public NotAcceptableResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}