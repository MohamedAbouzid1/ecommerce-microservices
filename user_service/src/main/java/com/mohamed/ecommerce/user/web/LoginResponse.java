package com.mohamed.ecommerce.user.web;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.Instant;
import java.util.UUID;

public class LoginResponse {
    private String accessToken;
    private String tokenType= "Bearer";
    private Instant expiresAt;

    private UUID userId;
    private String email;

    public LoginResponse(String accessToken, Instant expiresAt, UUID userId, String email) {
        this.accessToken = accessToken;
        this.expiresAt = expiresAt;
        this.userId = userId;
        this.email = email;

    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }
}
