package com.rmit.sept.reviews.services;

import com.rmit.sept.reviews.Repositories.BookRepository;
import com.rmit.sept.reviews.Repositories.ReviewRepository;
import com.rmit.sept.reviews.Repositories.TransactionRepository;
import com.rmit.sept.reviews.Repositories.UserRepository;
import com.rmit.sept.reviews.model.Book;
import com.rmit.sept.reviews.model.Review;
import com.rmit.sept.reviews.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ReviewService {

    @Autowired
    public ReviewRepository reviewRepository;

    @Autowired
    public TransactionRepository transactionRepository;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public BookRepository bookRepository;

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

    public boolean incrementRating(Review review) {

        if (review.getReviewer() == null) {
            return false;
        } else {
            User user = userRepository.getById(review.getReviewerId());

            user.setRatingTotal(review.getUserRating() + user.getRatingNo());

            user.setRatingNo(user.getRatingNo() + 1);

            userRepository.save(user);

            Book book = new Book();
            book.setRating(review.getUserRating() + book.getRatingNo());
            book.setRatingNo(book.getRatingNo() + 1);
            bookRepository.save(book);

            return true;
        }

    }

    public boolean deleteReview(Long id) {
        try {
            reviewRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void sortRatingByAscending() {
        reviewRepository.sortRatingByAsc();
    }

    public void sortRatingByDescending() {
        reviewRepository.sortRatingByDesc();
    }
}
