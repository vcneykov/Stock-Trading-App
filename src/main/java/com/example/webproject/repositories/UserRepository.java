package com.example.webproject.repositories;

import com.example.webproject.core.models.Transaction;
import com.example.webproject.repositories.models.TransactionDAO;
import com.example.webproject.repositories.models.UserDAO;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface UserRepository {

    UserDAO createUser(String username, String password, String email, BigDecimal cash);
    UserDAO getUser(int id);

    UserDAO getUserByUsername(String username);

    List<UserDAO> listUsers(int page, int pageSize);

    List<TransactionDAO> listUsersTransactions(int userId, int page, int pageSize);
    void deleteUser(int id);
}
