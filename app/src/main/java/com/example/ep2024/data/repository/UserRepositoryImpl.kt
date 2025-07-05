package com.example.ep2024.data.repository

import com.example.ep2024.domain.model.user.User
import com.example.ep2024.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor() : UserRepository {
    override fun getUser(id: String?): User? {
        return null
    }
}
