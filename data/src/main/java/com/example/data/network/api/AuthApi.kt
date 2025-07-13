package com.example.data.network.api

import com.example.data.network.dto.auth.LoginRequestDTO
import com.example.data.network.dto.auth.LoginResponseDTO
import com.example.data.network.dto.auth.RegisterRequestDTO
import com.example.data.network.dto.auth.RegisterResponseDTO
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("/api/auth/signup")
    suspend fun register(@Body request: RegisterRequestDTO): RegisterResponseDTO

    @POST("/api/auth/login")
    suspend fun login(@Body request: LoginRequestDTO): LoginResponseDTO
}