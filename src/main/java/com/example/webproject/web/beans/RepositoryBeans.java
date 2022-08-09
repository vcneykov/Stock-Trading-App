package com.example.webproject.web.beans;

import com.example.webproject.repositories.StockRepository;
import com.example.webproject.repositories.TransactionRepository;
import com.example.webproject.repositories.UserRepository;
import com.example.webproject.repositories.mysql.MySQLStockRepository;
import com.example.webproject.repositories.mysql.MySQLTransactionRepository;
import com.example.webproject.repositories.mysql.MySQLUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration
public class RepositoryBeans {

    @Bean
    public UserRepository userRepository(TransactionTemplate trTemplate, JdbcTemplate jdbc) {
        return new MySQLUserRepository(trTemplate, jdbc);
    }

    @Bean
    public StockRepository stockRepository(TransactionTemplate trTemplate, JdbcTemplate jdbc) {
        return new MySQLStockRepository(trTemplate, jdbc);
    }

    @Bean
    public TransactionRepository transactionRepository(TransactionTemplate trTemplate, JdbcTemplate jdbc) {
        return new MySQLTransactionRepository(trTemplate, jdbc);
    }
}
