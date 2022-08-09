package com.example.webproject.core.models;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Transaction {
    public final Integer id;
    public final Integer userId;
    public final Integer stockId;
    public final BigDecimal quantity;
    public final BigDecimal sum;

    public final Timestamp transactionDate;

    public Transaction(Integer id, Integer userId, Integer stockId,
                       BigDecimal quantity, BigDecimal sum, Timestamp transactionDate) {
        this.id = id;
        this.userId = userId;
        this.stockId = stockId;
        this.quantity = quantity;
        this.sum = sum;
        this.transactionDate = transactionDate;
    }

    public BigDecimal getSum() { return this.sum; }

    public Integer getUserId() { return this.userId; }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", userId=" + userId +
                ", stockId=" + stockId +
                ", quantity=" + quantity +
                ", sum=" + sum +
                ", transactionDate=" + transactionDate +
                '}';
    }
}
