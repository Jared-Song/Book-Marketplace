package com.rmit.sept.bk_loginservices.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import java.util.Date;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(generator = "transaction_sequence", strategy = GenerationType.SEQUENCE)
    @GenericGenerator(name = "transaction_sequence", strategy = "sequence", parameters = {
        @Parameter(name = "sequence_name", value = "transaction_sequence"),
        @Parameter(name = "increment_size", value = "1"),
    })
    @Column(name = "transaction_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "buyer_id")
    private User buyerID;
    @OneToOne
    @JoinColumn(name = "book_id")
    private Book bookID;

    @Column(name = "price")
    private double price;

    @Column(name = "date_processed")
    private Date dateProcessed;

    @Column(name = "updated_at")
    private Date updatedAt;


    @Column(name = "transactions_status_id")
    private TransactionStatus status;

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

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    @PrePersist
    protected void onCreate() {
        this.dateProcessed = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }
}
