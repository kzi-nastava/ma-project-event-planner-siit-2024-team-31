package com.example.ep2024.domain.repository

import com.example.ep2024.domain.model.Service

interface ServiceRepository {
    fun getService(id: String?): Service?
}