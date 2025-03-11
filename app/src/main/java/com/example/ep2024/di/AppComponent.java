package com.example.ep2024.di;

import com.example.ep2024.data.di.RepositoryModule;
import com.example.ep2024.presentation.ui.CreateServiceFragment;
import com.example.ep2024.MainActivity;
import com.example.ep2024.presentation.ui.LoginFragment;

import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component(modules = {RepositoryModule.class, SecureStorageModule.class})
public interface AppComponent {
    void inject(MainActivity mainActivity);
    void inject(CreateServiceFragment createServiceFragment);
    void inject(LoginFragment loginFragment);
}