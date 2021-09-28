package com.rmit.sept.bk_loginservices.web;

import com.rmit.sept.bk_loginservices.model.User;
import com.rmit.sept.bk_loginservices.model.UserReview;
import com.rmit.sept.bk_loginservices.services.MapValidationErrorService;
import com.rmit.sept.bk_loginservices.services.UserReviewService;
import com.rmit.sept.bk_loginservices.services.UserService;
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

    @Autowired
    private UserService userService;

    // adds the service to review a book
    @Autowired
    private UserReviewService userReviewService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @GetMapping("/all")
    public Iterable<UserReview> getAllReviews() {
        Iterable<UserReview> userReviews = userReviewService.getAllReviews();
        return userReviews;
    }

    @PostMapping("/newReview")
    public ResponseEntity<?> addReview(@Valid @RequestBody UserReview review, BindingResult result) {


        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) {
            return errorMap;
        }

        if (review.getUserId() == null) return new ResponseEntity<String>("Unable to add the new user review, User id not given!.", HttpStatus.ACCEPTED);
        User user = userService.findById(review.getUserId());
        if (user == null) return new ResponseEntity<String>("Unable to add the user review, User to tie to not found!.", HttpStatus.ACCEPTED);
        review.setUser(user);
        //review.setUserId(user.getId());
        if (review.getReviewerId() == null) return new ResponseEntity<String>("Unable to add the new user review, reviewer id not given!.", HttpStatus.ACCEPTED);
        User reviewer = userService.findById(review.getReviewerId());
        if (reviewer == null) return new ResponseEntity<String>("Unable to add the user review, reviewer to tie to not found!.", HttpStatus.ACCEPTED);
        review.setReviewer(reviewer);
        //review.setReviewerId(reviewer.getId());
        UserReview userReview = userReviewService.addReview(review);

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
