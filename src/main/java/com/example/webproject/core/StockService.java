package com.example.webproject.core;

import com.example.webproject.core.models.Stock;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface StockService {

    Stock createStock(String stockName, String ticker, BigDecimal price);

    List<Stock> updateStocks() throws IOException;

    Stock getStockById(String ticker);

    List<Stock> listStocks(int page, int pageSize);

    void deleteStock(int id);
}
