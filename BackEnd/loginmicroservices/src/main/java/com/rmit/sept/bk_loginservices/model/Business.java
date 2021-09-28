package com.rmit.sept.bk_loginservices.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.Date;

@Entity
@Table(name = "business_users")
public class Business {
    @Id
    @Column(name = "id")
    private int id;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @Column(name = "ABN")
    private int ABN;
    @Column(name = "name")
    private String companyName;


    public User getUser() {
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }

    private Date created_At;
    private Date updated_At;

    public Business(int ABN, String companyName, User user) {
        this.ABN = ABN;
        this.companyName = companyName;
        this.user = user;
    }

    public Business() {
        
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

    public Date getcreated_At() {
        return created_At;
    }

    public void setcreated_At(Date created_At) {
        this.created_At = created_At;
    }

    public Date getupdated_At() {
        return updated_At;
    }

    public void setupdated_At(Date updated_At) {
        this.updated_At = updated_At;
    }


    @PrePersist
    protected void onCreate() {
        this.created_At = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updated_At = new Date();
    }

}
