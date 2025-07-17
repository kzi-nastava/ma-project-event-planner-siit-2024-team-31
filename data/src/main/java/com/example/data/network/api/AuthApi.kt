package com.example.data.network.api

import com.example.data.network.dto.auth.LoginRequestDTO
import com.example.data.network.dto.auth.LoginResponseDTO
import com.example.data.network.dto.auth.RegisterRequestDTO
import com.example.data.network.dto.auth.RegisterResponseDTO
import com.example.data.network.util.NoAuth
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface AuthApi {

    @NoAuth
    @Multipart
    @POST("/api/auth/signup")
    suspend fun register(
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
        @Part("role") role: RequestBody,
        @Part("firstName") firstName: RequestBody,
        @Part("lastName") lastName: RequestBody,
        @Part("phoneNumber") phoneNumber: RequestBody,
        @Part("country") country: RequestBody,
        @Part("city") city: RequestBody,
        @Part("address") address: RequestBody,
        @Part("zipCode") zipCode: RequestBody,
        @Part("description") description: RequestBody?,
        @Part photos: List<MultipartBody.Part>
    ): Response<RegisterResponseDTO>

    @NoAuth
    @POST("/api/auth/login")
    suspend fun login(@Body request: LoginRequestDTO): Response<LoginResponseDTO>
}