package com.example.ep2024.data.model.responseDTOs.auth;

import androidx.annotation.NonNull;

import com.example.ep2024.domain.model.Role;

public class LoginResponseDTO {
    private final String token;
    private final String role;

    public LoginResponseDTO(String token, String role) {
        this.token = token;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public Role getRole() {
        return Role.fromString(role);
    }

}
