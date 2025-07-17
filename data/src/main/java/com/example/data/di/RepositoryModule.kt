package com.example.data.di

import android.content.Context
import com.example.data.network.api.AuthApi
import com.example.data.repository.AuthRepositoryImpl
import com.example.data.storage.UserStorageService
import com.example.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        api: AuthApi,
        userStorage: UserStorageService,
        @ApplicationContext context: Context
    ): AuthRepository {
        return AuthRepositoryImpl(api, userStorage, context)
    }

}