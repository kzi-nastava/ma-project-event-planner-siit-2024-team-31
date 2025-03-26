package com.example.ep2024.di.modules

import android.content.Context
import com.example.ep2024.config.SecureStorage
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SecureStorageModule {

    @Provides
    @Singleton
    fun provideSecureStorage(context: Context?): SecureStorage {
        return SecureStorage(context)
    }

}