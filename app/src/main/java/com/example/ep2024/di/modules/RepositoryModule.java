package com.example.ep2024.di.modules;

import com.example.ep2024.data.network.ApiService;
import com.example.ep2024.data.network.auth.AuthApi;
import com.example.ep2024.data.repository.AuthRepositoryImpl;
import com.example.ep2024.domain.repository.AuthRepository;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

import javax.inject.Singleton;

@Module
public class RepositoryModule {

    @Singleton
    @Provides
    public AuthRepository provideAuthRepository(ApiService apiService) {
        return new AuthRepositoryImpl(apiService);
    }

    @Singleton
    @Provides
    public AuthApi provideAuthApi(Retrofit retrofit) {
        return retrofit.create(AuthApi.class);
    }

}