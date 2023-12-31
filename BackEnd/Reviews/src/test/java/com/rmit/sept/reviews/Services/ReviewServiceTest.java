package com.rmit.sept.reviews.Services;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.ArrayList;
import java.util.List;

import com.rmit.sept.reviews.Repositories.ReviewRepository;
import com.rmit.sept.reviews.Repositories.UserRepository;
import com.rmit.sept.reviews.model.Book;
import com.rmit.sept.reviews.model.BookStatus;
import com.rmit.sept.reviews.model.Review;
import com.rmit.sept.reviews.model.Transaction;
import com.rmit.sept.reviews.model.TransactionStatus;
import com.rmit.sept.reviews.model.User;
import com.rmit.sept.reviews.services.MapValidationErrorService;
import com.rmit.sept.reviews.services.ReviewService;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;

@ActiveProfiles("test")
@SpringBootTest
public class ReviewServiceTest {

    MockMvc mockMvc;

    List<Review> reviews;

    @MockBean
    private ReviewRepository reviewRepository;

    @MockBean
    private ReviewService reviewService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private MapValidationErrorService mapValidationErrorService;

    @BeforeEach
    public void setup() {
        this.mockMvc = standaloneSetup(this.reviewService).build();

        User user1 = new User();
        user1.setId(1L);
        user1.setEmail("johndoe@gmail.com");
        user1.setUsername("JohnDoe");
        user1.setFullName("John Doe");
        user1.setPassword("password");
        user1.setAddress("1 John Street, Doeland");

        Book book = new Book();
        book.setISBN(93838382);
        book.setPrice(120);
        book.setBookStatus(BookStatus.AVAILABLE);
        book.setAuthorName("Aster Barr");
        book.setId(20L);

        Transaction transaction = new Transaction();
        transaction.setBuyer(user1);
        transaction.setStatus(TransactionStatus.DELIVERED);
        transaction.setId(1L);
        transaction.setBook(book);

        Review review = new Review();
        review.setId(1L);
        review.setReviewer(user1);
        review.setTransaction(transaction);
        review.setUserRating(9);
        this.reviews = new ArrayList<>();
        reviews.add(review);

        Review review2 = null;
        reviews.add(review2);

    }

    @Test
    @DisplayName("Test: getAllReviews() [Success]")
    public void getAllReviews() throws Exception {
        given(reviewRepository.findAll()).willReturn(reviews);
        Iterable<Review> reviewIterable = reviewService.getAllReviews();
        Assert.assertNotNull(reviewIterable);
    }

    @Test
    @DisplayName("Test: addReview() [Success]")
    public void testAddReview() {
        when(mapValidationErrorService.MapValidationService(ArgumentMatchers.any(BindingResult.class))).thenReturn(null);
        when(reviewService.addReview(ArgumentMatchers.any(Review.class))).thenReturn(reviews.get(0));
        Review review = reviewService.addReview(reviews.get(0));
        Assert.assertNotNull(review);
    }

    @Test
    @DisplayName("Test: addReview() [Fail]")
    public void testAddReviewFail() {
        when(mapValidationErrorService.MapValidationService(ArgumentMatchers.any(BindingResult.class))).thenReturn(null);
        when(reviewService.addReview(ArgumentMatchers.any(Review.class))).thenReturn(reviews.get(0));
        Review review = reviewService.addReview(reviews.get(1));
        Assertions.assertNull(review, (String) null);
    }


    @Test
    @DisplayName("Test: incrementRating() [Success]")
    public void incrementRating() {
        when(mapValidationErrorService.MapValidationService(ArgumentMatchers.any(BindingResult.class))).thenReturn(null);
        when(reviewService.addReview(ArgumentMatchers.any(Review.class))).thenReturn(reviews.get(0));

        reviewService.incrementRating(reviews.get(0));
    }

    @Test
    @DisplayName("Test: incrementRating() [Fail]")
    public void incrementRatingFail() {
        when(mapValidationErrorService.MapValidationService(ArgumentMatchers.any(BindingResult.class))).thenReturn(null);
        when(reviewService.addReview(ArgumentMatchers.any(Review.class))).thenReturn(reviews.get(0));

        reviewService.incrementRating(reviews.get(1));

        Assertions.assertNull(reviews.get(1), (String) null);
    }
}