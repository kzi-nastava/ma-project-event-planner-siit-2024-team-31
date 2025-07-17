package com.example.data.network.dto.auth

class LoginResponseDTO(
    val token: String,
    val role: String,
    val message: String? = null
) {
}