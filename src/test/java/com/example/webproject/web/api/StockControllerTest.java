package com.example.webproject.web.api;

import com.example.webproject.core.StockService;
import com.example.webproject.core.UserService;
import com.example.webproject.core.models.Stock;
import com.example.webproject.core.yahoo.YahooFinanceStockService;
import com.example.webproject.repositories.StockRepository;
import com.example.webproject.repositories.UserRepository;
import com.example.webproject.repositories.models.StockDAO;
import com.example.webproject.web.api.models.StockInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StockControllerTest {
    private StockController stockController;
    private StockService stockService;
    private UserService userService;
    private StockRepository stockRepository;
    private UserRepository userRepository;
    private static final String mockFailToken = "eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJzb2Z0dGVrSldUIiwic3ViI" +
            "joiUHJlc2xhdiIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJpZCI6MSwidXNlcm5hbWUiOiJ1c2V" +
            "yIiwicm9sZV9pZCI6MSwiaWF0IjoxNjU1NDcyMjkxLCJleHAiOjE2NTU1MzIyOTF9.PGuBzVg43t6sFFXv" +
            "fU4_blDxVlZOsN2XIog-E5G5vM125lH-kSIse3bjB5bJxWI-7cX_vQpSaUoEAzcUzFhXrw";

    private static final String mockSuccessToken = "eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJzb2Z0dGVrSldUIiw" +
            "ic3ViIjoiUHJlc2xhdiIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJpZCI6MSwidXNlcm5hbWU" +
            "iOiJQcmVzbGF2Iiwicm9sZV9pZCI6MiwiaWF0IjoxNjU1NjI2MjEwLCJleHAiOjE2NTU2ODYyMTB9.v0" +
            "CmSwpHCxJurXjYJTy6VNG7FeXLN4yfvczkZ2Ox-QLZE99U5Je9s5ruzZqYWtf7oY1-9NcfQApfJdggIw7Vhg";
    private final StockDAO template = new StockDAO(1, "tesla", "tsla",
            BigDecimal.valueOf(100));

    @BeforeEach
    public void setUp() {
        stockRepository = Mockito.mock(StockRepository.class);
        userRepository = Mockito.mock(UserRepository.class);
        stockService = new YahooFinanceStockService(stockRepository);
        userService = new UserService(userRepository);
        stockController = new StockController((YahooFinanceStockService) stockService);
    }

    @Test
    public void createMatchTestSuccess () {
        when(stockRepository.createStock(anyString(), anyString(), any())).thenReturn(template);
        StockInput stock = new StockInput("tesla", "tsla", BigDecimal.valueOf(100));

        //ResponseEntity<?> response = stockController.createStock(stock);

        //assertEquals(HttpStatus.OK, response.getStatusCode());
    }

/*    @Test
    public void createStockFail () {
        when(stockRepository.createStock(anyString(), anyString(), any())).thenReturn(template);
        StockInput stock = new StockInput("tesla", "tsla", BigDecimal.valueOf(100));

        StockInput response = stockController.createStock(stock);
        assertEquals(response, stock);
    }*/

    @Test
    public void getMatchTest () {
        when(stockRepository.getStockByTicker(anyString())).thenReturn(template);
        //ResponseEntity<?> response = stockController.getStock("TSLA");
        //assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void listMatchesTest() throws IOException {
        List<StockDAO> matches = new ArrayList<>();
        matches.add(template);
        when(stockRepository.listStocks(anyInt(), anyInt())).thenReturn(matches);

        List<Stock> response = stockController.listStocks(0, 1);

        assertEquals(matches.size(), response.size());
        for (int i = 0; i < matches.size(); i++) {
            assertEquals(matches.get(i).id, response.get(i).id);
            assertEquals((matches.get(i).stockName), response.get(i).stockName);
            assertEquals((matches.get(i).price), response.get(i).price);
        }
    }

    @Test
    public void deleteMatchTest() {
        stockController.deleteStock(1);
        verify(stockRepository, times(1)).deleteStock(anyInt());
    }
}
