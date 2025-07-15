package com.example.domain.entity.user;

enum class Role(val string: String, val displayedString: String) {
    USER("USER", "Basic User"),
    ADMIN("ADMIN", "Administrator"),
    OD("OD", "Event Organizer"),
    PUP("PUP", "Product/Service Provider"),;

    companion object {
        fun fromString(value: String): Role? {
            return Role.entries.find { it.string == value }
        }
    }

    override fun toString(): String {
        return string
    }

}