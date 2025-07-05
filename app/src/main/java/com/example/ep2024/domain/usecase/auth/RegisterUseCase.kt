package com.example.ep2024.domain.usecase.auth

import com.example.ep2024.data.model.requestDTOs.auth.RegisterRequestDTO
import com.example.ep2024.data.model.responseDTOs.auth.CommonMessageResponseDTO
import com.example.ep2024.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val authRepository: AuthRepository) {

}
