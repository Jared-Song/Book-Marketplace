package com.rmit.sept.bk_loginservices.model;

public class UserReview {

    private Long id;
    private String review;
    private Rating ratingOfUser;
    private User userBeingReviewed;

    public UserReview(Long id, String review, Rating ratingOfUser, User userBeingReviewed) {
        this.id = id;
        this.review = review;
        this.ratingOfUser = ratingOfUser;
        this.userBeingReviewed = userBeingReviewed;
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

    public Rating getRatingOfUser() {
        return ratingOfUser;
    }

    public void setRatingOfUser(Rating ratingOfUser) {
        this.ratingOfUser = ratingOfUser;
    }

    public User getUserBeingReviewed() {
        return userBeingReviewed;
    }

    public void setUserBeingReviewed(User userBeingReviewed) {
        this.userBeingReviewed = userBeingReviewed;
    }
}
