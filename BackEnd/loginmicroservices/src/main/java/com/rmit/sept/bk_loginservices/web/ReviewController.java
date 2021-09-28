package com.rmit.sept.bk_loginservices.web;

import com.rmit.sept.bk_loginservices.model.User;
import com.rmit.sept.bk_loginservices.model.Review;
import com.rmit.sept.bk_loginservices.model.Transaction;
import com.rmit.sept.bk_loginservices.services.MapValidationErrorService;
import com.rmit.sept.bk_loginservices.services.ReviewService;
import com.rmit.sept.bk_loginservices.services.TransactionService;
import com.rmit.sept.bk_loginservices.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    private ReviewService userReviewService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @GetMapping("/all")
    public Iterable<Review> getAllReviews() {
        Iterable<Review> userReviews = userReviewService.getAllReviews();
        return userReviews;
    }

    @PostMapping("/newReview")
    public ResponseEntity<?> addReview(@Valid @RequestBody Review review, BindingResult result) {


        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) {
            return errorMap;
        }

        if (review.getTransactionId() == null) return new ResponseEntity<String>("Unable to add the new user review, transaction id not given!.", HttpStatus.ACCEPTED);
        Transaction transaction = transactionService.findById(review.getTransactionId());
        if (transaction == null) return new ResponseEntity<String>("Unable to add the user review, transaction to tie to not found!.", HttpStatus.ACCEPTED);
        review.setTransaction(transaction);
        //review.setUserId(user.getId());
        if (review.getReviewerId() == null) return new ResponseEntity<String>("Unable to add the new user review, reviewer id not given!.", HttpStatus.ACCEPTED);
        User reviewer = userService.findById(review.getReviewerId());
        if (reviewer == null) return new ResponseEntity<String>("Unable to add the user review, reviewer to tie to not found!.", HttpStatus.ACCEPTED);
        review.setReviewer(reviewer);
        //review.setReviewerId(reviewer.getId());
        Review userReview = userReviewService.addReview(review);

        if (userReview == null) {
            return new ResponseEntity<String>("Review failed to be added! " + String.valueOf(review.getId()) + " ", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Review with ID " + String.valueOf(review.getId()) + " was added", HttpStatus.OK);
        }
    }

    /*

    @DeleteMapping("/{new}")
    public ResponseEntity<?> delete(@PathVariable("new") Long id) {
        bookReviewService.deleteReview(id);
        return new ResponseEntity<String>("Review with ID " + id + " was deleted", HttpStatus.OK);
    }
    */
}
