package com.rmit.sept.bk_loginservices.web;

import javax.validation.Valid;

import com.rmit.sept.bk_loginservices.model.Transaction;
import com.rmit.sept.bk_loginservices.services.TransactionService;
import com.rmit.sept.bk_loginservices.services.MapValidationErrorService;

import org.apache.catalina.connector.Response;
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

    @GetMapping(path = "/all")
    public ResponseEntity<?> getAllTransactions() {
        Iterable<Transaction> transactions = transactionService.findAllTransactions();
        System.out.println(transactions);
        if(!transactions.iterator().hasNext()){
            return new ResponseEntity<String>("No transactions found", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Iterable<Transaction>>(transactions, HttpStatus.OK);
    }

    @GetMapping(path = "/{Id}")
    public ResponseEntity<?> getTransactionById(@PathVariable Long Id) {
        Transaction transaction = transactionService.findById(Id);
        if(transaction == null){
            return new ResponseEntity<String>("Transaction with ID '" + Id + "' does not exist", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Transaction>(transaction, HttpStatus.OK);
    }

    @GetMapping(path = "/buyer/{buyerID}")
    public ResponseEntity<?> getAllTransactionByBuyerID(@PathVariable Long buyerID) {
        Iterable<Transaction> transactions = transactionService.getAllByBuyerID(buyerID);
        System.out.println(transactions);
        if(!transactions.iterator().hasNext()){
            return new ResponseEntity<String>("No transactions found", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Iterable<Transaction>>(transactions, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<?> createNewTransaction(@Valid @RequestBody Transaction transaction, BindingResult result) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null)
            return errorMap;

        Transaction newTransaction = transactionService.saveTransaction(transaction);

        return new ResponseEntity<Transaction>(newTransaction, HttpStatus.CREATED);
    }
}