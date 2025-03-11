package com.example.ep2024

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var navView: BottomNavigationView

    var isLoggedIn: Boolean = false
    var userRole: String = "NK"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        navView = findViewById(R.id.bottom_navigation)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        updateBottomNavigationMenu()

        navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_main -> {
                    navController.navigate(R.id.mainPageFragment)
                    true
                }
                R.id.navigation_login -> {
                    navController.navigate(R.id.loginFragment)
                    true
                }
                R.id.navigation_registration -> {
                    navController.navigate(R.id.registrationFragment)
                    true
                }
                R.id.navigation_profile -> {
                    navController.navigate(R.id.profileFragment)
                    true
                }
                R.id.navigation_services -> {
                    navController.navigate(R.id.myServicesFragment)
                    true
                }
                R.id.navigation_settings -> {
                    //TODO: settings

                    // navController.navigate(R.id.settingsFragment)
                    true
                }
                else -> false
            }
        }
    }

    fun updateBottomNavigationMenu() {
        navView.menu.clear()
        if (isLoggedIn) {
            when (userRole) {
                "AK" -> {
                    navView.inflateMenu(R.menu.bottom_nav_menu_ak)
                }
                "OD" -> {
                    navView.inflateMenu(R.menu.bottom_nav_menu_od)
                }
                "PUP" -> {
                    navView.inflateMenu(R.menu.bottom_nav_menu_pup)
                }
                else -> {
                    navView.inflateMenu(R.menu.bottom_nav_menu)
                }
            }
        } else {
            navView.inflateMenu(R.menu.bottom_nav_menu)
        }
    }

    fun logoutUser() {
        isLoggedIn = false
        userRole = "NK"
        updateBottomNavigationMenu()
    }
}