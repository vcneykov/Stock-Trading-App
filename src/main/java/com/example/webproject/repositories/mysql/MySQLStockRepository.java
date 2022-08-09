package com.example.webproject.repositories.mysql;

import com.example.webproject.repositories.StockRepository;
import com.example.webproject.repositories.models.StockDAO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

import static com.example.webproject.repositories.mysql.MySQLStockRepository.Queries.*;

public class MySQLStockRepository implements StockRepository {

    private final TransactionTemplate trTemplate;
    private final JdbcTemplate jdbc;

    public MySQLStockRepository(TransactionTemplate trTemplate, JdbcTemplate jdbc) {
        this.trTemplate = trTemplate;
        this.jdbc = jdbc;
    }

    @Override
    public StockDAO createStock(String stockName, String ticker, BigDecimal price) {
        return trTemplate.execute(status -> {
            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbc.update(con -> {
                PreparedStatement ps = con.prepareStatement(INSERT_STOCK, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, stockName);
                ps.setString(2, ticker);
                ps.setBigDecimal(3, price);
                return ps;
            }, keyHolder);

            Integer id = Objects.requireNonNull(keyHolder.getKey()).intValue();
            return new StockDAO(id, stockName, ticker, price);
        });
    }

    @Override
    public StockDAO getStockByTicker(String ticker) {
        return jdbc.queryForObject(GET_STOCK,
                (rs, rowNum) -> fromResultSet(rs), ticker);
    }

    @Override
    public StockDAO updatePrice(BigDecimal price, String ticker) {
        trTemplate.execute(status -> {
            jdbc.update(UPDATE_STOCK_PRICE, price, ticker);
            return null;
        });
        return getStockByTicker(ticker);
    }

    @Override
    public List<StockDAO> listStocks(int page, int pageSize) {
        return jdbc.query(LIST_STOCKS,
                (rs, rowNum) -> fromResultSet(rs), page*pageSize, pageSize);
    }

    private StockDAO fromResultSet(ResultSet rs) throws SQLException {
        return new StockDAO(rs.getInt("stock_id"),
                rs.getString("stock_name"),
                rs.getString("ticker"),
                rs.getBigDecimal("price"));
    }

    @Override
    public void deleteStock(int id) {
        trTemplate.execute(status -> {
            jdbc.update(DELETE_STOCK, id);
            return null;
        });
    }

    static class Queries {
        public static final String INSERT_STOCK = "INSERT INTO stocks" +
                " (stock_name,ticker,price)" +
                " VALUES (?, ?, ?)";

        public static final String GET_STOCK = "SELECT * FROM stocks WHERE ticker = ?";


        public static final String LIST_STOCKS = "SELECT * " +
                "FROM stocks " +
                "LIMIT ?, ?";

        public static final String DELETE_STOCK = "DELETE FROM stocks WHERE stock_id = ?";

        public static final String UPDATE_STOCK_PRICE = "UPDATE " +
                "stocks " +
                "SET price = ? " +
                "WHERE ticker = ?";
    }
}
