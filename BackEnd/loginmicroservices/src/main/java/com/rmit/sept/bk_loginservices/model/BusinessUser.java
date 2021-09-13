package com.rmit.sept.bk_loginservices.model;

public class BusinessUser extends User {
    private int ABN;
    private String companyName;

    public BusinessUser() {
    }

    public int getABN() {
        return ABN;
    }

    public void setABN(int ABN) {
        this.ABN = ABN;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

}
