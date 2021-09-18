package com.rmit.sept.bk_loginservices.services;

import org.springframework.stereotype.Service;

import com.rmit.sept.bk_loginservices.Repositories.TransactionRepository;
import com.rmit.sept.bk_loginservices.exceptions.TransactionException;
import com.rmit.sept.bk_loginservices.model.Transaction;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    public Iterable<Transaction> getAllByBuyerID(Long buyerID) {
        Iterable<Transaction> transactions = transactionRepository.findByBuyerID(buyerID);
        return transactions;
    }

    public Iterable<Transaction> getAllBySellerID(Long sellerID) {
        Iterable<Transaction> transactions = transactionRepository.findBySellerID(sellerID);
        return transactions;
    }

    public Transaction findById(Long Id) {
        Transaction book = transactionRepository.findById(Id).orElse(null);
        return book;
    }

    
    public Transaction saveTransaction(Transaction transaction) {
        // test to make sure the transaction can be saved
        try {
            transaction.setId(transaction.getId());
            return transactionRepository.save(transaction);
        } catch (Exception e) {
            throw new TransactionException("Transaction Error Exception");
        }
    }

    public Transaction updateTransactionStatus(long status,Transaction transaction){
        long id = transaction.getId();
        //updates just the status
        transactionRepository.updateTransactionStatus(status, id);
        Transaction updateTransaction = transactionRepository.getById(id);
        return updateTransaction;
    }

    public void deleteTransactionById(Long Id) {
        Transaction transaction = transactionRepository.findById(Id).orElse(null);
        transactionRepository.delete(transaction);
    }
    
    public Iterable<Transaction> findAllTransactions() {
        return transactionRepository.findAll();
    }
}
