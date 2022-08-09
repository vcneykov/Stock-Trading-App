package com.example.webproject.core.models;

import java.math.BigDecimal;

public class Stock {

    public final Integer id;
    public final String stockName;
    public final String ticker;
    public final BigDecimal price;

    public Stock(Integer id, String stockName, String ticker, BigDecimal price) {
        this.id = id;
        this.stockName = stockName;
        this.ticker = ticker;
        this.price = price;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public String getName() { return this.stockName; }

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
