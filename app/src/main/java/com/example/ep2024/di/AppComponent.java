package com.example.ep2024.di;

import com.example.ep2024.di.modules.AppModule;
import com.example.ep2024.di.modules.NetworkModule;
import com.example.ep2024.di.modules.RepositoryModule;
import com.example.ep2024.di.modules.SecureStorageModule;
import com.example.ep2024.presentation.ui.CreateServiceFragment;
import com.example.ep2024.MainActivity;
import com.example.ep2024.presentation.ui.LoginFragment;

import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component(modules = {RepositoryModule.class, SecureStorageModule.class, AppModule.class, NetworkModule.class})
public interface AppComponent {
    void inject(MainActivity mainActivity);
    void inject(CreateServiceFragment createServiceFragment);
    void inject(LoginFragment loginFragment);
}