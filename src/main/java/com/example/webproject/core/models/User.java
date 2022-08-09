package com.example.webproject.core.models;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class User {

    public final Integer id;
    public final String username;
    public final String password;
    public final String email;
    public final BigDecimal cash;

    public User(Integer id, String username, String password,
                String email, BigDecimal cash) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.cash = cash;
    }

    public BigDecimal getCash() {
        return this.cash;
    }

    @Override
    public String toString() {
        return "UserDAO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", cash=" + cash +
                '}';
    }
}
