package com.rmit.sept.reviews.services;

import com.rmit.sept.reviews.Repositories.BookRepository;
import com.rmit.sept.reviews.Repositories.ReviewRepository;
import com.rmit.sept.reviews.Repositories.TransactionRepository;
import com.rmit.sept.reviews.Repositories.UserRepository;
import com.rmit.sept.reviews.model.Book;
import com.rmit.sept.reviews.model.Review;
import com.rmit.sept.reviews.model.Transaction;
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
        boolean reviewed = false;
        Transaction transaction = review.getTransaction();
        Book book = transaction.getBook();

        User seller = book.getSeller();
        if (seller == null) {
            System.out.println("seller is null");
        } else {
            try {
                seller.setRatingTotal(review.getUserRating() + seller.getRatingTotal());
                seller.setRatingNo(seller.getRatingNo() + 1);
                seller = userRepository.save(seller);

                book.setRatingTotal(review.getBookRating() + book.getRatingTotal());
                book.setRatingNo(book.getRatingNo() + 1);
                book = bookRepository.save(book);
                if (seller != null && book != null) {
                    reviewed = true;
                    transaction.setIsReviewed(reviewed);
                    transactionRepository.save(transaction);
                }
            } catch (Exception e) {
                System.out.println("exception");
            }
         
        }

        return reviewed;
    }

    public boolean deleteReview(Long id) {
        try {
            reviewRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
