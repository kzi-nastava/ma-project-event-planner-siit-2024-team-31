package com.example.ep2024.ui.viewModels.auth.types

import android.net.Uri
import com.example.domain.entity.user.Role

data class RegisterFormState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val role: Role = Role.USER,
    val companyName: String? = null,
    val phoneNumber: String? = null,
    val country: String? = null,
    val city: String? = null,
    val address: String? = null,
    val zipCode: String? = null,
    val description: String? = null,
    val photos: List<Uri> = emptyList(),
    val isLoading: Boolean = false,
    val registrationSuccess: Boolean = false,
    val error: String? = null
)