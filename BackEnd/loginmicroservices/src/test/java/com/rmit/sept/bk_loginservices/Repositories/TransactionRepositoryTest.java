package com.rmit.sept.bk_loginservices.Repositories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.rmit.sept.bk_loginservices.model.Transaction;

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

    Transaction TRANSACTION_1 = new Transaction(1l, 1l, 1l, 1l, 12);
    Transaction TRANSACTION_2 = new Transaction(2l, 1l, 1l, 1l, 12);
    Transaction TRANSACTION_3 = new Transaction(1l, 2l, 1l, 1l, 12);
    Transaction TRANSACTION_4 = new Transaction(1l, 2l, 1l, 1l, 12);

    @Test
    public void findNoTransctions_success() {
        List<Transaction> transactions = new ArrayList<Transaction>();
        assertEquals(transactions, transactionRepository.findAll());
    }

    @Test
    public void saveTransaction_success() {
        Transaction savedTransaction = transactionRepository.save(TRANSACTION_1);
        assertThat(savedTransaction).hasFieldOrPropertyWithValue("buyerID", 1l);
        assertThat(savedTransaction).hasFieldOrPropertyWithValue("sellerID", 1l);
        assertThat(savedTransaction).hasFieldOrPropertyWithValue("bookID", 1l);
        assertThat(savedTransaction).hasFieldOrPropertyWithValue("status", 1l);
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

    @Test
    public void getTransactionsByID_success() {
        transactionRepository.save(TRANSACTION_1);
        transactionRepository.save(TRANSACTION_2);
        transactionRepository.save(TRANSACTION_3);
        transactionRepository.save(TRANSACTION_4);

        assertEquals(TRANSACTION_1, transactionRepository.findById(1l).orElse(null));
    }

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

        assertEquals(transactions, transactionRepository.findByBuyerID(1l));
    }

    @Test
    public void getTransactionsBySellerID_success() {
        List<Transaction> transactions = new ArrayList<Transaction>(Arrays.asList(TRANSACTION_1, TRANSACTION_2));
        transactionRepository.save(TRANSACTION_1);
        transactionRepository.save(TRANSACTION_2);
        transactionRepository.save(TRANSACTION_3);
        transactionRepository.save(TRANSACTION_4);

        assertEquals(transactions, transactionRepository.findBySellerID(1l));
    }

    @Test
    public void updateTransaction_success() {
        Transaction savedTransaction = transactionRepository.save(TRANSACTION_1);
        assertThat(savedTransaction).hasFieldOrPropertyWithValue("buyerID", 1l);
        TRANSACTION_1.setBuyerID(2l);
        transactionRepository.save(TRANSACTION_1);
        assertThat(savedTransaction).hasFieldOrPropertyWithValue("buyerID", 2l);
    }


}
    