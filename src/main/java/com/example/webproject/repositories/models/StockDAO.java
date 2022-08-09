package com.example.webproject.repositories.models;

import java.math.BigDecimal;

public class StockDAO {

    public final Integer id;
    public final String stockName;
    public final String ticker;
    public BigDecimal price;

    public StockDAO(Integer id, String stockName, String ticker, BigDecimal price) {
        this.id = id;
        this.stockName = stockName;
        this.ticker = ticker;
        this.price = price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "id=" + id +
                ", stockName='" + stockName + '\'' +
                ", ticker='" + ticker + '\'' +
                ", price=" + price +
                '}';
    }
}

