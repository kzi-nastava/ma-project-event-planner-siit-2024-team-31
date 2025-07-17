package com.example.ep2024.ui.viewModels.auth

import android.net.Uri
import com.example.domain.entity.user.Role

data class RegisterFormState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val password: String = "",
    val role: Role = Role.USER,
    val companyName: String? = null,
    val description: String? = null,
    val profileImageUri: Uri? = null,
    val portfolioImageUris: List<Uri> = emptyList(),
    val isLoading: Boolean = false,
    val registrationSuccess: Boolean = false,
    val error: String? = null
)