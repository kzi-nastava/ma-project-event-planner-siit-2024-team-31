package com.example.ep2024.data.model;

import com.example.ep2024.domain.model.Role;

public class UserLoginResponse {
    private final String token;
    private final String role;

    public UserLoginResponse(String token, String role) {
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
