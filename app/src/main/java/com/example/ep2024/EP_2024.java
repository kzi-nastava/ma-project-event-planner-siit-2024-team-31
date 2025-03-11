package com.example.ep2024;

import android.app.Application;
import com.example.ep2024.di.AppComponent;
import com.example.ep2024.di.DaggerAppComponent;

public class EP_2024 extends Application {

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.create();
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }

}