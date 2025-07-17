package com.example.ep2024.ui.viewModels.auth;

data class LoginState (
        val isLoading: Boolean = false,
        val isSuccess: Boolean = false,
        val error: String? = null
)