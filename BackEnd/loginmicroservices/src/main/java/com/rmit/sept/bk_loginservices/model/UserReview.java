package com.rmit.sept.bk_loginservices.model;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "user_reviews")
public class UserReview {
    @Id
    @GeneratedValue(generator = "user_review_sequence", strategy = GenerationType.SEQUENCE)
    @GenericGenerator(name = "user_review_sequence", strategy = "sequence", parameters = {
        @Parameter(name = "sequence_name", value = "user_review_sequence"),
        @Parameter(name = "increment_size", value = "1"),
    })
    @Column(name = "review_id")
    private Long id;
    @OneToOne
    @JoinColumn(name = "reviewer_id")
    private User reviewer;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "rating")
    private int rating;
    @Column(name = "review")
    private String review;

    @Transient
    private Long reviewerId;

    @Transient
    private Long userId;

    public Long getId() {
        return id;
    }

    public Long getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(Long reviewerId) {
        this.reviewerId = reviewerId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
