package com.rmit.sept.bk_loginservices.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "business_users")
public class Business {
    @Id
    @GeneratedValue(generator = "business_sequence", strategy = GenerationType.SEQUENCE)
    @GenericGenerator(name = "business_sequence", strategy = "sequence", parameters = {
        @Parameter(name = "sequence_name", value = "businesssequence"),
        @Parameter(name = "increment_size", value = "1"),
    })
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
