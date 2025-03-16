package com.example.ep2024.domain.usecase.auth;

import com.example.ep2024.data.model.requestDTOs.auth.LoginRequestDTO;
import com.example.ep2024.data.model.responseDTOs.auth.LoginResponseDTO;
import com.example.ep2024.domain.repository.AuthRepository;

import javax.inject.Inject;

public class LoginUseCase {

    private final AuthRepository authRepository;

    @Inject
    public LoginUseCase(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public LoginResponseDTO execute(LoginRequestDTO req) throws Exception {
        return authRepository.login(req);
    }

}
