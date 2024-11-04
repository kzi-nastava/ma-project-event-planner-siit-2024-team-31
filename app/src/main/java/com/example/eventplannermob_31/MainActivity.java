package com.example.eventplannermob_31;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eventplannermob_31.activities.HomeScreen;
import com.example.eventplannermob_31.activities.LoginScreen;
import com.example.eventplannermob_31.activities.RegisterScreen;
import com.example.eventplannermob_31.activities.SplashScreen;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Button homeScreenButton = findViewById(R.id.homeScreenButton);
        Button registrationScreenButton = findViewById(R.id.RegisterScreenButton);
        Button loginScreenButton = findViewById(R.id.LoginScreenButton);
        Button splashScreenButton = findViewById(R.id.SplashScreenButton);

        homeScreenButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(MainActivity.this, HomeScreen.class);
                startActivity(i);
            }
        });

        registrationScreenButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(MainActivity.this, RegisterScreen.class);
                startActivity(i);
            }
        });

        loginScreenButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(MainActivity.this, LoginScreen.class);
                startActivity(i);
            }
        });

        splashScreenButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(MainActivity.this, SplashScreen.class);
                startActivity(i);
            }
        });


    }

    @Override
    protected void onStart() { super.onStart(); }

    @Override
    protected void onRestart() { super.onRestart(); }

    @Override
    protected void onResume() { super.onResume(); }

    @Override
    protected void onPause() { super.onPause(); }

    @Override
    protected void onStop() { super.onStop(); }

    @Override
    protected void onDestroy() { super.onDestroy(); }

}
