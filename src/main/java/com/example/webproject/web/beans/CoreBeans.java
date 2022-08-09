package com.example.webproject.web.beans;

import com.example.webproject.core.yahoo.YahooFinanceStockService;
import com.example.webproject.core.TransactionService;
import com.example.webproject.core.UserService;
import com.example.webproject.repositories.StockRepository;
import com.example.webproject.repositories.TransactionRepository;
import com.example.webproject.repositories.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreBeans {

    @Bean
    public UserService userService(UserRepository repository) {
        return new UserService(repository);
    }

    @Bean
    public YahooFinanceStockService stockService(StockRepository repository) {
        return new YahooFinanceStockService(repository);
    }

    @Bean
    public TransactionService transactionService(TransactionRepository repository) {
        return new TransactionService(repository);
    }
}
