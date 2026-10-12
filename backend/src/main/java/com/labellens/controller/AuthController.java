package com.labellens.controller;

import com.labellens.model.User;
import com.labellens.repository.UserRepository;
import com.labellens.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> req) {
        String email = req.get("email");
        String password = req.get("password");
        String displayName = req.get("displayName");
        String lang = req.getOrDefault("preferredLanguage", "en");

        if (email == null || email.trim().isEmpty() || password == null || password.length() < 6) {
            return ResponseEntity.badRequest().body(Map.of("error", "Valid email and password (minimum 6 characters) are required."));
        }

        if (userRepository.existsByEmail(email.toLowerCase().trim())) {
            return ResponseEntity.badRequest().body(Map.of("error", "An account with this email address already exists."));
        }

        User u = new User();
        u.setEmail(email.toLowerCase().trim());
        u.setDisplayName(displayName != null && !displayName.trim().isEmpty() ? displayName.trim() : email.split("@")[0]);
        u.setPasswordHash(passwordEncoder.encode(password));
        u.setPreferredLanguage(lang);
        u.setRole("ROLE_USER");

        userRepository.save(u);

        String token = jwtUtil.generateToken(u.getEmail(), u.getRole(), u.getId());
        return ResponseEntity.ok(Map.of(
            "token", token,
            "email", u.getEmail(),
            "displayName", u.getDisplayName(),
            "role", u.getRole(),
            "preferredLanguage", u.getPreferredLanguage()
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> req) {
        String email = req.get("email");
        String password = req.get("password");

        if (email == null || password == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email and password are required."));
        }

        return userRepository.findByEmail(email.toLowerCase().trim())
            .filter(u -> passwordEncoder.matches(password, u.getPasswordHash()))
            .map(u -> {
                String token = jwtUtil.generateToken(u.getEmail(), u.getRole(), u.getId());
                return ResponseEntity.ok(Map.of(
                    "token", token,
                    "email", u.getEmail(),
                    "displayName", u.getDisplayName(),
                    "role", u.getRole(),
                    "preferredLanguage", u.getPreferredLanguage()
                ));
            })
            .orElseGet(() -> ResponseEntity.status(401).body(Map.of("error", "Invalid credentials entered.")));
    }
}
