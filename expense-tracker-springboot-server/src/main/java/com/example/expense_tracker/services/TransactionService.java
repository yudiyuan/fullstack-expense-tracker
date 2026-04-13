package com.example.expense_tracker.services;

import com.example.expense_tracker.entities.Transaction;
import com.example.expense_tracker.entities.TransactionCategory;
import com.example.expense_tracker.entities.User;
import com.example.expense_tracker.repositories.TransactionCategoryRepository;
import com.example.expense_tracker.repositories.TransactionRepository;
import com.example.expense_tracker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service

public class TransactionService {
    private  static final Logger logger=Logger.getLogger(TransactionService.class.getName());

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionCategoryRepository transactionCategoryRepository;

    @Autowired
    private UserRepository userRepository;

    //post
    public Transaction createTransaction(Transaction transaction) {
        logger.info("Creating Transaction");

        //find category
        Optional<TransactionCategory> transactionCategoryOptional=Optional.empty();
        if(transaction.getTransactionCategory()!=null){
            transactionCategoryOptional=transactionCategoryRepository.findById(
                    transaction.getTransactionCategory().getId()
            );
        }

        //find user
        User user=userRepository.findById(transaction.getUser().getId()).get();

        //create the new transaction obj
        Transaction newTransaction=new Transaction();
        newTransaction.setTransactionCategory(
                transactionCategoryOptional.isEmpty()?null: transactionCategoryOptional.get()
        );
        newTransaction.setUser(user);
        newTransaction.setTransactionDate(transaction.getTransactionDate());
        newTransaction.setTransactionType(transaction.getTransactionType());
        newTransaction.setTransactionAmount(transaction.getTransactionAmount());
        newTransaction.setTransactionName(transaction.getTransactionName());

        return transactionRepository.save(newTransaction);

    }




}
