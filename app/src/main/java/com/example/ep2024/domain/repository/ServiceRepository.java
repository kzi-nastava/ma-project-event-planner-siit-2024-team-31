package com.example.ep2024.domain.repository;

import com.example.ep2024.domain.model.Service;

public interface ServiceRepository {
    Service getService(String id);

}