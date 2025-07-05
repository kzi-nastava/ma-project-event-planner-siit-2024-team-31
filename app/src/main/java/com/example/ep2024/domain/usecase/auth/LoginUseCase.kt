package com.example.ep2024.domain.usecase.auth

import com.example.ep2024.data.model.requestDTOs.auth.LoginRequestDTO
import com.example.ep2024.data.model.responseDTOs.auth.LoginResponseDTO
import com.example.ep2024.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val authRepository: AuthRepository) {

}
