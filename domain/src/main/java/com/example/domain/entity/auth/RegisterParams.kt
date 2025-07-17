package com.example.domain.entity.auth

import com.example.domain.entity.user.Role

data class RegisterParams(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val role: Role,
    val profileImageUri: String?,
    val companyName: String?,
    val description: String?,
    val photos: List<String>,
    val country: String?,
    val city: String?,
    val phoneNumber: String?,
    val address: String?,
    val zipCode: String?,
)