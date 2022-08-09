package com.example.webproject.repositories;

import com.example.webproject.repositories.models.TransactionDAO;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository {

    TransactionDAO createTransaction(int userId, int stockId, BigDecimal quantity,
                                     BigDecimal value, Timestamp transactionDate);
    List<TransactionDAO> getTransactionsFromUser(int id, int page, int pageSize);
    List<TransactionDAO> listTransactions(int page, int pageSize);
}
