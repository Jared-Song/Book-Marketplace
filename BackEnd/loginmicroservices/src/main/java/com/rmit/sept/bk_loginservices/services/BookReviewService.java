package com.rmit.sept.bk_loginservices.services;


import com.rmit.sept.bk_loginservices.Repositories.BookReviewRepository;
import com.rmit.sept.bk_loginservices.model.BookReview;
import com.rmit.sept.bk_loginservices.model.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class BookReviewService {

    @Autowired
    public BookReviewRepository bookReviewRepository;

    public BookReviewService() {

    }

    public List<BookReview> getAllReviews() {
        List<BookReview> bookReviews = new ArrayList<>();
        bookReviewRepository.findAll().forEach(bookReviews::add);
        return bookReviews;
    }

    public void addReview(BookReview bookReview) {
        bookReviewRepository.save(bookReview);
    }

    public void deleteReview(Long id) {
        bookReviewRepository.deleteById(id);
    }

}
