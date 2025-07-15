package com.example.data.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_session")

@Singleton
class UserStorageService @Inject constructor(@ApplicationContext private val context: Context) {

    private object PreferencesKeys {
        val USER_TOKEN = stringPreferencesKey("user_token")
        val USER_ROLE = stringPreferencesKey("user_role")
    }

    val userFlow: Flow<SystemUser?> = context.dataStore.data
            .map { preferences ->
            val token = preferences[PreferencesKeys.USER_TOKEN]
        val role = preferences[PreferencesKeys.USER_ROLE]
        if (token != null && role != null) {
            SystemUser(token = token, role = role)
        } else {
            null
        }
    }

    suspend fun saveUser(user: SystemUser) {
        context.dataStore.edit { preferences ->
                preferences[PreferencesKeys.USER_TOKEN] = user.token
            preferences[PreferencesKeys.USER_ROLE] = user.role
        }
    }

    suspend fun clearUser() {
        context.dataStore.edit { it.clear() }
    }
}