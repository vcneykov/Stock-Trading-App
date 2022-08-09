package com.example.webproject.web.api.models;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class TransactionInput {

    public final Integer userId;
    public final Integer stockId;
    public final BigDecimal quantity;
    public final BigDecimal value;
    public final Timestamp transactionDate;

    public TransactionInput(Integer userId, Integer stockId,
                            BigDecimal quantity, BigDecimal value, Timestamp transactionDate) {
        this.userId = userId;
        this.stockId = stockId;
        this.quantity = quantity;
        this.value = value;
        this.transactionDate = transactionDate;
    }
}
