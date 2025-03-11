package com.example.ep2024.domain.model;

import androidx.annotation.NonNull;

public enum Role {
    ADMIN("ADMIN"),
    USER("USER"),
    PUP("PUP"),
    OD("OD");

    private final String _str;

    Role(String str) {
        _str = str;
    }

    @NonNull
    @Override
    public String toString() {
        return _str;
    }

    public static Role fromString(String str){
        for (Role role : Role.values()) {
            if (role._str.equals(str)) {
                return role;
            }
        }
        return null;
    }

}
