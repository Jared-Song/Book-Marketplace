package com.rmit.sept.bk_loginservices.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.Date;

@Entity
@Table(name = "business")
public class Business {

    @Id
    @Column(name = "user_id")
    private Long userId;

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
        return userId;
    }

    public void setId(Long userId) {
        this.userId = userId;
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
