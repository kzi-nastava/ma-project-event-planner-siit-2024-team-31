package com.example.ep2024

import android.app.Application
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApp : Application() {}

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main_activity)
    }

}