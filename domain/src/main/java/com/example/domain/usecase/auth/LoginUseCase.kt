package com.example.domain.usecase.auth

import com.example.domain.repository.AuthRepository
import com.example.domain.util.Resource

class LoginUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): Resource<Unit> {
        if (email.isBlank() || password.isBlank()) {
            return Resource.Error("Email and password can't be empty.")
        }
        return repository.login(email, password)
    }
}