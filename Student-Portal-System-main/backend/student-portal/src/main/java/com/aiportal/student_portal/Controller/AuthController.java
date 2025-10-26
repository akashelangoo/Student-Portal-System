package com.aiportal.student_portal.Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.aiportal.student_portal.Repositories.UserRepository;
import com.aiportal.student_portal.Models.User;
import com.aiportal.student_portal.Models.Role;
import com.aiportal.student_portal.Security.JwtUtils;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public Map<String, String> register(@RequestBody Map<String, String> body) {
        try {
            String name = body.get("name");
            String email = body.get("email");
            String password = body.get("password");
            String roleStr = body.getOrDefault("role", "STUDENT");

            // Validate required fields
            if (name == null || name.trim().isEmpty()) {
                return Map.of("error", "Name is required");
            }
            if (email == null || email.trim().isEmpty()) {
                return Map.of("error", "Email is required");
            }
            if (password == null || password.trim().isEmpty()) {
                return Map.of("error", "Password is required");
            }

            if (userRepository.existsByEmail(email)) {
                return Map.of("error", "Email already exists");
            }

           
            Role role;
            try {
                role = Role.valueOf(roleStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                return Map.of("error", "Invalid role. Available roles: STUDENT, TEACHER, ADMIN");
            }

       
            User user = new User();
            user.setName(name.trim());
            user.setEmail(email.trim().toLowerCase());
            user.setPassword(passwordEncoder.encode(password));
            user.setRole(role);

            userRepository.save(user);

            String token = jwtUtils.generateJwtToken(email);
            Map<String, String> response = Map.of(
                    "token", token,
                    "message", "User registered successfully",
                    "role", role.name()
            );
            return response;

        } catch (Exception e) {
            Map<String, String> errorResponse = Map.of("error", "Registration failed: " + e.getMessage());
            return errorResponse;
        }
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> body) {
        try {
            String email = body.get("email");
            String password = body.get("password");

            if (email == null || email.trim().isEmpty()) {
                return Map.of("error", "Email is required");
            }
            if (password == null || password.trim().isEmpty()) {
                return Map.of("error", "Password is required");
            }

            Optional<User> userOpt = userRepository.findByEmail(email.trim().toLowerCase());
            if (userOpt.isEmpty()) {
                return Map.of("error", "Invalid email or password");
            }

            User user = userOpt.get();
            if (!passwordEncoder.matches(password, user.getPassword())) {
                return Map.of("error", "Invalid email or password");
            }

            String token = jwtUtils.generateJwtToken(user.getEmail());
            Map<String, String> response = Map.of(
                    "token", token,
                    "role", user.getRole().name(),
                    "message", "Login successful",
                    "name", user.getName()
            );
            return response;

        } catch (Exception e) {
            Map<String, String> errorResponse = Map.of("error", "Login failed: " + e.getMessage());
            return errorResponse;
        }
    }
}
