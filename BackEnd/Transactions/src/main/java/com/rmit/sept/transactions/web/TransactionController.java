package com.rmit.sept.transactions.web;

import javax.validation.Valid;

import com.rmit.sept.transactions.TransactionsApplication;
import com.rmit.sept.transactions.exceptions.NotFoundException;
import com.rmit.sept.transactions.model.Transaction;
import com.rmit.sept.transactions.model.TransactionStatus;
import com.rmit.sept.transactions.services.MapValidationErrorService;
import com.rmit.sept.transactions.services.TransactionService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/transactions")
public class TransactionController {
    private static final Logger LOGGER = LogManager.getLogger(TransactionsApplication.class);

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    // get all transactions within the system
    @GetMapping(path = "/all")
    public ResponseEntity<?> getAllTransactions() {
        LOGGER.trace("Finding all transactions");
        Iterable<Transaction> transactions = transactionService.findAllTransactions();

        // test if any transactions were found
        if (!transactions.iterator().hasNext()) {
            LOGGER.warn("No transactions were found");
            throw new NotFoundException("No transactions found");
        }
        return new ResponseEntity<Iterable<Transaction>>(transactions, HttpStatus.OK);
    }

    // get a transaction by its transaction id
    @GetMapping(path = "/{transactionId}")
    public ResponseEntity<?> getTransactionById(@PathVariable Long transactionId) {
        LOGGER.trace("Finding transaction with ID " + transactionId);
        Transaction transaction = transactionService.findById(transactionId);
        // test if a transaction was found
        if (transaction == null) {
            LOGGER.warn("Transaction with ID " + transactionId + " was not found");
            throw new NotFoundException("Transaction with ID '" + transactionId + "' does not exist");
        }
        return new ResponseEntity<Transaction>(transaction, HttpStatus.OK);
    }

    // delete a transaction by its transaction id
    @DeleteMapping(path = "/{transactionId}")
    public ResponseEntity<?> deleteTransaction(@PathVariable Long transactionId) {
        LOGGER.trace("Finding transaction with ID " + transactionId);
        Transaction transaction = transactionService.findById(transactionId);
        // test if a transaction was found
        if (transaction == null) {
            LOGGER.warn("Transaction with ID " + transactionId + " was not found");
            throw new NotFoundException("Transaction with ID '" + transactionId + "' does not exist");
        }
        LOGGER.trace("Deleting transaction with ID " + transactionId);
        transactionService.deleteTransactionById(transactionId);
        return new ResponseEntity<String>("Transaction with ID " + transactionId + " was deleted", HttpStatus.OK);
    }

    // get transactions by buyer ID
    @GetMapping(path = "/buyer/{buyerId}")
    public ResponseEntity<?> getAllTransactionByBuyerId(@PathVariable Long buyerId) {
        LOGGER.trace("Finding transaction with the buyer ID " + buyerId);
        Iterable<Transaction> transactions = transactionService.getAllByBuyerID(buyerId);
        if (!transactions.iterator().hasNext()) {
            LOGGER.warn("No transactions were found with buyer ID " + buyerId);
            throw new NotFoundException("No transactions found with buyer ID '" + buyerId + "'");
        }
        return new ResponseEntity<Iterable<Transaction>>(transactions, HttpStatus.OK);
    }

    // get transactions by seller ID
    @GetMapping(path = "/seller/{sellerId}")
    public ResponseEntity<?> getAllTransactionBySellerId(@PathVariable Long sellerId) {
        LOGGER.trace("Finding transaction with the buyer ID " + sellerId);
        Iterable<Transaction> transactions = transactionService.getAllBySellerID(sellerId);
        // test if any transactions were found
        if (!transactions.iterator().hasNext()) {
            LOGGER.warn("No transactions were found with seller ID " + sellerId);
            throw new NotFoundException("No transactions found with seller ID '" + sellerId + "'");
        }
        return new ResponseEntity<Iterable<Transaction>>(transactions, HttpStatus.OK);
    }

    // create a new transaction
    @PostMapping("/new")
    public ResponseEntity<?> createNewTransaction(@Valid @RequestBody Transaction transaction, BindingResult result) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) {
            LOGGER.warn("The transaction's details are invalid and the transaction cannot be added");
            return errorMap;
        }
        LOGGER.trace("The new transaction has been successfully added");
        Transaction newTransaction = transactionService.saveTransaction(transaction);
        return new ResponseEntity<Transaction>(newTransaction, HttpStatus.CREATED);
    }

    // update the status of a transaction
    @PostMapping("/updateStatus/{transactionId}")
    @ResponseBody
    public ResponseEntity<?> updateTransactionStatus(@RequestBody Transaction transaction,
            @PathVariable Long transactionId) {

        TransactionStatus status = transaction.getStatus();
        LOGGER.trace("Finding transaction with ID " + transactionId);
        Transaction transaction2 = transactionService.findById(transactionId);
        if (transaction2 != null) {
            LOGGER.trace("Updating the status for the transaction with ID " + transactionId);
            Transaction updateTransaction = transactionService.updateTransactionStatus(status, transaction2);
            return new ResponseEntity<Transaction>(updateTransaction, HttpStatus.OK);
        } else {
            LOGGER.warn("transaction with ID " + transactionId + " was not found");
            throw new NotFoundException("Transaction with ID " + transactionId + " was not found");
        }
    }

    // refund transaction by id
    @GetMapping("/refund/{transactionId}")
    public ResponseEntity<?> refundTransaction(@PathVariable Long transactionId) {
        LOGGER.trace("Finding transaction with ID " + transactionId);
        Transaction transaction = transactionService.findById(transactionId);
        // test if a transaction was found
        if (transaction == null) {
            LOGGER.warn("transaction with ID " + transactionId + " was not found");
            throw new NotFoundException("Transaction with ID '" + transactionId + "' does not exist");
        }
        LOGGER.trace("Refunding transaction with ID " + transactionId);
        boolean refunded = transactionService.refundTransaction(transaction);
        if (refunded) {
            LOGGER.trace("Refund was successful for transaction with ID " + transactionId);
            return new ResponseEntity<String>("Refund successful.", HttpStatus.OK);
        } else {
            LOGGER.trace("Refund request was created for transaction with ID " + transactionId);
            return new ResponseEntity<String>("Refund request was created.", HttpStatus.OK);
        }

    }

}