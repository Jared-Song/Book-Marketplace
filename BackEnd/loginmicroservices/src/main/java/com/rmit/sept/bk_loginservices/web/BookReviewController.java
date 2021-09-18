package com.rmit.sept.bk_loginservices.web;

import org.springframework.web.bind.annotation.RestController;


import com.rmit.sept.bk_loginservices.Repositories.BookReviewRepository;
import com.rmit.sept.bk_loginservices.model.BookReview;
import com.rmit.sept.bk_loginservices.services.BookReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/bookReview")
public class BookReviewController {

    // adds the service to review a book
    @Autowired
    private BookReviewService bookReviewService;

    /*
    @PostMapping("/newReview")
    public ResponseEntity<?> addReview(@Valid @RequestBody BookReview review, BindingResult result) {
        bookReviewService.addReview(review);
        return new ResponseEntity<String>("Review with ID " + review + " was added", HttpStatus.OK);
    }
    */

    @GetMapping("")
    public ResponseEntity<?> getAllReviews() {
        List<BookReview> bookReviews = bookReviewService.getAllReviews();
        return new ResponseEntity<String>("Reviews " + bookReviews + " ", HttpStatus.OK);
    }

    /*
    @PostMapping("/{new}")
    public ResponseEntity<?> addRating(@PathVariable("new") @RequestBody Integer rating) {
        bookReviewService.addRating(rating);
        return new ResponseEntity<String>("Rating with ID " + rating + " was added", HttpStatus.OK);
    }

     */

    @DeleteMapping("/{new}")
    public ResponseEntity<?> delete(@PathVariable("new") Long id) {
        bookReviewService.deleteReview(id);
        return new ResponseEntity<String>("Review with ID " + id + " was deleted", HttpStatus.OK);
    }

}
