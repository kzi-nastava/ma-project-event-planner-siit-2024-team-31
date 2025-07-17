package com.example.ep2024.ui.viewModels.auth.types

sealed class RegisterResult {
    object Success : RegisterResult()
    data class Error(val message: String) : RegisterResult()
}