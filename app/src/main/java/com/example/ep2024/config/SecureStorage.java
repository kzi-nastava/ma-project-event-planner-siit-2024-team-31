package com.example.ep2024.config;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

public class SecureStorage {
    private static final String PREFS_FILENAME = "secure_prefs";
    private final SharedPreferences sharedPreferences;

    public SecureStorage(Context context) {
        try {
            String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
            sharedPreferences = EncryptedSharedPreferences.create(
                    PREFS_FILENAME,
                    masterKeyAlias,
                    context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (Exception e) {
            throw new RuntimeException("Creating error EncryptedSharedPreferences", e);
        }
    }

    public void saveUser(String userJson) {
        sharedPreferences.edit().putString("user", userJson).apply();
    }

    public String getUser() {
        return sharedPreferences.getString("user", null);
    }
}