package com.rmit.sept.bk_loginservices.Repositories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.rmit.sept.bk_loginservices.model.Book;
import com.rmit.sept.bk_loginservices.model.BookImage;
import com.rmit.sept.bk_loginservices.model.BookStatus;
import com.rmit.sept.bk_loginservices.model.Quality;
import com.rmit.sept.bk_loginservices.model.ServiceType;
import com.rmit.sept.bk_loginservices.model.Transaction;
import com.rmit.sept.bk_loginservices.model.TransactionStatus;
import com.rmit.sept.bk_loginservices.model.User;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;

    User USER_1 = new User("johndoe@gmail.com", "JohnDoe", "John Doe", "password", "1 John Street, Doeland");
    User USER_2 = new User("johndoe@gmail.com", "JohnDoe2", "John Doe", "password", "1 John Street, Doeland");
    List<BookImage> imageUrl = Arrays.asList(new BookImage(1l, "imageurl", 1));
    Book BOOK_1 = new Book(0l, "title", "authorname", USER_2, 1, 5, "category", Quality.NEW, imageUrl, 0.0, ServiceType.E_BOOK, BookStatus.AVAILABLE);
    Book BOOK_2 = new Book(1l, "title", "authorname", USER_1, 1, 5, "category", Quality.NEW, imageUrl, 0.0, ServiceType.E_BOOK, BookStatus.AVAILABLE);


    Transaction TRANSACTION_1 = new Transaction(USER_1, BOOK_1, TransactionStatus.PROCESSING, 12.0);
    //Transaction TRANSACTION_1 = new Transaction(1l, 1l, 1l, 1l, 12);
    Transaction TRANSACTION_2 = new Transaction(USER_2, BOOK_1, TransactionStatus.PROCESSING, 12.0);
    // Transaction TRANSACTION_2 = new Transaction(2l, 1l, 1l, 1l, 12);
    Transaction TRANSACTION_3 = new Transaction(USER_1, BOOK_2, TransactionStatus.PROCESSING, 12.0);
    // Transaction TRANSACTION_3 = new Transaction(1l, 2l, 1l, 1l, 12);
    Transaction TRANSACTION_4 = new Transaction(USER_2, BOOK_2, TransactionStatus.PROCESSING, 12.0);
    // Transaction TRANSACTION_4 = new Transaction(1l, 2l, 1l, 1l, 12);

    @Test
    public void findNoTransctions_success() {
        List<Transaction> transactions = new ArrayList<Transaction>();
        assertEquals(transactions, transactionRepository.findAll());
    }

    @Test
    public void saveTransaction_success() {
        Transaction savedTransaction = transactionRepository.save(TRANSACTION_1);
        assertThat(savedTransaction).hasFieldOrPropertyWithValue("buyerID", USER_1);
        assertThat(savedTransaction).hasFieldOrPropertyWithValue("bookID", BOOK_1);
        assertThat(savedTransaction).hasFieldOrPropertyWithValue("status", TransactionStatus.PROCESSING);
        assertThat(savedTransaction).hasFieldOrPropertyWithValue("price", 12.0);
    }

    @Test
    public void getAllTransactions_success() {
        List<Transaction> transactions = new ArrayList<Transaction>(Arrays.asList(TRANSACTION_1, TRANSACTION_2, TRANSACTION_3, TRANSACTION_4));
        transactionRepository.save(TRANSACTION_1);
        transactionRepository.save(TRANSACTION_2);
        transactionRepository.save(TRANSACTION_3);
        transactionRepository.save(TRANSACTION_4);

        assertEquals(transactions, transactionRepository.findAll());
    }

    @Test
    public void deleteTransaction_success() {
        List<Transaction> transactions = new ArrayList<Transaction>(Arrays.asList(TRANSACTION_1));
        transactionRepository.save(TRANSACTION_1);
        assertEquals(transactions, transactionRepository.findAll());
        transactionRepository.delete(TRANSACTION_1);
        transactions.clear();
        assertEquals(transactions, transactionRepository.findAll());
    }

    // @Test
    // public void getTransactionsByID_success() {
    //     transactionRepository.save(TRANSACTION_1);
    //     transactionRepository.save(TRANSACTION_2);
    //     transactionRepository.save(TRANSACTION_3);
    //     transactionRepository.save(TRANSACTION_4);

    //     assertEquals(TRANSACTION_1, transactionRepository.findById(1l).orElse(null));
    // }

    @Test
    public void getTransactionsByID_null() {
        transactionRepository.save(TRANSACTION_1);
        transactionRepository.save(TRANSACTION_2);
        transactionRepository.save(TRANSACTION_3);
        transactionRepository.save(TRANSACTION_4);

        assertEquals(null, transactionRepository.findById(5l).orElse(null));
    }

    @Test
    public void getTransactionsByBuyerID_success() {
        List<Transaction> transactions = new ArrayList<Transaction>(Arrays.asList(TRANSACTION_1, TRANSACTION_3, TRANSACTION_4));
        transactionRepository.save(TRANSACTION_1);
        transactionRepository.save(TRANSACTION_2);
        transactionRepository.save(TRANSACTION_3);
        transactionRepository.save(TRANSACTION_4);

        assertEquals(transactions, transactionRepository.findByBuyerID(USER_1));
    }

    @Test
    public void getTransactionsBySellerID_success() {
        List<Transaction> transactions = new ArrayList<Transaction>(Arrays.asList(TRANSACTION_1, TRANSACTION_2));
        transactionRepository.save(TRANSACTION_1);
        transactionRepository.save(TRANSACTION_2);
        transactionRepository.save(TRANSACTION_3);
        transactionRepository.save(TRANSACTION_4);

        assertEquals(transactions, transactionRepository.findByBookID(BOOK_1));
    }

    @Test
    public void updateTransaction_success() {
        Transaction savedTransaction = transactionRepository.save(TRANSACTION_1);
        assertThat(savedTransaction).hasFieldOrPropertyWithValue("buyerID", USER_2);
        TRANSACTION_1.setBuyerID(USER_2);
        transactionRepository.save(TRANSACTION_1);
        assertThat(savedTransaction).hasFieldOrPropertyWithValue("buyerID", USER_2);
    }


}
    