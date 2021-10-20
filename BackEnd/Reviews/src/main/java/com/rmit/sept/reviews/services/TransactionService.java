package com.rmit.sept.reviews.services;

import com.rmit.sept.reviews.Repositories.TransactionRepository;
import com.rmit.sept.reviews.model.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction findById(Long Id) {
        Transaction book = transactionRepository.findById(Id).orElse(null);
        return book;
    }
}
