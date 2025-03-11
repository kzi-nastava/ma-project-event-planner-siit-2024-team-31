package com.example.ep2024.domain.model.user;

import com.example.ep2024.domain.model.Role;

public class User {

    private final int id;
    private final String email;
    private final Role role;

    public User(int id, String email, Role role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }
}
