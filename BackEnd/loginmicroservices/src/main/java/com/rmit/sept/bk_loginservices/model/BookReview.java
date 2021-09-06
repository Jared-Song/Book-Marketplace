package com.rmit.sept.bk_loginservices.model;

public class BookReview {

    private Long id;
    private String review;
    private Rating rating;
    private Book bookBeingReviewed;

    public BookReview(Long id, String review, Rating rating, Book bookBeingReviewed) {
        this.id = id;
        this.review = review;
        this.rating = rating;
        this.bookBeingReviewed = bookBeingReviewed;
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

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public Book getBookBeingReviewed() {
        return bookBeingReviewed;
    }

    public void setBookBeingReviewed(Book bookBeingReviewed) {
        this.bookBeingReviewed = bookBeingReviewed;
    }
}