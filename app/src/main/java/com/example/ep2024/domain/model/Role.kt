package com.example.ep2024.domain.model

enum class Role(private val _str: String) {
    ADMIN("ADMIN"),
    USER("USER"),
    PUP("PUP"),
    OD("OD");

    override fun toString(): String {
        return _str
    }

    companion object {
        fun fromString(str: String): Role? {
            for (role in entries) {
                if (role._str == str) {
                    return role
                }
            }
            return null
        }

        @JvmStatic
        val rolesListWithoutAdminRole: List<Role>
            get() = ArrayList(
                listOf(
                    USER,
                    OD,
                    PUP
                )
            )

        val rolesList: List<Role>
            get() = ArrayList(
                listOf(
                    ADMIN,
                    USER,
                    OD,
                    PUP
                )
            )
    }
}
