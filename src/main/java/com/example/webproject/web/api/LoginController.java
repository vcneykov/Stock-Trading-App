package com.example.webproject.web.api;

import com.example.webproject.core.UserService;
import com.example.webproject.core.models.User;
import com.example.webproject.security.MyUserDetailsService;
import com.example.webproject.security.models.AuthenticationRequest;
import com.example.webproject.security.models.AuthenticationResponse;
import com.example.webproject.security.util.JwtUtil;
import com.example.webproject.web.api.models.UserInput;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class LoginController {

    private final AuthenticationManager authenticationManager;

    private final MyUserDetailsService userDetailsService;

    private final JwtUtil jwtTokenUtil;

    private final UserService userService;

    public LoginController(AuthenticationManager authenticationManager, MyUserDetailsService userDetailsService, JwtUtil jwtTokenUtil, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @PostMapping(value = "/register")
    public User createUser(@RequestBody UserInput user) {
        return userService.createUser(user.username, user.password, user.email, BigDecimal.valueOf(0));
    }
}
