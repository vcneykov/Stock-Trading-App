package com.example.webproject.web.api;

import com.example.webproject.core.TransactionService;
import com.example.webproject.core.models.Transaction;
import com.example.webproject.web.api.models.TransactionInput;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = "/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/create")
    public Transaction createTransaction(@RequestBody TransactionInput transactionInput) {
        return transactionService.createTransaction(transactionInput.userId, transactionInput.stockId,
                transactionInput.quantity, transactionInput.value, transactionInput.transactionDate);
    }

    @GetMapping(value = "/{id}")
    public List<Transaction> getTransactionsFromUser(
            @PathVariable Integer id,
            @RequestParam(defaultValue = "0",required = false) Integer page,
            @RequestParam(defaultValue = "20",required = false) Integer pageSize) {
        return transactionService.getTransactionsFromUser(id, page, pageSize);
    }

    @GetMapping(value = "/sortValue")
    public List<Transaction> sortTransactionsByValue(
            @RequestParam(defaultValue = "0",required = false) Integer page,
            @RequestParam(defaultValue = "20",required = false) Integer pageSize) {
        return transactionService.sortTransactionByValue(page, pageSize);
    }

    @GetMapping(value = "/sortUser")
    public List<Transaction> sortTransactionsByUser(
            @RequestParam(defaultValue = "0",required = false) Integer page,
            @RequestParam(defaultValue = "20",required = false) Integer pageSize) {
        return transactionService.sortTransactionsByUser(page, pageSize);
    }

    @GetMapping(value = "/all")
    public List<Transaction> listTransactions(
            @RequestParam(defaultValue = "0",required = false) Integer page,
            @RequestParam(defaultValue = "20",required = false) Integer pageSize) {

        return transactionService.listTransactions(page, pageSize);
    }
}
