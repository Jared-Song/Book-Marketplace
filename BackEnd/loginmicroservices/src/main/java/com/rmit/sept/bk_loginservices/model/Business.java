package com.rmit.sept.bk_loginservices.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.Date;

@Entity
@Table(name = "business")
public class Business {

    @Id
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true)
    private int ABN;

    private String companyName;

    @JsonBackReference
    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }
    

    private Date created_At;
    private Date updated_At;

    public Business(Long userID, int ABN, String companyName, User user){
        this.id = userID;
        this.ABN = ABN;
        this.companyName = companyName;
        this.user = user;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
