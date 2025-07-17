package com.example.data.network.dto.auth

class RegisterRequestDTO(
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val role: String,
    val phoneNumber: String?,
    val country: String?,
    val city: String?,
    val address: String?,
    val zipCode: String?,
    val description: String?
)