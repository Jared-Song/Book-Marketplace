package com.rmit.sept.bk_loginservices.web;

import com.rmit.sept.bk_loginservices.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
class ReviewControllerTest {

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {

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
    }

    @Test
    @DisplayName("Test delete review success")
    void delete() throws UnsupportedEncodingException {
        // Mocking service
//      when(userService.findById(1L)).thenReturn(users.get(0));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/reviews/delete/1");

        //ConsoleIOContext.AllSuggestionsCompletionTask mockMvc = null;
        //MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        //MockHttpServletResponse response = result.getResponse();

        //assertEquals(HttpStatus.OK.value(), response.getStatus());
        //assertEquals("User with ID 1 was deleted", response.getContentAsString());
    }


    @AfterEach
    void tearDown() {

    }

    @Test
    void getAllReviews() {
    }

    @Test
    @DisplayName("Test add review success")
    void addReview() {

    }



    @Test
    void testGetAllReviews() {
    }

    @Test
    void testAddReview() {
    }

    @Test
    void testDelete() {
    }
}