package com.example.ep2024.domain.repository

import com.example.ep2024.domain.model.user.User

interface UserRepository {
    fun getUser(id: String?): User?
}
