package com.example.ep2024.domain.repository;

import com.example.ep2024.domain.model.user.User;

public interface UserRepository {
    User getUser(String id);

}
