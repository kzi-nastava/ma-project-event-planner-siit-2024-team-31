package com.example.ep2024

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.ep2024.config.SecureStorage
import com.example.ep2024.di.AppComponent
import com.example.ep2024.di.DaggerAppComponent
import com.example.ep2024.di.modules.AppModule
import com.example.ep2024.di.modules.NetworkModule
import com.example.ep2024.di.modules.RepositoryModule
import com.example.ep2024.di.modules.SecureStorageModule
import com.example.ep2024.domain.model.Role
import com.example.ep2024.domain.model.user.User
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.google.gson.Gson
import javax.inject.Inject

class MainApp : Application() {

    private lateinit var appComponent: AppComponent;

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .networkModule(NetworkModule())
            .repositoryModule(RepositoryModule())
            .secureStorageModule(SecureStorageModule())
            .appModule(AppModule(this))
            .build()
    }
}

class MainActivity : AppCompatActivity() {

    @Inject
    var secureStorage: SecureStorage? = null

    @Inject
    lateinit var appComponent: AppComponent

    private var navController: NavController? = null
    private var navView: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        navView = findViewById(R.id.bottom_navigation)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment?

        appComponent.inject(this)

        navController = navHostFragment.navController


        updateBottomNavigationMenu()

        navView.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener { item: MenuItem ->
            val itemId = item.itemId

            when (itemId) {
                R.id.navigation_main -> {
                    navController!!.navigate(R.id.mainPageFragment)
                    return@OnItemSelectedListener true
                }
                R.id.navigation_login -> {
                    navController!!.navigate(R.id.loginFragment)
                    return@OnItemSelectedListener true
                }
                R.id.navigation_registration -> {
                    navController!!.navigate(R.id.registrationFragment)
                    return@OnItemSelectedListener true
                }
                R.id.navigation_profile -> {
                    navController!!.navigate(R.id.profileFragment)
                    return@OnItemSelectedListener true
                }
                R.id.navigation_services -> {
                    navController!!.navigate(R.id.myServicesFragment)
                    return@OnItemSelectedListener true
                }
                R.id.navigation_settings -> {
                    TODO()
                }

                else -> {false}
            }

        })

        //for debug
        secureStorage!!.clearUser()
    }

    fun updateBottomNavigationMenu() {
        navView!!.menu.clear()

        val userJson = secureStorage!!.user
        if (userJson == null) {
            navView!!.inflateMenu(R.menu.bottom_nav_menu)
        } else {
            val user = Gson().fromJson(
                userJson,
                User::class.java
            )
            val userRole = user.role
            if (userRole != null) {
                when (userRole) {
                    Role.ADMIN -> navView!!.inflateMenu(R.menu.bottom_nav_menu_admin)
                    Role.USER -> navView!!.inflateMenu(R.menu.bottom_nav_menu_ak)
                    Role.OD -> navView!!.inflateMenu(R.menu.bottom_nav_menu_od)
                    Role.PUP -> navView!!.inflateMenu(R.menu.bottom_nav_menu_pup)
                    else -> navView!!.inflateMenu(R.menu.bottom_nav_menu)
                }
            } else {
                navView!!.inflateMenu(R.menu.bottom_nav_menu)
            }
        }
    }

    fun logoutUser() {
        secureStorage!!.clearUser()
        updateBottomNavigationMenu()
    }
}