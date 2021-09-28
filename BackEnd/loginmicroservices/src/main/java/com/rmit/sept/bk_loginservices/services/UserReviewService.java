package com.rmit.sept.bk_loginservices.services;

import com.rmit.sept.bk_loginservices.Repositories.UserReviewRepository;
import com.rmit.sept.bk_loginservices.exceptions.ReviewException;
import com.rmit.sept.bk_loginservices.model.BookReview;
import com.rmit.sept.bk_loginservices.model.UserReview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserReviewService {

    @Autowired
    public UserReviewRepository userReviewRepository;

    public UserReviewService() {

    }

    public Iterable<UserReview> getAllReviews() {
        return userReviewRepository.findAll();
    }

    public UserReview addReview(UserReview userReview) {
        try {
            return userReviewRepository.save(userReview);
        } catch (Exception e) {
            return null;
        }
    }

    public UserReview addRating(UserReview rating) {
        try {
            while (userReviewRepository.findBookReviewByID(rating.getId()).iterator().hasNext()) {
                userReviewRepository.findBookReviewByID(rating.getId()).iterator().next().setRating(rating.getRating());
            }
        } catch (Exception e) {
            throw new ReviewException("Cannot add rating");
        }

        return rating;
    }

    public void deleteReview(Long id) {
        userReviewRepository.deleteById(id);
    }

    public void sortRatingByAscending() {
        userReviewRepository.sortRatingByAsc();
    }

    public void sortRatingByDescending() {
        userReviewRepository.sortRatingByDesc();
    }
}
