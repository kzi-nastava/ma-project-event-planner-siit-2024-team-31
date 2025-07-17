package com.example.ep2024.ui.viewModels.auth.types

data class LoginFormState (
        val isLoading: Boolean = false,
        val isSuccess: Boolean = false,
        val error: String? = null
)