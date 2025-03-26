package com.example.ep2024.domain.model.service

enum class ConfirmationMethod(private val _str: String) {
    AUTOMATIC("AUTOMATIC"),
    MANUAL("MANUAL");

    override fun toString(): String {
        return _str
    }

    companion object {
        fun fromString(str: String): ConfirmationMethod? {
            for (method in entries) {
                if (method._str == str) {
                    return method
                }
            }
            return null
        }
    }
}