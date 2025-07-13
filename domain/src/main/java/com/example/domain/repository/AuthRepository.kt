package com.example.domain.repository

import com.example.domain.util.Resource

interface AuthRepository {
    suspend fun register(username: String, email: String, password: String): Resource<Unit>
    suspend fun login(email: String, password: String): Resource<Unit>
}