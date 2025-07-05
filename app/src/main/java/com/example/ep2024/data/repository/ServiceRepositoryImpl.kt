package com.example.ep2024.data.repository

import com.example.ep2024.domain.model.Service
import com.example.ep2024.domain.repository.ServiceRepository
import javax.inject.Inject

class ServiceRepositoryImpl @Inject constructor() : ServiceRepository {
    override fun getService(id: String?): Service? = null
}
