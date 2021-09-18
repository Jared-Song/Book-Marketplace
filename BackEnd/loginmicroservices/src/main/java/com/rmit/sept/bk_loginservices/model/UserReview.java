package com.rmit.sept.bk_loginservices.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // private Long userID;

    // private int rating;

    private String review;


    public UserReview() {

    }

    public UserReview(Long id, String review, Rating ratingOfUser, User userBeingReviewed) {
        this.id = id;
        this.review = review;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
