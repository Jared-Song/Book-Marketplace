package com.rmit.sept.bk_loginservices.model;

import javax.persistence.*;

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
    @Column(name = "review_id")
    private Long id;
    @OneToOne
    @JoinColumn(name = "reviewer_id")
    private User reviewer;
    @OneToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;
    @Column(name = "rating")
    private int rating;
    @Column(name = "review")
    private String review;

    @Transient
    private Long reviewerId;

    @Transient
    private Long transactionId;

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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
