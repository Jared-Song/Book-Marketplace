package com.rmit.sept.bk_loginservices.web;

import com.rmit.sept.bk_loginservices.model.UserReview;
import com.rmit.sept.bk_loginservices.services.UserReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/userReview")
public class UserReviewController {

    // adds the service to review a book
    @Autowired
    private UserReviewService userReviewService;

    @GetMapping("/all")
    public Iterable<UserReview> getAllReviews() {
        Iterable<UserReview> userReviews = userReviewService.getAllReviews();
        return userReviews;
    }


    @PostMapping("/newReview")
    public ResponseEntity<?> addReview(@Valid @RequestBody UserReview review, BindingResult result) {
        userReviewService.addReview(review);
        return new ResponseEntity<String>("Review with ID " + review + " was added", HttpStatus.OK);
    }

    /*

    @PostMapping("/{new}")
    public ResponseEntity<?> addRating(@PathVariable("new") @RequestBody Integer rating) {
        bookReviewService.addRating(rating);
        return new ResponseEntity<String>("Rating with ID " + rating + " was added", HttpStatus.OK);
    }


    @DeleteMapping("/{new}")
    public ResponseEntity<?> delete(@PathVariable("new") Long id) {
        bookReviewService.deleteReview(id);
        return new ResponseEntity<String>("Review with ID " + id + " was deleted", HttpStatus.OK);
    }
    */
}
