package com.example.ep2024.domain.model.user

import com.example.ep2024.domain.model.Role

class User(val token: String, @JvmField val role: Role)
