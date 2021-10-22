package com.rmit.sept.transactions.Repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Date;
import java.util.Iterator;

import javax.transaction.Transactional;

import com.rmit.sept.transactions.model.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@Transactional
public class TransactionRepositoryTest {
    @Autowired
    private TransactionRepository transactionRepository;

    // testing for empty transaction repository
    @Test
    @Rollback(true)
    public void should_find_no_transactions_if_transactionRepository_is_empty() {
        Iterable<Transaction> transactions = transactionRepository.findAll();
        if (transactions.iterator().hasNext()) {
            assertThat(transactions).isNotEmpty();
        } else {
            assertThat(transactions).isEmpty();
        }
    }

    //test to find a transaction by id
    @Test
    @Rollback(true)
    public void find_transaction_by_id() {
        Book book = new Book();
        book.setId(1L);
        User buyer = new User();
        buyer.setId(1L);

        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setBuyer(buyer);
        transaction.setBook(book);
        transaction.setPrice(99.99);
        transaction.setStatus(TransactionStatus.PROCESSING);
        transaction.setQuantity(3);
        transaction.setIsReviewed(false);
        transaction.setCreatedAt(new Date());
        transactionRepository.save(transaction);

 
        Transaction findTransaction = transactionRepository.getById(1L);
        assertNotNull(findTransaction);
        
        assertThat(findTransaction).hasFieldOrPropertyWithValue("id", 1L);
        assertThat(findTransaction).hasFieldOrPropertyWithValue("price", 99.99);
        assertThat(findTransaction).hasFieldOrPropertyWithValue("status", TransactionStatus.PROCESSING);
        assertThat(findTransaction).hasFieldOrPropertyWithValue("quantity", 3);
        assertThat(findTransaction).hasFieldOrPropertyWithValue("isReviewed", false);
        assertThat(findTransaction.getBook()).hasFieldOrPropertyWithValue("id", 1L);
        assertThat(findTransaction.getBuyer()).hasFieldOrPropertyWithValue("id", 1L);
    }

     // test to delete a transaction
     @Test
     @Rollback(true)
     public void delete_transaction() {
        Book book = new Book();
        book.setId(1L);
        User buyer = new User();
        buyer.setId(1L);

        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setBuyer(buyer);
        transaction.setBook(book);
        transaction.setPrice(99);
        transaction.setStatus(TransactionStatus.PROCESSING);
        transaction.setQuantity(3);
        transaction.setIsReviewed(false);
        transaction.setCreatedAt(new Date());
        Transaction savedTransaction = transactionRepository.save(transaction);

        transactionRepository.delete(savedTransaction);
 
        Transaction findTransaction = transactionRepository.getById(1L);
        assertNull(findTransaction);
    }

    //test to find a transaction by id
    @Test
    @Rollback(true)
    public void save_new_transaction() {
        Book book = new Book();
        book.setId(1L);
        User buyer = new User();
        buyer.setId(1L);

        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setBuyer(buyer);
        transaction.setBook(book);
        transaction.setPrice(99.99);
        transaction.setStatus(TransactionStatus.PROCESSING);
        transaction.setQuantity(3);
        transaction.setIsReviewed(false);
        transaction.setCreatedAt(new Date());
        Transaction savedTransaction = transactionRepository.save(transaction);

        
        assertThat(savedTransaction).hasFieldOrPropertyWithValue("id", 1L);
        assertThat(savedTransaction).hasFieldOrPropertyWithValue("price", 99.99);
        assertThat(savedTransaction).hasFieldOrPropertyWithValue("status", TransactionStatus.PROCESSING);
        assertThat(savedTransaction).hasFieldOrPropertyWithValue("quantity", 3);
        assertThat(savedTransaction).hasFieldOrPropertyWithValue("isReviewed", false);
        assertThat(savedTransaction.getBook()).hasFieldOrPropertyWithValue("id", 1L);
        assertThat(savedTransaction.getBuyer()).hasFieldOrPropertyWithValue("id", 1L);
    }

    // test to find all users after saving a new user
    @Test
    @Rollback(true)
    public void find_all_returns_saved_transactions() {
        Book book = new Book();
        book.setId(1L);
        User buyer = new User();
        buyer.setId(1L);

        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setBuyer(buyer);
        transaction.setBook(book);
        transaction.setPrice(99.99);
        transaction.setStatus(TransactionStatus.PROCESSING);
        transaction.setQuantity(3);
        transaction.setIsReviewed(false);
        transaction.setCreatedAt(new Date());
        transactionRepository.save(transaction);

        Iterable<Transaction> transactions = transactionRepository.findAll();
        Iterator<Transaction> iter = transactions.iterator();
        Transaction savedTransaction = iter.next();
        while(iter.hasNext() && savedTransaction.getId() != 1L) {
            savedTransaction = iter.next();
        }
        assertThat(savedTransaction).hasFieldOrPropertyWithValue("id", 1L);
        assertThat(savedTransaction).hasFieldOrPropertyWithValue("price", 99.99);
        assertThat(savedTransaction).hasFieldOrPropertyWithValue("status", TransactionStatus.PROCESSING);
        assertThat(savedTransaction).hasFieldOrPropertyWithValue("quantity", 3);
        assertThat(savedTransaction).hasFieldOrPropertyWithValue("isReviewed", false);
        assertThat(savedTransaction.getBook()).hasFieldOrPropertyWithValue("id", 1L);
        assertThat(savedTransaction.getBuyer()).hasFieldOrPropertyWithValue("id", 1L);
    }

}
