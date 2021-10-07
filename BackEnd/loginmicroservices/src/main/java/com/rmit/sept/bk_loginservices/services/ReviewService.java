package com.rmit.sept.bk_loginservices.services;

import com.rmit.sept.bk_loginservices.Repositories.ReviewRepository;
import com.rmit.sept.bk_loginservices.exceptions.ReviewException;
import com.rmit.sept.bk_loginservices.model.Review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ReviewService {

    @Autowired
    public ReviewRepository reviewRepository;

    public ReviewService() {

    }

    public Iterable<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Review addReview(Review userReview) {
        try {
            return reviewRepository.save(userReview);
        } catch (Exception e) {
            return null;
        }
    }

    public Review addRating(Review rating) {
        try {
            while (reviewRepository.findBookReviewByID(rating.getId()).iterator().hasNext()) {
                reviewRepository.findBookReviewByID(rating.getId()).iterator().next().setBookRating(rating.getBookRating());
            }
        } catch (Exception e) {
            throw new ReviewException("Cannot add rating");
        }

        return rating;
    }

    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    public void sortRatingByAscending() {
        reviewRepository.sortRatingByAsc();
    }

    public void sortRatingByDescending() {
        reviewRepository.sortRatingByDesc();
    }
}
