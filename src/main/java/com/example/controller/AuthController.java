package com.example.controller;

import com.example.dto.AuthRequest;
import com.example.dto.AuthResponse;
import com.example.dto.RegisterRequest;
import com.example.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            authService.register(request);
            return ResponseEntity.ok(Map.of("message", "User registered successfully"));
        }
        catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
        catch (RuntimeException ex) {
            if ("EMAIL_EXISTS".equals(ex.getMessage())) {
                return ResponseEntity.status(409).body(Map.of("error", "Email already exists"));
            }
            return ResponseEntity.internalServerError().body(Map.of("error", "Something went wrong"));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            String token = authService.login(request.getEmail(), request.getPassword());
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
        }
    }

}
