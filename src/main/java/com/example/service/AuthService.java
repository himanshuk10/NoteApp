package com.example.service;

import com.example.dto.RegisterRequest;
import com.example.entity.User;
import com.example.repository.UserRepository;
import com.example.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

    public void register(RegisterRequest request) {
        if (request.getEmail() == null || !EMAIL_PATTERN.matcher(request.getEmail()).matches()) {
            throw new IllegalArgumentException("Invalid email format");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("EMAIL_EXISTS");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
    }

    public String login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        return jwtUtil.generateToken(user.getEmail());
    }
}
