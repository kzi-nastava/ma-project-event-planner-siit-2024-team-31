package com.example.ep2024

import android.app.Application
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ep2024.di.AppComponent
import com.example.ep2024.di.DaggerAppComponent

class MainApp : Application() {

    val appComponent: AppComponent = DaggerAppComponent.create()

    override fun onCreate() {
        super.onCreate()
    }

}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}