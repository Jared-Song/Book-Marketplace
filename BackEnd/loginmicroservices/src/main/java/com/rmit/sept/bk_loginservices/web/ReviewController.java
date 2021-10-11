package com.rmit.sept.bk_loginservices.web;

import javax.validation.Valid;

import com.rmit.sept.bk_loginservices.model.Review;
import com.rmit.sept.bk_loginservices.model.Transaction;
import com.rmit.sept.bk_loginservices.model.User;
import com.rmit.sept.bk_loginservices.services.MapValidationErrorService;
import com.rmit.sept.bk_loginservices.services.ReviewService;
import com.rmit.sept.bk_loginservices.services.TransactionService;
import com.rmit.sept.bk_loginservices.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/review")
public class ReviewController {

    @Autowired
    private UserService userService;
    
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

        if (review.getReviewerId() == null)
            return new ResponseEntity<String>("Unable to add the new user review, reviewer id not given!.", HttpStatus.NOT_ACCEPTABLE);

        User reviewer = userService.findById(review.getReviewerId());

        if (reviewer == null)
            return new ResponseEntity<String>("Unable to add the user review, reviewer to tie to not found!.", HttpStatus.NOT_FOUND);

        review.setReviewer(reviewer);

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
            return new ResponseEntity<String>("Review with ID " + reviewID + " failed to be deleted", HttpStatus.OK);
        }

    }

}
