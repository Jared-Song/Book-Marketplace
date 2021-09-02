package com.rmit.sept.bk_loginservices.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User buyerID;
    @OneToOne
    @JoinColumn(name = "book_id")
    private Book bookID;

    @Column(name = "price")
    private double price;

    @Column(name = "date_processed")
    private Date dateProcessed;
    private Date updated_At;


    @Column(name = "transactions_status_id")
    private Status status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getBuyerID() {
        return buyerID;
    }

    public void setBuyerID(User buyerID) {
        this.buyerID = buyerID;
    }

    public Book getBookID() {
        return bookID;
    }

    public void setBookID(Book bookID) {
        this.bookID = bookID;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @PrePersist
    protected void onCreate() {
        this.dateProcessed = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updated_At = new Date();
    }
}
