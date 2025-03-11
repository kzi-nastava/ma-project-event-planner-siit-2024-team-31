package com.example.ep2024.di;

import android.content.Context;
import com.example.ep2024.config.SecureStorage;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module
public class SecureStorageModule {

    @Provides
    @Singleton
    SecureStorage provideSecureStorage(Context context) {
        return new SecureStorage(context);
    }

}