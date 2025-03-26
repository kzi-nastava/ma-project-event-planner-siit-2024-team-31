package com.example.ep2024.domain.repository

import com.example.ep2024.data.model.requestDTOs.auth.LoginRequestDTO
import com.example.ep2024.data.model.requestDTOs.auth.RegisterRequestDTO
import com.example.ep2024.data.model.responseDTOs.auth.CommonMessageResponseDTO
import com.example.ep2024.data.model.responseDTOs.auth.LoginResponseDTO

interface AuthRepository {

    @Throws(Exception::class)
    fun login(req: LoginRequestDTO?): LoginResponseDTO?

    @Throws(Exception::class)
    fun signup(req: RegisterRequestDTO?): CommonMessageResponseDTO?

}
