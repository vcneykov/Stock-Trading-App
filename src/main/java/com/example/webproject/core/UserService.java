package com.example.webproject.core;

import com.example.webproject.core.models.Transaction;
import com.example.webproject.core.models.User;
import com.example.webproject.execptions.userExceptions.CreateUserException;
import com.example.webproject.execptions.userExceptions.UserNotFoundException;
import com.example.webproject.repositories.UserRepository;
import org.springframework.dao.DataAccessException;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User createUser(String username, String password, String email, BigDecimal cash) {
        try {
            return Mappers.fromUserDAO(repository.createUser(username, password, email, cash));
        } catch (DataAccessException e) {
            throw new CreateUserException();
        }
    }

    public User getUser(int id) {
        try {
            return Mappers.fromUserDAO(repository.getUser(id));
        } catch (DataAccessException e) {
            throw new UserNotFoundException();
        }
    }

    public List<User> listUsers(int page, int pageSize) {
        return repository.listUsers(page, pageSize)
                .stream()
                .map(Mappers::fromUserDAO)
                .collect(Collectors.toList());
    }

    public List<Transaction> listUserTransactions(int userId, int page, int pageSize) {
        return repository.listUsersTransactions(userId, page, pageSize)
                .stream()
                .map(Mappers::fromTransactionDAO)
                .collect(Collectors.toList());
    }

    public void deleteUser(int id) {
        try {
            repository.deleteUser(id);
        } catch (DataAccessException e) {
            throw new UserNotFoundException();
        }
    }
}
