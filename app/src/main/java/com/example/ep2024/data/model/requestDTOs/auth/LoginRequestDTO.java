package com.example.ep2024.data.model.requestDTOs.auth;

public class LoginRequestDTO {

    private final String email;
    private final String password;

    public LoginRequestDTO(String username, String password) {
        this.email = username;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
