package com.example.webproject.repositories;

import com.example.webproject.repositories.models.StockDAO;

import java.math.BigDecimal;
import java.util.List;

public interface StockRepository {

    StockDAO createStock(String stockName, String ticker, BigDecimal price);
    //StockDAO getStockById(int id);

    StockDAO getStockByTicker(String ticker);

    StockDAO updatePrice(BigDecimal price, String ticker);
    List<StockDAO> listStocks(int page, int pageSize);
    void deleteStock(int id);
}
