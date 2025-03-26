package com.example.ep2024.di.modules

import com.example.ep2024.data.network.ApiService
import com.example.ep2024.data.network.auth.AuthApi
import com.example.ep2024.data.repository.AuthRepositoryImpl
import com.example.ep2024.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideAuthRepository(apiService: ApiService?): AuthRepository {
        return AuthRepositoryImpl(apiService)
    }

    @Singleton
    @Provides
    fun provideAuthApi(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

}