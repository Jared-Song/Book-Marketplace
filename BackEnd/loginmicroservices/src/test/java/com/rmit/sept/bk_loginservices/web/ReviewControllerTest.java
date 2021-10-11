package com.rmit.sept.bk_loginservices.web;

import com.rmit.sept.bk_loginservices.model.*;
import com.rmit.sept.bk_loginservices.services.MapValidationErrorService;
import com.rmit.sept.bk_loginservices.services.ReviewService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.WebApplicationContext;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class ReviewControllerTest {

/*

    @MockBean
    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;

    @Autowired
    private ReviewController reviewController;

    @MockBean
    MapValidationErrorService mapValidationErrorService;

    List<Review> reviews = new ArrayList<>();

    @BeforeEach
    void setup() {

        this.mockMvc = standaloneSetup(this.reviewController).build();

        User user = new User();
        user.setId(1L);
        user.setRatingTotal(3);
        user.setRatingTotal(2);

        Transaction transaction = new Transaction();
        transaction.setId(1L);

        Book book = new Book();
        book.setId(1L);
        book.setBookStatus(BookStatus.AVAILABLE);
        book.setAuthorName("Arthur Clive");
        book.setPrice(2.00);
        book.setISBN(9023903);
        transaction.setBookID(book);

        transaction.setStatus(TransactionStatus.DELIVERED);
        transaction.setBuyerID(user);

        Review review = new Review();
        review.setReviewer(user);
        review.setTransaction(transaction);
        review.setBookRating(book.getRating());
        review.setUserRating(user.getRatingNo());
        review.setId(1L);


        reviews.add(review);
    }

    @AfterEach
    public void tearDown() {

    }

    @Test
    public void getAllReviews() {
    }

    @Test
    @DisplayName("Test add review success")
    public void addReview() throws Exception {

        // Mocking service
        when(mapValidationErrorService.MapValidationService(ArgumentMatchers.any(BindingResult.class)))
                .thenReturn(null);
        when(reviewService.addReview(ArgumentMatchers.any(Review.class))).thenReturn(reviews.get(0));

        String inputJson = "{\n" + \"userId\": 1\",
                "rating": 2,
                "reviewerId": 3,
                "transactionId": 2,
                "review": "It was not the best" }";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/review/addReview")
                .accept(MediaType.APPLICATION_JSON).content(inputJson).contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

 */
}