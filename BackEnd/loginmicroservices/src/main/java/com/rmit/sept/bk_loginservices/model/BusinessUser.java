package com.rmit.sept.bk_loginservices.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "business_users")
public class BusinessUser implements Serializable {

    @Id
    @OneToOne
    @JoinColumn(name = "user_id")
    private User userID;

    @Column(name = "ABN")
    private int ABN;
    @Column(name = "name")
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

    public User getUserID() {
        return userID;
    }

    public void setUserID(User userID) {
        this.userID = userID;
    }

}
