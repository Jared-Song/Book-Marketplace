package com.rmit.sept.transactions.exceptions;

public class ConflictResponse {

    private String message;

    public ConflictResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}