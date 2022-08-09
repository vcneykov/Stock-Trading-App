package com.example.webproject.web.api;

import com.example.webproject.core.UserService;
import com.example.webproject.core.models.User;
import com.example.webproject.repositories.UserRepository;
import com.example.webproject.repositories.models.UserDAO;
import com.example.webproject.security.MyUserDetailsService;
import com.example.webproject.security.models.AuthenticationRequest;
import com.example.webproject.security.util.JwtUtil;
import com.example.webproject.web.api.models.UserInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class LoginControllerTest {
    private LoginController loginController;
    private UserRepository userRepository;

    private AuthenticationManager authenticationManager;

    private static final String mockToken = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJzb2Z0dGVrSldUIiwic3ViIjo" +
            "iUHJlc2xhdiIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJpZCI6MSwidXNlcm5hbWUiOiJQcmVzbGF2Iiwicm9sZV" +
            "9pZCI6MSwiaWF0IjoxNjU1NjI0MTE3LCJleHAiOjE2NTU2ODQxMTd9.IIxndom5Dd5vlm9Qi-ZJ3YPFqdgt8zs0iBve47uf" +
            "-UzWPopzD4irRNl1xzhXFNgYNjX5N3ENZfutpVNmTNlIfw";

    @BeforeEach
    public void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        authenticationManager = Mockito.mock(AuthenticationManager.class);

        loginController = new LoginController(authenticationManager, new MyUserDetailsService(userRepository),
                new JwtUtil(), new UserService(userRepository));
    }

    @Test
    public void createUserTest() {
        UserDAO userDAO = new UserDAO(1, "user", "123", "example@example.com",
                BigDecimal.valueOf(100));

        when(userRepository.createUser(anyString(), anyString(), anyString(), any())).thenReturn(userDAO);
        UserInput userInput = new UserInput("user", "123", "example@example.com",
                BigDecimal.valueOf(100));

        User response = loginController.createUser(userInput);

        assertEquals(1, response.id);
        assertEquals("user", response.username);
        assertEquals("123", response.password);
        assertEquals("example@example.com", response.email);
    }

    @Test
    public void createAuthenticationToken() throws Exception {
        UserDAO userDAO = new UserDAO(1, "root", "abcdef",
                "user@user.com", BigDecimal.valueOf(100));

        when(userRepository.getUserByUsername(anyString())).thenReturn(userDAO);
        when(userRepository.getUser(anyInt())).thenReturn(userDAO);
        UserInput userInput = new UserInput("root", "abcdef", "asd@gmail.com",
                BigDecimal.valueOf(100));

        ResponseEntity<?> response = loginController.createAuthenticationToken(new AuthenticationRequest(
                "root", "abcdef"));

/*        assertEquals("1", response.getHeaders().get);
        assertNotEquals(mockToken, response.token);*/
    }
}
