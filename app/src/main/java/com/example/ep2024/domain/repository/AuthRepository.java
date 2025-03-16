package com.example.ep2024.domain.repository;

import com.example.ep2024.data.model.requestDTOs.auth.LoginRequestDTO;
import com.example.ep2024.data.model.responseDTOs.auth.LoginResponseDTO;

public interface AuthRepository {

    LoginResponseDTO login(LoginRequestDTO req) throws Exception;

}
