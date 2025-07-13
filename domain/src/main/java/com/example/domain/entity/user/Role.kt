package com.example.domain.entity.user;

enum class Role(val string: String) {
    USER("USER"),
    ADMIN("ADMIN"),
    OD("OD"),
    PUP("PUP"),;

    companion object {
        fun fromString(value: String): Role? {
            return Role.entries.find { it.string == value }
        }
    }
}