package com.example.expense_tracker.repositories;

import com.example.expense_tracker.entities.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findAllByUserIdOrderByTransactionDateDesc(int userId, Pageable pageable);
    List<Transaction> findAllByUserIdAndTransactionDateBetweenOrderByTransactionDateDesc(
                int userId,
                LocalDate startDate,
                LocalDate endDate
            );

}
