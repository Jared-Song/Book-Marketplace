package com.rmit.sept.transactions.exceptions;

public class NotFoundResponse {

    private String message;

    public NotFoundResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}