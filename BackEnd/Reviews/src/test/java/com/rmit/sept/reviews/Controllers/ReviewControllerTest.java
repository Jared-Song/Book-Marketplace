package com.rmit.sept.reviews.Controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.ArrayList;
import java.util.List;

import com.rmit.sept.reviews.model.Book;
import com.rmit.sept.reviews.model.BookStatus;
import com.rmit.sept.reviews.model.Review;
import com.rmit.sept.reviews.model.Transaction;
import com.rmit.sept.reviews.model.TransactionStatus;
import com.rmit.sept.reviews.model.User;
import com.rmit.sept.reviews.services.MapValidationErrorService;
import com.rmit.sept.reviews.services.ReviewService;
import com.rmit.sept.reviews.web.ReviewController;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.WebApplicationContext;


@ActiveProfiles("test")
@SpringBootTest
public class ReviewControllerTest {

    MockMvc mockMvc;

    @MockBean
    ReviewService reviewService;

    @Autowired
    ReviewController reviewController;

    @Autowired
    WebApplicationContext webApplicationContext;

    List<Review> reviews;

    @MockBean
    private MapValidationErrorService mapValidationErrorService;

    @BeforeEach
    public void setup() {
        this.mockMvc = standaloneSetup(this.reviewController).build();

        User user1 = new User();
        user1.setId(5L);
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
        //review.setTransactionId(transaction.getId());

        this.reviews = new ArrayList<>();
        reviews.add(review);
    }

    @Test
    @DisplayName("Test: addReview() [Success]")
    public void testAddReview() throws Exception {

        when(mapValidationErrorService.MapValidationService(ArgumentMatchers.any(BindingResult.class))).thenReturn(null);
        when(reviewService.addReview(ArgumentMatchers.any(Review.class))).thenReturn(reviews.get(0));


        String input = "{\n" +
                "    \"userId\": 2,\n" +
                "    \"rating\": 2,\n" +
                "    \"reviewerId\": 3,\n" +
                "    \"transactionId\": 4,\n" +
                "    \"review\": \"It was not the best\"\n" +
                "}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/review/addReview")

        .accept(MediaType.APPLICATION_JSON).content(input).contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }


    //The server could not understand the request due to invalid syntax.
    @Test
    @DisplayName("Test: addReviewIsBadRequest() [Fail]")
    public void testAddReviewIsBadRequest() throws Exception {

        when(mapValidationErrorService.MapValidationService(ArgumentMatchers.any(BindingResult.class))).thenReturn(null);
        when(reviewService.addReview(ArgumentMatchers.any(Review.class))).thenReturn(reviews.get(0));

        String input = "";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/review/addReview")

                .accept(MediaType.APPLICATION_JSON).content(input).contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

    }

    @Test
    @DisplayName("Test: transactionIDIsNull() [Fail]")
    public void testTransactionIDIsNull() throws Exception {

        when(mapValidationErrorService.MapValidationService(ArgumentMatchers.any(BindingResult.class))).thenReturn(null);
        when(reviewService.addReview(ArgumentMatchers.any(Review.class))).thenReturn(reviews.get(0));

        String input = "{\n" +
                "    \"userId\": 2,\n" +
                "    \"rating\": 2,\n" +
                "    \"reviewerId\": 3,\n" +
                "    \"transactionId\": null,\n" +
                "    \"review\": \"It was not the best\"\n" +
                "}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/review/addReview")

                .accept(MediaType.APPLICATION_JSON).content(input).contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NOT_ACCEPTABLE.value(), response.getStatus());
    }

    @Test
    public void testReviewIDIsNull() throws Exception {
        when(mapValidationErrorService.MapValidationService(ArgumentMatchers.any(BindingResult.class))).thenReturn(null);
        when(reviewService.addReview(ArgumentMatchers.any(Review.class))).thenReturn(reviews.get(0));

        String input = "{\n" +
                "    \"userId\": 1,\n" +
                "    \"rating\": 2,\n" +
                "    \"reviewerId\": null,\n" +
                "    \"transactionId\": 4,\n" +
                "    \"review\": \"It was not the best\"\n" +
                "}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/review/addReview")

                .accept(MediaType.APPLICATION_JSON).content(input).contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NOT_ACCEPTABLE.value(), response.getStatus());
    }



    @Test
    public void testGetAllReview() throws Exception {

        when(reviewService.getAllReviews()).thenReturn(reviews);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/review/all").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


}
