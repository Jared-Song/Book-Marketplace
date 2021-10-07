package com.rmit.sept.transactions.web;

import javax.validation.Valid;

import com.rmit.sept.transactions.model.Transaction;
import com.rmit.sept.transactions.model.TransactionStatus;
import com.rmit.sept.transactions.services.MapValidationErrorService;
import com.rmit.sept.transactions.services.TransactionService;
import com.rmit.sept.transactions.exceptions.NotFoundException;

import org.hibernate.annotations.NotFound;
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
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    // get all transactions within the system
    @GetMapping(path = "/all")
    public ResponseEntity<?> getAllTransactions() {
        Iterable<Transaction> transactions = transactionService.findAllTransactions();
        System.out.println(transactions);
        //test if any transactions were found
        if(!transactions.iterator().hasNext()){
            throw new NotFoundException("No transactions found");
        }
        return new ResponseEntity<Iterable<Transaction>>(transactions, HttpStatus.OK);
    }

    // get a transaction by its transaction id
    @GetMapping(path = "/{Id}")
    public ResponseEntity<?> getTransactionById(@PathVariable Long Id) {
        Transaction transaction = transactionService.findById(Id);
        //test if a transaction was found
        if(transaction == null){
            throw new NotFoundException("Transaction with ID '" + Id + "' does not exist");
        }
        return new ResponseEntity<Transaction>(transaction, HttpStatus.OK);
    }

    // delete a transaction by its transaction id
    @DeleteMapping(path = "/{Id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable Long Id) {
        Transaction transaction = transactionService.findById(Id);
        //test if a transaction was found
        if(transaction == null){
            throw new NotFoundException("Transaction with ID '" + Id + "' does not exist");
        }
        transactionService.deleteTransactionById(Id);
        return new ResponseEntity<String>("Transaction with ID " + Id + " was deleted", HttpStatus.OK);
    }

    // get transactions by buyer ID
    @GetMapping(path = "/buyer/{buyerID}")
    public ResponseEntity<?> getAllTransactionByBuyerID(@PathVariable Long buyerID) {
        Iterable<Transaction> transactions = transactionService.getAllByBuyerID(buyerID);
        if(!transactions.iterator().hasNext()){
            throw new NotFoundException("No transactions found with buyer ID '" + buyerID + "'");
        }
        return new ResponseEntity<Iterable<Transaction>>(transactions, HttpStatus.OK);
    }

    //get transactions by seller ID
    @GetMapping(path = "/seller/{sellerID}")
    public ResponseEntity<?> getAllTransactionBySellerID(@PathVariable Long sellerID) {
        Iterable<Transaction> transactions = transactionService.getAllBySellerID(sellerID);
        //test if any transactions were found
        if(!transactions.iterator().hasNext()){
            throw new NotFoundException("No transactions found with seller ID '" + sellerID + "'");
        }
        return new ResponseEntity<Iterable<Transaction>>(transactions, HttpStatus.OK);
    }

    // create a new transaction
    @PostMapping("/new")
    public ResponseEntity<?> createNewTransaction(@Valid @RequestBody Transaction transaction, BindingResult result) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null)
            return errorMap;

        Transaction newTransaction = transactionService.saveTransaction(transaction);

        return new ResponseEntity<Transaction>(newTransaction, HttpStatus.CREATED);
    }

    // update the status of a transaction
    @PostMapping("/{Id}/updateStatus")
    @ResponseBody
    public ResponseEntity<?> updateTransactionStatus(@RequestBody Transaction transaction, @PathVariable Long Id) {
        TransactionStatus status = transaction.getStatus();
        Transaction transaction2 = transactionService.findById(Id);
        if (transaction2 != null) {
            Transaction updateTransaction = transactionService.updateTransactionStatus(status, transaction2);
            return new ResponseEntity<Transaction>(updateTransaction, HttpStatus.OK);
        } else {
           throw new NotFoundException("Transaction with ID " + Id + " was not found");
        }
    }

    //refund transaction by id
    @GetMapping("/{Id}/refund")
    public ResponseEntity<?> refundTransaction(@PathVariable Long Id) {
        Transaction transaction = transactionService.findById(Id);
        //test if a transaction was found
        if(transaction == null){
            throw new NotFoundException("Transaction with ID '" + Id + "' does not exist");
        }
        boolean refunded = transactionService.refundTransaction(transaction);
        if (refunded) {
            return new ResponseEntity<String>("Refund successful.", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Refund request was created.", HttpStatus.OK);
        }
        
    }

}