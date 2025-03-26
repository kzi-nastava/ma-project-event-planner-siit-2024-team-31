package com.example.ep2024.data.model.responseDTOs.auth

import com.example.ep2024.domain.model.Role

class LoginResponseDTO(val token: String, private val role: String) {
    fun getRole(): Role? {
        return Role.fromString(role)
    }
}
