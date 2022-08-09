package com.example.webproject.repositories.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.tomcat.jni.Local;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TransactionDAO {

    public final Integer id;
    public final Integer userId;
    public final Integer stockId;
    public final BigDecimal quantity;
    public final BigDecimal value;

    public final Timestamp transactionDate;

    public TransactionDAO(Integer id, Integer userId, Integer stockId, BigDecimal quantity,
                          BigDecimal value, Timestamp transactionDate) {
        this.id = id;
        this.userId = userId;
        this.stockId = stockId;
        this.quantity = quantity;
        this.value = value;
        this.transactionDate = transactionDate;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", userId=" + userId +
                ", stockId=" + stockId +
                ", quantity=" + quantity +
                ", value=" + value +
                ", transactionDate=" + transactionDate +
                '}';
    }
}
