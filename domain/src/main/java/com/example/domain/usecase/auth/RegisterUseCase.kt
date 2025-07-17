package com.example.domain.usecase.auth

import com.example.domain.entity.auth.RegisterParams
import com.example.domain.entity.user.Role
import com.example.domain.repository.AuthRepository
import com.example.domain.util.Resource
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(params: RegisterParams): Resource<Unit> {

        val repositoryParams = RegisterParams(
            firstName = params.firstName,
            lastName = params.lastName,
            email = params.email,
            password = params.password,
            role = params.role,
            profileImageUri = params.profileImageUri,
            companyName = params.companyName,
            description = params.description,
            photos = params.photos,
            country = params.country,
            city = params.city,
            phoneNumber = params.phoneNumber,
            address = params.address,
            zipCode = params.zipCode
        )

        return repository.register(params = repositoryParams)
    }
}