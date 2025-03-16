package com.example.ep2024.data.repository;

import com.example.ep2024.data.model.requestDTOs.auth.LoginRequestDTO;
import com.example.ep2024.data.model.responseDTOs.auth.LoginResponseDTO;
import com.example.ep2024.data.network.ApiService;
import com.example.ep2024.domain.repository.AuthRepository;

import javax.inject.Inject;

import retrofit2.Response;

public class AuthRepositoryImpl implements AuthRepository {

    private final ApiService apiService;

    @Inject
    public AuthRepositoryImpl(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO req) throws Exception {
        Response<LoginResponseDTO> response = apiService.login(req).execute();
        if (response.isSuccessful() && response.body() != null) {
            return response.body();
        } else {
            throw new Exception("Auth Error");
        }
    }

}
