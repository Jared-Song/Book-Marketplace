package com.rmit.sept.transactions.Controllers;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.rmit.sept.transactions.model.*;
import com.rmit.sept.transactions.services.TransactionService;
import com.rmit.sept.transactions.services.MapValidationErrorService;
import com.rmit.sept.transactions.web.TransactionController;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.validation.BindingResult;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class TransactionControllerTest {
    MockMvc mockMvc;

    @Autowired
    TransactionController transactionController;

    @MockBean
    TransactionService transactionService;

    @MockBean
    private MapValidationErrorService mapValidationErrorService;

    private List<Transaction> transactions;
    private Book book;
    private User buyer;

    @BeforeEach
    public void setup() {
        this.mockMvc = standaloneSetup(this.transactionController).build(); // builds an instance of the transaction controller

        book = new Book();
        book.setId(1L);
        buyer = new User();
        buyer.setId(1L);

        Transaction transaction1 = new Transaction();
        transaction1.setId(1L);
        transaction1.setBuyer(buyer);
        transaction1.setBook(book);
        transaction1.setPrice(99.99);
        transaction1.setStatus(TransactionStatus.PROCESSING);
        transaction1.setQuantity(3);
        transaction1.setIsReviewed(false);
        transaction1.setCreatedAt(new Date());

        Transaction transaction2 = new Transaction();
        transaction2.setId(2L);
        transaction2.setBuyer(buyer);
        transaction2.setBook(book);
        transaction2.setPrice(99.99);
        transaction2.setStatus(TransactionStatus.PROCESSING);
        transaction2.setQuantity(3);
        transaction2.setIsReviewed(false);
        transaction2.setCreatedAt(new Date());

        transactions = new ArrayList<Transaction>();
        transactions.add(transaction1);
        transactions.add(transaction2);
    }

    @Test
    @DisplayName("Test getAllTransactions") // test for getting all books
    void testgetAllTransactions() throws Exception {
        // Mocking service
        when(transactionService.findAllTransactions()).thenReturn(transactions);
        mockMvc.perform(get("/api/transactions/all").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1))).andExpect(jsonPath("$[0].buyer.id", is(1)))
                .andExpect(jsonPath("$[0].book.id", is(1))).andExpect(jsonPath("$[0].price", is(99.99)))
                .andExpect(jsonPath("$[0].status", is(TransactionStatus.PROCESSING.toString()))).andExpect(jsonPath("$[0].quantity", is(3)))
                .andExpect(jsonPath("$[0].isReviewed", is(false)))

                .andExpect(jsonPath("$[1].id", is(2))).andExpect(jsonPath("$[1].buyer.id", is(1)))
                .andExpect(jsonPath("$[1].book.id", is(1))).andExpect(jsonPath("$[1].price", is(99.99)))
                .andExpect(jsonPath("$[1].status", is(TransactionStatus.PROCESSING.toString()))).andExpect(jsonPath("$[1].quantity", is(3)))
                .andExpect(jsonPath("$[1].isReviewed", is(false)));

    }

    @Test
    @DisplayName("Test getTransactionById success") // test for getting a transaction sucessfully
    void testGetTransactionByIdSuccess() throws Exception {
        // Mocking service
        when(transactionService.findById(1L)).thenReturn(transactions.get(0));
        mockMvc.perform(get("/api/transactions/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1))).andExpect(jsonPath("buyer.id", is(1)))
                .andExpect(jsonPath("book.id", is(1))).andExpect(jsonPath("price", is(99.99)))
                .andExpect(jsonPath("status", is(TransactionStatus.PROCESSING.toString())))
                .andExpect(jsonPath("quantity", is(3)))
                .andExpect(jsonPath("isReviewed", is(false)));
    }

    @Test
    @DisplayName("Test getTransactionById not found") // test for getting a transaction that doesn't exist
    void testgetTransactionByIdNotFound() throws Exception {
        // Mocking service
        when(transactionService.findById(3L)).thenReturn(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/books/3")
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());

    }
}
