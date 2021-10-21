package com.rmit.sept.transactions.Services;


import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.rmit.sept.transactions.Repositories.TransactionRepository;
import com.rmit.sept.transactions.Repositories.UserRepository;
import com.rmit.sept.transactions.Repositories.BookRepository;
import com.rmit.sept.transactions.services.TransactionService;
import com.rmit.sept.transactions.model.*;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)

public class TransactionServiceTest {
    @Autowired
    private TransactionService transactionService;
    @MockBean
    private TransactionRepository transactionRepository;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private UserRepository userRepository;

    private List<Transaction> transactions;
    private Transaction transaction1, transaction2;
    private List<Book> books;


    @BeforeEach
    public void setup() {
        Book book = new Book();
        book.setId(1L);
        User buyer = new User();
        buyer.setId(1L);

        transaction1 = new Transaction();
        transaction1.setId(1L);
        transaction1.setBuyer(buyer);
        transaction1.setBook(book);
        transaction1.setPrice(99.99);
        transaction1.setStatus(TransactionStatus.PROCESSING);
        transaction1.setQuantity(3);
        transaction1.setIsReviewed(false);
        transaction1.setCreatedAt(new Date());

        transaction2 = new Transaction();
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

        books = new ArrayList<Book>();
        books.add(book);
    }

    @Test
    @DisplayName("Test findAllTranscations success") // test for finding all transcation
    public void testFindAllTransactionsSuccess() throws Exception {
        given(transactionRepository.findAll()).willReturn(transactions);
        Iterable<Transaction> allTransactions = transactionService.findAllTransactions();
        Assert.assertNotNull(allTransactions);
    }

    @Test
    @DisplayName("Test getAllBySellerID success") // test for finding all transaction by seller ID successfully
    public void testGetAllBySellerIDSuccess() throws Exception {
        User user = new User();
        given(userRepository.getById(1L)).willReturn(user);
        given(bookRepository.findBySeller(user)).willReturn(books);
        given(transactionRepository.findByBookIn(books)).willReturn(transactions);
        Iterable<Transaction> allTransactions = transactionService.getAllBySellerID(1L);
        Assert.assertNotNull(allTransactions);
    }

    @Test
    @DisplayName("Test getAllBySellerID fail") // test for finding all transaction by seller ID that doesn't exist
    public void testGetAllBySellerIDfail() throws Exception {
        given(userRepository.getById(null)).willReturn(null);
        given(bookRepository.findBySeller(null)).willReturn(null);
        given(transactionRepository.findByBookIn(null)).willReturn(null);
        Iterable<Transaction> allTransactions = transactionService.getAllBySellerID(null);
        Assert.assertNull(allTransactions);
    }

    @Test
    @DisplayName("Test getAllByBuyerID success") // test for finding all transaction by buyer ID successfully
    public void testGetAllByBuyerIDSuccess() throws Exception {
        User user = new User();
        given(userRepository.getById(1L)).willReturn(user);
        given(transactionRepository.findByBuyer(user)).willReturn(transactions);
        Iterable<Transaction> allTransactions = transactionService.getAllByBuyerID(1L);
        Assert.assertNotNull(allTransactions);
    }

    @Test
    @DisplayName("Test getAllByBuyerID fail") // test for finding all transaction by buyer ID that doesn't exist
    public void testGetAllByBuyerIDFail() throws Exception {
        given(userRepository.getById(null)).willReturn(null);
        given(transactionRepository.findByBuyer(null)).willReturn(null);
        Iterable<Transaction> allTransactions = transactionService.getAllByBuyerID(null);
        Assert.assertNull(allTransactions);
    }

}
