package com.rmit.sept.transactions.services;

import com.rmit.sept.transactions.Repositories.TransactionRepository;
import com.rmit.sept.transactions.Repositories.UserRepository;
import com.rmit.sept.transactions.Repositories.BookRepository;
import com.rmit.sept.transactions.exceptions.NotFoundException;
import com.rmit.sept.transactions.exceptions.NotAcceptableException;
import com.rmit.sept.transactions.exceptions.NotAcceptableException;
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

    public Iterable<Transaction> getAllBySellerID(Long sellerID) {
        User seller = userRepository.getById(sellerID);
        Iterable<Book> books = bookRepository.findBySeller(seller);
        Iterable<Transaction> transactions = transactionRepository.findByBookIn(books);
        return transactions;
    }

    public Transaction findById(Long Id) {
        Transaction book = transactionRepository.findById(Id).orElse(null);
        return book;
    }

    
    public Transaction saveTransaction(Transaction transaction) {
        if(transaction.getBuyerID() == null) {
            throw new NotAcceptableException("No buyer ID supplied");
        }

        if(transaction.getBookID() == null) {
            throw new NotAcceptableException("No book ID supplied");
        }

        //find and set user
        User buyer = userRepository.getById(transaction.getBuyerID());
        if (buyer == null) {
            throw new NotFoundException("Unable to find user");
        }
        transaction.setBuyer(buyer);

        //find and set book
        Book book = bookRepository.getById(transaction.getBookID());
        if (book == null) {
            throw new NotFoundException("Unable to find book");
        }
        transaction.setBook(book);


        // test to make sure the transaction can be saved
        try {
            transaction.setId(transaction.getId());
            return transactionRepository.save(transaction);
        } catch (Exception e) {
            throw new NotAcceptableException("Error creating transaction");
        }
    }

    public Transaction updateTransactionStatus(TransactionStatus status, Transaction transaction){
        transaction.setStatus(status);
        //updates just the status
        try {
            return transactionRepository.save(transaction);
        } catch (Exception e) {
            throw new NotAcceptableException("Error updating transaction, could not be saved");
        }

    }

    public void deleteTransactionById(Long Id) {
        Transaction transaction = transactionRepository.findById(Id).orElse(null);
        transactionRepository.delete(transaction);
    }
    
    public Iterable<Transaction> findAllTransactions() {
        return transactionRepository.findAll();
    }
}
