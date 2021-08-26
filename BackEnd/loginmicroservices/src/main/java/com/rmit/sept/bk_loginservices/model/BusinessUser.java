package com.rmit.sept.bk_loginservices.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "business_users")
public class BusinessUser {

    private Long userID;
    private int ABN;
    private String companyName;

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

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

}
