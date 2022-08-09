package com.example.webproject.core;

import com.example.webproject.core.models.Transaction;
import com.example.webproject.execptions.transactionExceptions.CreateTransactionException;
import com.example.webproject.repositories.TransactionRepository;
import org.springframework.dao.DataAccessException;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionService {

    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    public Transaction createTransaction(Integer userId, Integer stockId, BigDecimal quantity,
                                         BigDecimal value, Timestamp transactionDate) {
        try {
            return Mappers.fromTransactionDAO(repository.createTransaction(userId, stockId, quantity, value, transactionDate));
        } catch (DataAccessException e) {
            throw new CreateTransactionException();
        }
    }

    public List<Transaction> getTransactionsFromUser(int id, int page, int pageSize) {
        return repository.listTransactions(page, pageSize)
                .stream()
                .map(Mappers::fromTransactionDAO)
                .filter(tr -> tr.userId == id)
                .collect(Collectors.toList());
    }

    public List<Transaction> sortTransactionByValue(int page, int pageSize) {
        return repository.listTransactions(page, pageSize)
                .stream()
                .map(Mappers::fromTransactionDAO)
                .sorted(Comparator.comparing(Transaction::getSum))
                .collect(Collectors.toList());
    }

    public List<Transaction> sortTransactionsByUser(int page, int pageSize) {
        return repository.listTransactions(page, pageSize)
                .stream()
                .map(Mappers::fromTransactionDAO)
                .sorted(Comparator.comparing(Transaction::getUserId))
                .collect(Collectors.toList());
    }

    public List<Transaction> listTransactions(int page, int pageSize) {
        return repository.listTransactions(page, pageSize)
                .stream()
                .map(Mappers::fromTransactionDAO)
                .collect(Collectors.toList());
    }
}
