package com.rmit.sept.bk_loginservices.exceptions;

public class AbnAlreadyExistsResponse {

    private String abn;

    public AbnAlreadyExistsResponse(String abn) {
        this.abn = abn;
    }

    public String getAbn() {
        return abn;
    }

    public void setAbn(String abn) {
        this.abn = abn;
    }
}