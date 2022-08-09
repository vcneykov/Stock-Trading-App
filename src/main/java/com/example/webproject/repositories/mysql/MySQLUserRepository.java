package com.example.webproject.repositories.mysql;

import com.example.webproject.repositories.UserRepository;
import com.example.webproject.repositories.models.TransactionDAO;
import com.example.webproject.repositories.models.UserDAO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;

import static com.example.webproject.repositories.mysql.MySQLUserRepository.Queries.*;

public class MySQLUserRepository implements UserRepository {

    private final TransactionTemplate trTemplate;
    private final JdbcTemplate jdbc;

    public MySQLUserRepository(TransactionTemplate trTemplate, JdbcTemplate jdbc) {
        this.trTemplate = trTemplate;
        this.jdbc = jdbc;
    }

    @Override
    public UserDAO createUser(String username, String password,
                              String email, BigDecimal cash) {
        return trTemplate.execute(status -> {
            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbc.update(con -> {
                PreparedStatement ps = con.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, username);
                ps.setString(2, password);
                ps.setString(3, email);
                ps.setString(4, cash.toString());
                return ps;
            }, keyHolder);

            Integer id = Objects.requireNonNull(keyHolder.getKey()).intValue();
            return new UserDAO(id, username, password, email, cash);
        });
    }

    @Override
    public UserDAO getUser(int id) {
        return jdbc.queryForObject(GET_USER,
                (rs, rowNum) -> fromResultSet(rs), id);
    }

    @Override
    public UserDAO getUserByUsername(String username) {
        return jdbc.queryForObject(GET_USER_BY_USERNAME,
                (rs, rowNum) -> fromResultSet(rs), username);
    }

    @Override
    public List<UserDAO> listUsers(int page, int pageSize) {
        return jdbc.query(LIST_USERS,
                (rs, rowNum) -> fromResultSet(rs), page*pageSize, pageSize);
    }

    @Override
    public List<TransactionDAO> listUsersTransactions(int user_id, int page, int pageSize) {
        return jdbc.query(LIST_USERS_TRANSACTIONS,
                (rs, rowNum) -> transactionResultSet(rs), user_id, page*pageSize, pageSize);
    }

    @Override
    public void deleteUser(int id) {
        trTemplate.execute(status -> {
            jdbc.update(DELETE_USER, id);
            return null;
        });
    }

    private UserDAO fromResultSet(ResultSet rs) throws SQLException {
        return new UserDAO(
                rs.getInt("user_id"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("email"),
                rs.getBigDecimal("cash"));
    }

    private TransactionDAO transactionResultSet(ResultSet rs) throws SQLException {
        return new TransactionDAO(
                rs.getInt("transaction_id"),
                rs.getInt("user_id"),
                rs.getInt("stock_id"),
                rs.getBigDecimal("stock_quantity"),
                rs.getBigDecimal("sum"),
                rs.getTimestamp("transaction_date"));
    }

    static class Queries {
        public static final String INSERT_USER = "INSERT INTO users" +
                " (username,password,email,cash)" +
                " VALUES (?, ?, ?, ?)";

        public static final String GET_USER = "SELECT * \n" +
                "FROM \n" +
                "users \n" +
                "WHERE user_id = ?";

        public static final String GET_USER_BY_USERNAME = "SELECT * FROM users WHERE username = ?";


        public static final String LIST_USERS = "SELECT * " +
                "FROM users " +
                "LIMIT ?, ?";

        public static final String LIST_USERS_TRANSACTIONS = "SELECT * FROM transactions JOIN users on " +
                "users.user_id = transactions.user_id " +
                "WHERE transactions.user_id = ? " +
                "LIMIT ?, ?";

        public static final String DELETE_USER = "DELETE FROM users WHERE user_id = ?";

        public static final String GET_USER_CASH = "SELECT cash FROM users WHERE user_id = ?";
    }
}
