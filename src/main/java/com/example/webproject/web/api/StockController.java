package com.example.webproject.web.api;

import com.example.webproject.core.yahoo.YahooFinanceStockService;
import com.example.webproject.core.models.Stock;
import com.example.webproject.web.api.models.StockInput;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = "/api/stocks")
public class StockController {
    private final YahooFinanceStockService stockService;

    public StockController(YahooFinanceStockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping("/add")
    public Stock createStock(@RequestBody StockInput stock) {
        return stockService.createStock(stock.stockName, stock.ticker, stock.price);
    }

    @GetMapping(value = "/{ticker}")
    public Stock getStock(@PathVariable String ticker) {
        return stockService.getStockById(ticker);
    }

    @GetMapping(value = "/all")
    public List<Stock> listStocks(
            @RequestParam(defaultValue = "0",required = false) Integer page,
            @RequestParam(defaultValue = "20",required = false) Integer pageSize) throws IOException {

        return stockService.updateStocks();
    }

    @GetMapping(value = "/sort/price")
    public List<Stock> sortByPriceAndList(
            @RequestParam(defaultValue = "0",required = false) Integer page,
            @RequestParam(defaultValue = "20",required = false) Integer pageSize) throws IOException {

        return stockService.updateStocks()
                .stream()
                .sorted(Comparator.comparing(Stock::getPrice))
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/sort/name")
    public List<Stock> sortByNameAndList(
            @RequestParam(defaultValue = "0",required = false) Integer page,
            @RequestParam(defaultValue = "20",required = false) Integer pageSize) throws IOException {

        return stockService.updateStocks()
                .stream()
                .sorted(Comparator.comparing(Stock::getName))
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/filterMax/{maxPrice}")
    public List<Stock> filterUnderPrice(
            @PathVariable BigDecimal maxPrice,
            @RequestParam(defaultValue = "0",required = false) Integer page,
            @RequestParam(defaultValue = "20",required = false) Integer pageSize) throws IOException {

        return stockService.updateStocks()
                .stream()
                .filter(stock -> stock.getPrice().compareTo(maxPrice) < 0)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/filterMin/{minPrice}")
    public List<Stock> filterAbovePrice(
            @PathVariable BigDecimal minPrice,
            @RequestParam(defaultValue = "0",required = false) Integer page,
            @RequestParam(defaultValue = "20",required = false) Integer pageSize) throws IOException {

        return stockService.updateStocks()
                .stream()
                .filter(stock -> stock.getPrice().compareTo(minPrice) > 0)
                .collect(Collectors.toList());
    }

    @DeleteMapping(value = "/delete/{id}")
    public void deleteStock(@PathVariable Integer id) {
        stockService.deleteStock(id);
    }
}
