package com.rmit.sept.reviews.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

@TypeDef(
    name = "pg_enum",
    typeClass = PostgreSQLEnumType.class
)
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
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;


    @Enumerated(EnumType.STRING)
    @Column(length = 20, name = "transactions_status_id", columnDefinition = "transaction_status")
    @Type(type = "pg_enum")
    private TransactionStatus status;

    @OneToOne(mappedBy = "transaction")
    @JsonManagedReference
    private Review review;

    public Transaction(User buyerID, Book bookID, TransactionStatus status, double price){
        this.buyerID = buyerID;
        this.bookID = bookID;
        this.status = status;
        this.price = price;
    }

    public Transaction(){
        
    }

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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }
}
