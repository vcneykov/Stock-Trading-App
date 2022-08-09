package com.example.webproject.web.api;

import com.example.webproject.core.UserService;
import com.example.webproject.core.models.Transaction;
import com.example.webproject.core.models.User;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/{id}")
    public User getUser(@PathVariable Integer id) {
        return userService.getUser(id);
    }

    @GetMapping("/listAll")
    public List<User> listUsers(
            @RequestParam(defaultValue = "0",required = false) Integer page,
            @RequestParam(defaultValue = "20",required = false) Integer pageSize) {
        return userService.listUsers(page, pageSize);
    }

    @GetMapping("/transactions/{userId}")
    public List<Transaction> listUsersTransactions(
            @PathVariable Integer userId,
            @RequestParam(defaultValue = "0",required = false) Integer page,
            @RequestParam(defaultValue = "20",required = false) Integer pageSize) {
        return userService.listUserTransactions(userId, page, pageSize);
    }

    @DeleteMapping(value = "/delete/{id}")
    public void deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
    }
}
