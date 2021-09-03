package com.rmit.sept.bk_loginservices.services;

import org.springframework.stereotype.Service;

import java.util.Date;

import com.rmit.sept.bk_loginservices.Repositories.TransactionRepository;
import com.rmit.sept.bk_loginservices.exceptions.TransactionException;
import com.rmit.sept.bk_loginservices.model.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    public Iterable<Transaction> getAllByBuyerID(Long buyerID) {
        Iterable<Transaction> transactions = transactionRepository.findByBuyerID(buyerID);
        return transactions;
    }

    public Transaction saveTransaction(Transaction transaction) {
        try {
            transaction.setId(transaction.getId());
            return transactionRepository.save(transaction);
        } catch (Exception e) {
            throw new TransactionException("Transaction Error Exception");
        }
    }

    public Iterable<Transaction> findAllTransactions() {
        return transactionRepository.findAll();
    }
}
