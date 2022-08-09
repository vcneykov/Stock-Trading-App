package com.example.webproject.web.api.models;

import java.math.BigDecimal;

public class StockInput {

    public final String stockName;
    public final String ticker;
    public final BigDecimal price;

    public StockInput(String stockName, String ticker, BigDecimal price) {
        this.stockName = stockName;
        this.ticker = ticker;
        this.price = price;
    }
}
