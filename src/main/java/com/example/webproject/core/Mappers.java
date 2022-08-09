package com.example.webproject.core;

import com.example.webproject.core.models.Stock;
import com.example.webproject.core.models.Transaction;
import com.example.webproject.core.models.User;
import com.example.webproject.repositories.models.StockDAO;
import com.example.webproject.repositories.models.TransactionDAO;
import com.example.webproject.repositories.models.UserDAO;

public class Mappers {
    public static User fromUserDAO(UserDAO user) {
        return new User(user.id, user.username, user.password, user.email, user.cash);
    }

    public static Stock fromStockDAO(StockDAO stock) {
        return new Stock(stock.id, stock.stockName, stock.ticker, stock.price);
    }

    public static Transaction fromTransactionDAO(TransactionDAO transaction) {
        return new Transaction(transaction.id, transaction.userId, transaction.stockId,
                transaction.quantity, transaction.value, transaction.transactionDate);
    }
}
