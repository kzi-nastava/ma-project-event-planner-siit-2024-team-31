package com.example.ep2024.domain.model.user;

import com.example.ep2024.domain.model.Role;

public class User {

    private final String token;
    private final Role role;

    public User(String token, Role role) {
        this.token = token;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public Role getRole() {
        return role;
    }

}
