package com.example.webproject.web.api.models;

import java.math.BigDecimal;

public class UserInput {

    public final String username;

    public String password;
    public final String email;
    public final BigDecimal cash;


    public UserInput(String username, String password, String email, BigDecimal cash) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.cash = cash;
    }

    public void setPassword(String encodedPassword) {
        this.password = encodedPassword;
    }
}
