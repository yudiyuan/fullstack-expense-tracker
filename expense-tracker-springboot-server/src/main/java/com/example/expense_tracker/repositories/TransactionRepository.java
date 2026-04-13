package com.example.expense_tracker.repositories;

import com.example.expense_tracker.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
}
