package com.example.webproject.core.yahoo;

import com.example.webproject.core.Mappers;
import com.example.webproject.core.StockService;
import com.example.webproject.core.models.Stock;
import com.example.webproject.execptions.stockExceptions.CreateStockException;
import com.example.webproject.execptions.stockExceptions.InvalidStockIdException;
import com.example.webproject.repositories.StockRepository;
import org.springframework.dao.DataAccessException;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class YahooFinanceStockService implements StockService {

    private final StockRepository repository;
    private List<String> tickers = new ArrayList<>(Arrays.asList("TSLA", "META" ,"BABA", "INTC", "MSFT", "IBM",
            "NFLX", "GOOG", "AMZN", "V"));

    public YahooFinanceStockService(StockRepository repository) {
        this.repository = repository;
    }

    public Stock createStock(String stockName, String ticker, BigDecimal price) {
        try {
            tickers.add(ticker);
            return Mappers.fromStockDAO(repository.createStock(stockName, ticker, price));
        } catch (DataAccessException e) {
            throw new CreateStockException();
        }
    }

    public List<Stock> updateStocks() throws IOException {
        List<Stock> toReturn = new ArrayList<>();

        for (String ticker : tickers) {
            BigDecimal newPrice = YahooFinance.get(ticker).getQuote().getPrice();
            toReturn.add(Mappers.fromStockDAO(repository.updatePrice(newPrice, ticker)));
        }

        return toReturn;
    }

    public Stock getStockById(String ticker) {
        try {
            return Mappers.fromStockDAO(repository.getStockByTicker(ticker));
        } catch (DataAccessException e) {
            throw new InvalidStockIdException();
        }
    }

    public List<Stock> listStocks(int page, int pageSize) {
        return repository.listStocks(page, pageSize)
                .stream()
                .map(Mappers::fromStockDAO)
                .collect(Collectors.toList());
    }

    public void deleteStock(int id) {
        try {
            repository.deleteStock(id);
        } catch (DataAccessException e) {
            throw new InvalidStockIdException();
        }
    }
}
