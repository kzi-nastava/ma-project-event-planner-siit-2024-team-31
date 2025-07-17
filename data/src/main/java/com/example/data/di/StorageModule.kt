package com.example.data.di

import android.content.Context
import com.example.data.storage.UserStorageService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StorageModule {

    @Provides
    @Singleton
    fun provideUserStorageService(@ApplicationContext context: Context): UserStorageService {
        return UserStorageService(context)
    }

}