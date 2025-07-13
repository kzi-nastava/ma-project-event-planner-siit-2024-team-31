package com.example.data.repository

import com.example.data.network.api.AuthApi
import com.example.data.network.dto.auth.LoginRequestDTO
import com.example.data.network.dto.auth.RegisterRequestDTO
import com.example.data.network.util.parseErrorBody
import com.example.data.storage.SystemUser
import com.example.data.storage.UserStorageService
import com.example.domain.repository.AuthRepository
import com.example.domain.util.Resource
import retrofit2.HttpException
import java.io.IOException

class AuthRepositoryImpl(
    private val api: AuthApi,
    private val userStorage: UserStorageService
) : AuthRepository {

    override suspend fun register(username: String, email: String, password: String): Resource<Unit> {
        return try {
            api.register(
                request = RegisterRequestDTO(email = email, password = password, username = username)
            )
            Resource.Success(Unit)
        } catch (e: IOException) {
            Resource.Error("Couldn't reach server, check your internet connection.")
        } catch (e: HttpException) {
            Resource.Error(e.parseErrorBody())
        }
    }

    override suspend fun login(email: String, password: String): Resource<Unit> {
        return try {
            val response = api.login(
                request = LoginRequestDTO(email = email, password = password)
            )
            val systemUser = SystemUser(
                token = response.token,
                role = response.role
            )
            userStorage.saveUser(systemUser)
            Resource.Success(Unit)
        } catch (e: IOException) {
            Resource.Error("Couldn't reach server, check your internet connection.")
        } catch (e: HttpException) {
            Resource.Error(e.parseErrorBody())
        }
    }

}