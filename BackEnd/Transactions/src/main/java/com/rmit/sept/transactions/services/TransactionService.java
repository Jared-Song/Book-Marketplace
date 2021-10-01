package com.rmit.sept.transactions.services;

import com.rmit.sept.transactions.Repositories.TransactionRepository;
import com.rmit.sept.transactions.Repositories.UserRepository;
import com.rmit.sept.transactions.Repositories.BookRepository;
import com.rmit.sept.transactions.exceptions.TransactionException;
import com.rmit.sept.transactions.model.Transaction;
import com.rmit.sept.transactions.model.TransactionStatus;
import com.rmit.sept.transactions.model.User;
import com.rmit.sept.transactions.model.Book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    

    public Iterable<Transaction> getAllByBuyerID(Long buyerID) {
        Iterable<Transaction> transactions = transactionRepository.findByBuyer(userRepository.getById(buyerID));
        return transactions;
    }

    public Transaction findById(Long Id) {
        Transaction book = transactionRepository.findById(Id).orElse(null);
        return book;
    }

    
    public Transaction saveTransaction(Transaction transaction) {
        //find and set user
        User buyer = userRepository.getById(transaction.getBuyerID());
        if (buyer == null) {
            throw new TransactionException("Unable to find user");
        }
        transaction.setBuyer(buyer);

        Book book = bookRepository.getById(transaction.getBookID());
        if (book == null) {
            throw new TransactionException("Unable to find book");
        }
        transaction.setBook(book);


        // test to make sure the transaction can be saved
        try {
            transaction.setId(transaction.getId());
            return transactionRepository.save(transaction);
        } catch (Exception e) {
            throw new TransactionException("Error creating transaction");
        }
    }

    public Transaction updateTransactionStatus(TransactionStatus status, Transaction transaction){
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
