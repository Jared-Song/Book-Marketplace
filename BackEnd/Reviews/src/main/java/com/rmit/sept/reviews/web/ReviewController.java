package com.rmit.sept.reviews.web;

import javax.validation.Valid;

import com.rmit.sept.reviews.model.Review;
import com.rmit.sept.reviews.model.Transaction;
import com.rmit.sept.reviews.services.MapValidationErrorService;
import com.rmit.sept.reviews.services.ReviewService;
import com.rmit.sept.reviews.services.TransactionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/review")
public class ReviewController {
    
    @Autowired
    private TransactionService transactionService;
    // adds the service to review a book
    @Autowired
    private ReviewService reviewService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @GetMapping("/all")
    public Iterable<Review> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @PostMapping("/addReview")
    public ResponseEntity<?> addReview(@Valid @RequestBody Review review, BindingResult result) {

        //check if there's a mapping error
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) {
            return errorMap;
        }

        if (review.getTransactionId() == null)
            return new ResponseEntity<String>("Unable to add the new user review, transaction id not given!.", HttpStatus.NOT_ACCEPTABLE);

        Transaction transaction = transactionService.findById(review.getTransactionId());

        if (transaction == null)
            return new ResponseEntity<String>("Unable to add the user review, transaction ID to not found!.", HttpStatus.NOT_FOUND);

        review.setTransaction(transaction);

        if (transaction.getIsReviewed()) {
            return new ResponseEntity<String>("Unable to add the user review, transaction has already been reviewed!.", HttpStatus.NOT_ACCEPTABLE);
        }

        Review userReview = reviewService.addReview(review);

        if (userReview == null) {
            return new ResponseEntity<String>("Review failed to be added! " + String.valueOf(review.getId()) + " ", HttpStatus.CONFLICT);
        } else {
            reviewService.incrementRating(review);
            return new ResponseEntity<String>("Review with ID " + String.valueOf(review.getId()) + " was added", HttpStatus.OK);
        }
    }

    @DeleteMapping("/deleteReview/{reviewID}")
    public ResponseEntity<?> delete(@PathVariable Long reviewID) {
        if (reviewService.deleteReview(reviewID)) {
            return new ResponseEntity<String>("Review with ID " + reviewID + " was deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Review with ID " + reviewID + " failed to be deleted", HttpStatus.NOT_ACCEPTABLE);
        }

    }

}
