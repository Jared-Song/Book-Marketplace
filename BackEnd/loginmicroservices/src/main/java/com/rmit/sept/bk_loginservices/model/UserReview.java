package com.rmit.sept.bk_loginservices.model;

import javax.persistence.*;

@Entity
@Table(name = "user_reviews")
public class UserReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id", insertable=false, updatable=false)
    private User reviewerId;
    @OneToOne
    @JoinColumn(name = "user_id", insertable=false, updatable=false)
    private User userId;
    @Column(name = "rating")
    private int rating;
    @Column(name = "review")
    private String review;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(User reviewerId) {
        this.reviewerId = reviewerId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
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
