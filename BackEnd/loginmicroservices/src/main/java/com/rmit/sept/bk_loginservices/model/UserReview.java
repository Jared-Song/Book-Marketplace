package com.rmit.sept.bk_loginservices.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;
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
