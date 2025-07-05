package com.example.ep2024.data.repository

import com.example.ep2024.data.model.requestDTOs.auth.LoginRequestDTO
import com.example.ep2024.data.model.requestDTOs.auth.RegisterRequestDTO
import com.example.ep2024.data.model.responseDTOs.auth.CommonMessageResponseDTO
import com.example.ep2024.data.model.responseDTOs.auth.LoginResponseDTO
import com.example.ep2024.data.network.ApiService
import com.example.ep2024.domain.repository.AuthRepository
import retrofit2.Response
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : AuthRepository {

}
