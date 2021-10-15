package com.rmit.sept.requests.services;

import java.util.Date;

import com.rmit.sept.requests.Repositories.RequestRepository;
import com.rmit.sept.requests.Repositories.TransactionRepository;
import com.rmit.sept.requests.exceptions.NotAcceptableException;
import com.rmit.sept.requests.model.Request;
import com.rmit.sept.requests.model.RequestType;
import com.rmit.sept.requests.model.Transaction;
import com.rmit.sept.requests.model.TransactionStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    private final long TWO_HOURS_IN_MILLISECONDS = 7200000;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private RequestRepository requestRepository;



    public Transaction findById(Long Id) {
        Transaction transaction = transactionRepository.findById(Id).orElse(null);
        return transaction;
    }

    public Transaction updateTransactionStatus(TransactionStatus status, Transaction transaction) {
        transaction.setStatus(status);
        // updates just the status
        try {
            return transactionRepository.save(transaction);
        } catch (Exception e) {
            throw new NotAcceptableException("Error updating transaction, could not be saved");
        }

    }


    public boolean refundTransaction(Transaction transaction) {
        boolean refunded = false;
        Date created = transaction.getCreatedAt();
        Date current = new Date();
        // check if transaction was created within 2 hours
        if (current.getTime() - created.getTime() > TWO_HOURS_IN_MILLISECONDS) {
            Request request = new Request();
            request.setRequestType(RequestType.REFUND_TRANSACTION);
            request.setUser(transaction.getBuyer());
            request.setUserId(transaction.getBuyerID());
            request.setRequest(transaction.getBuyer().getUsername() + " is requesting a refund for transaction with id "
                    + transaction.getId());
            requestRepository.save(request);
            transaction.setStatus(TransactionStatus.REFUND_PENDING);
        } else {
            transaction.setStatus(TransactionStatus.REFUNDED);
            refunded = true;
        }
        transactionRepository.save(transaction);
        return refunded;
    }
}
