package com.example.domain.repository

import com.example.domain.entity.auth.RegisterParams
import com.example.domain.util.Resource

interface AuthRepository {
    suspend fun login(email: String, password: String): Resource<Unit>
    suspend fun register(params: RegisterParams): Resource<Unit>
}