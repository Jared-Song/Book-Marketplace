package com.rmit.sept.bk_loginservices.exceptions;

public class UsernameExceptionResponse {

    private String username;

    public UsernameExceptionResponse(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}