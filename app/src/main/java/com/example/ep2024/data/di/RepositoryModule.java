package com.example.ep2024.data.di;

import com.example.ep2024.data.repository.UserRepositoryImpl;
import com.example.ep2024.domain.repository.ServiceRepository;
import com.example.ep2024.data.repository.ServiceRepositoryImpl;
import com.example.ep2024.domain.repository.UserRepository;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class RepositoryModule {

    @Provides
    @Singleton
    ServiceRepository provideServiceRepository() {
        return new ServiceRepositoryImpl();
    }

    @Provides
    @Singleton
    UserRepository provideUserRepository() {
        return new UserRepositoryImpl();
    }

}