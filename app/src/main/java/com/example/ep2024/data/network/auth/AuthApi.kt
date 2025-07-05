package com.example.ep2024.data.network.auth

import com.example.ep2024.data.model.requestDTOs.auth.LoginRequestDTO
import com.example.ep2024.data.model.requestDTOs.auth.RegisterRequestDTO
import com.example.ep2024.data.model.responseDTOs.auth.CommonMessageResponseDTO
import com.example.ep2024.data.model.responseDTOs.auth.LoginResponseDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("api/auth/login")
    fun login(@Body request: LoginRequestDTO): Call<LoginResponseDTO>

    @POST("api/auth/signup")
    fun signup(@Body request: RegisterRequestDTO): Call<CommonMessageResponseDTO>
}
