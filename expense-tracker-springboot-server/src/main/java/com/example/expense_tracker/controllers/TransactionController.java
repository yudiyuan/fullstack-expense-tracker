package com.example.expense_tracker.controllers;


import com.example.expense_tracker.entities.Transaction;
import com.example.expense_tracker.repositories.TransactionRepository;
import com.example.expense_tracker.repositories.UserRepository;
import com.example.expense_tracker.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionController {
    private static final Logger logger = Logger.getLogger(TransactionController.class.getName());

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    //get
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Transaction>> getAllTransactionsByUserIdAndYear(@PathVariable int userId,
                                                                               @RequestParam int year){
        logger.info("get all transactions by userId "+userId+"@"+year);
        List<Transaction> transactionsList=transactionService.getAllTransactionsByUserIdAndYear(userId,year);
        return ResponseEntity.status(HttpStatus.OK).body(transactionsList);
    }

    @GetMapping("/recent/user/{userId}")
    public ResponseEntity<List<Transaction>> getRecentTransactionsByUserId(
            @PathVariable int userId,
            @RequestParam int startPage,
            @RequestParam int endPage,
            @RequestParam int size
            ) {
        logger.info("Getting transactions by userId " + userId+", Page: " + startPage + ", " + endPage+ ")");
        List<Transaction> recentTransactionList = transactionService.getRecentTransactionsByUserId(
                userId,
                startPage,
                endPage,
                size
        );

        return ResponseEntity.status(HttpStatus.OK).body(recentTransactionList);
    }

    @GetMapping("/years/{userId}")
    public ResponseEntity<List<Integer>> getDistinctTransactionYears(@PathVariable  int userId)
    {
        logger.info("Getting distinct years by userId " + userId);

        return ResponseEntity.status(HttpStatus.OK).body(transactionService.getDistinctTransactionYears(userId));
    }

    //post
    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction){
        logger.info("Creating Transaction");
        Transaction newTransaction = transactionService.createTransaction(transaction);
        if(newTransaction == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //put
    @PutMapping
    public ResponseEntity<Transaction> updateTransaction(@RequestBody Transaction transaction){
        logger.info("Updating Transaction with id " + transaction.getId());
        Transaction updatedTransaction = transactionService.updateTransaction(transaction);
        if(updatedTransaction == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //delete
    @DeleteMapping("/{transactionId}")
    public ResponseEntity<Transaction> deleteTransactionById(@PathVariable int transactionId){
        logger.info("Deleting Transaction"+transactionId);
        transactionService.deleteTransactionById(transactionId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }







}
