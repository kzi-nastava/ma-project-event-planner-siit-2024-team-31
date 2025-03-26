package com.example.ep2024.data.model.requestDTOs.auth

class RegisterRequestDTO(
    var email: String,
    var password: String,
    var role: String,
    var firstName: String,
    var lastName: String,
    var photos: List<String>,
    var phoneNumber: String,
    var country: String,
    var city: String,
    var address: String,
    var zipCode: String,
    var description: String
)
