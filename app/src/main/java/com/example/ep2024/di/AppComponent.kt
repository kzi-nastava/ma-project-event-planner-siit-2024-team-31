package com.example.ep2024.di

import com.example.ep2024.MainActivity
import com.example.ep2024.di.modules.AppModule
import com.example.ep2024.di.modules.NetworkModule
import com.example.ep2024.di.modules.RepositoryModule
import com.example.ep2024.di.modules.SecureStorageModule
import com.example.ep2024.presentation.ui.CreateServiceFragment
import com.example.ep2024.presentation.ui.LoginFragment
import com.example.ep2024.presentation.ui.RegisterFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RepositoryModule::class, SecureStorageModule::class, AppModule::class, NetworkModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity?)
    fun inject(createServiceFragment: CreateServiceFragment?)
    fun inject(loginFragment: LoginFragment?)
    fun inject(registerFragment: RegisterFragment?)

}