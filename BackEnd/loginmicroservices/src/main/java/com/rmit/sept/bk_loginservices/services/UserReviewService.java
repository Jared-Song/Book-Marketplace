package com.rmit.sept.bk_loginservices.services;

import com.rmit.sept.bk_loginservices.Repositories.UserReviewRepository;
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

    public void addReview(UserReview userReview) {
        userReviewRepository.save(userReview);
    }

    public void deleteReview(Long id) {
        userReviewRepository.deleteById(id);
    }
}
