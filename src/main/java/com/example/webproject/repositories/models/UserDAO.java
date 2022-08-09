package com.example.webproject.repositories.models;

import java.math.BigDecimal;
import java.util.Map;

public class UserDAO {

    public final Integer id;
    public final String username;
    public final String password;
    public final String email;
    public final BigDecimal cash;

    public UserDAO(Integer id, String username,
                   String password, String email, BigDecimal cash) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.cash = cash;
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
