package com.aiportal.student_portal.Security;

import org.springframework.stereotype.Component;

@Component
public class JwtUtils {

    public String generateJwtToken(String email) {
        // Mock token generation for now
        return "mock-jwt-token-for-" + email + "-" + System.currentTimeMillis();
    }

    public String getEmailFromJwtToken(String token) {
        // Extract email from mock token
        if (token.startsWith("mock-jwt-token-for-")) {
            return token.replace("mock-jwt-token-for-", "").split("-")[0];
        }
        return null;
    }

    public boolean validateJwtToken(String token) {
        // Always return true for mock tokens
        return token != null && !token.isEmpty();
    }
}
