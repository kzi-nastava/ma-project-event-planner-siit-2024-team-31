package com.example.data.storage

import com.example.domain.entity.user.Role

data class SystemUser (
    val token: String,
    val role: Role
)