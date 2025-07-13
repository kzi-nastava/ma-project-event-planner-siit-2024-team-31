package com.example.domain.usecase.auth;

import com.example.domain.repository.AuthRepository
import com.example.domain.util.Resource

class RegisterUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(username: String, email: String, password: String): Resource<Unit> {
        if (username.isBlank() || email.isBlank() || password.isBlank()) {
            return Resource.Error("The fields can't be empty.")
        }
        return repository.register(username, email, password)
    }
}