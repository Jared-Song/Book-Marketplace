package com.rmit.sept.transactions.model;

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
import javax.persistence.Transient;

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
    private User buyer;


    @Transient 
    private Long buyerID;

    @OneToOne
    @JoinColumn(name = "book_id")
    private Book book;
    
    
    @Transient
    private Long bookID;

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

    @Column(name = "quantity")
    private int quantity;

    public Transaction(User buyer, Book book, TransactionStatus status, double price){
        this.buyer = buyer;
        this.book = book;
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

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public Long getBuyerID() {
        return buyerID;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Long getBookID(){
        return bookID;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
