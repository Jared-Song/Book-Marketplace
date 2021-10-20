package com.rmit.sept.reviews.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(generator = "review_sequence", strategy = GenerationType.SEQUENCE)
    @GenericGenerator(name = "review_sequence", strategy = "sequence", parameters = {
        @Parameter(name = "sequence_name", value = "review_sequence"),
        @Parameter(name = "increment_size", value = "1"),
    })

    @Column(name = "review_id") private Long id;

    @OneToOne @JoinColumn(name = "reviewer_id") private User reviewer;

    @OneToOne @JsonBackReference @JoinColumn(name = "transaction_id") private Transaction transaction;

    @Column(name = "book_rating") private int bookRating;

    @Column(name = "user_rating") private int userRating;

    @Transient private Long reviewerId;

    @Transient private Long transactionId;

    public Long getId() {
        return id;
    }

    public Long getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(Long reviewerId) {
        this.reviewerId = reviewerId;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getReviewer() {
        return reviewer;
    }

    public void setReviewer(User reviewer) {
        this.reviewer = reviewer;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public int getBookRating() {
        return bookRating;
    }

    public void setBookRating(int rating) {
        this.bookRating = rating;
    }

    public int getUserRating() {
        return userRating;
    }

    public void setUserRating(int rating) {
        this.userRating = rating;
    }
}
