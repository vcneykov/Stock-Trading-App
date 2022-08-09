package com.example.webproject.repositories.mysql;

import com.example.webproject.repositories.TransactionRepository;
import com.example.webproject.repositories.models.TransactionDAO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;
import java.util.Objects;

import static com.example.webproject.repositories.mysql.MySQLTransactionRepository.Queries.*;

public class MySQLTransactionRepository implements TransactionRepository {

    private final TransactionTemplate trTemplate;
    private final JdbcTemplate jdbc;

    public MySQLTransactionRepository(TransactionTemplate trTemplate, JdbcTemplate jdbc) {
        this.trTemplate = trTemplate;
        this.jdbc = jdbc;
    }

    @Override
    public TransactionDAO createTransaction(int userId, int stockId, BigDecimal quantity,
                                           BigDecimal value, Timestamp transactionDate) {
        return trTemplate.execute(status -> {
            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbc.update(con -> {
                PreparedStatement ps = con.prepareStatement(INSERT_TRANSACTION, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, userId);
                ps.setInt(2, stockId);
                ps.setBigDecimal(3, quantity);
                ps.setBigDecimal(4, value);
                ps.setTimestamp(5, transactionDate);
                return ps;
            }, keyHolder);

            Integer id = Objects.requireNonNull(keyHolder.getKey()).intValue();
            return new TransactionDAO(id, userId, stockId, quantity, value, transactionDate);
        });
    }

    @Override
    public List<TransactionDAO> getTransactionsFromUser(int id, int page, int pageSize) {
        return jdbc.query(GET_TRANSACTIONS_USER_ID,
                (rs, rowNum) -> fromResultSet(rs), page*pageSize, pageSize);
    }

    @Override
    public List<TransactionDAO> listTransactions(int page, int pageSize) {
        return jdbc.query(LIST_TRANSACTIONS,
                (rs, rowNum) -> fromResultSet(rs), page*pageSize, pageSize);
    }

    private TransactionDAO fromResultSet(ResultSet rs) throws SQLException {
        return new TransactionDAO(
                rs.getInt("transaction_id"),
                rs.getInt("user_id"),
                rs.getInt("stock_id"),
                rs.getBigDecimal("stock_quantity"),
                rs.getBigDecimal("transaction_value"),
                rs.getTimestamp("transaction_date"));
    }

    static class Queries {
        public static final String INSERT_TRANSACTION = "INSERT INTO transactions" +
                " (user_id, stock_id, stock_quantity, transaction_value, transaction_date)" +
                " VALUES (?, ?, ?, ?, ?)";

        public static final String GET_TRANSACTIONS_USER_ID = "SELECT * \n" +
                "FROM \n" +
                "transactions \n" +
                "WHERE user_id = ?";
        public static final String LIST_TRANSACTIONS = "SELECT * FROM transactions LIMIT ?,?";
    }
}
