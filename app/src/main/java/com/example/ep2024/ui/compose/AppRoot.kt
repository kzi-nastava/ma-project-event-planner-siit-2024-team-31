package com.example.ep2024.ui.compose

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ep2024.R
import com.example.ep2024.ui.compose.navigation.Routes
import com.example.ep2024.ui.compose.screens.auth.LoginScreen
import com.example.ep2024.ui.compose.screens.MainScreen
import com.example.ep2024.ui.compose.screens.auth.RegisterScreen
import com.example.ep2024.ui.compose.theme.AppTheme

private data class BottomItem(val route: String, val title: String, val iconRes: Int)

@Composable
fun AppRoot() {
    AppTheme {
        val navController = rememberNavController()
        val bottomItems = listOf(
            BottomItem(Routes.MAIN, "Home", R.drawable.ic_home),
            BottomItem(Routes.LOGIN, "Login", R.drawable.ic_login),
            BottomItem(Routes.REGISTER, "Register", R.drawable.ic_registration)
        )

        val containerColor = MaterialTheme.colorScheme.surface
        val selectedColor = MaterialTheme.colorScheme.primary
        val unselectedColor = MaterialTheme.colorScheme.onSurfaceVariant
        val indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)

        Scaffold(
            bottomBar = {
                val navBackStack by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStack?.destination?.route
                NavigationBar(
                    containerColor = containerColor,
                    tonalElevation = 4.dp
                )
                {
                    bottomItems.forEach { item ->
                        NavigationBarItem(
                            selected = currentRoute == item.route,
                            onClick = {
                                if (currentRoute != item.route) {
                                    navController.navigate(item.route) {
                                        popUpTo(Routes.MAIN)
                                        launchSingleTop = true
                                    }
                                }
                            },
                            icon = { Icon(painterResource(id = item.iconRes), contentDescription = item.title) },
                            label = { Text(item.title) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = selectedColor,
                                unselectedIconColor = unselectedColor,
                                selectedTextColor = selectedColor,
                                unselectedTextColor = unselectedColor,
                                indicatorColor = indicatorColor
                            )
                        )
                    }
                }
            }
        ) { padding ->
            NavHost(
                navController = navController,
                startDestination = Routes.MAIN,
                modifier = androidx.compose.ui.Modifier.padding(padding)
            ) {
                composable(Routes.MAIN) { MainScreen() }
                composable(Routes.LOGIN) { LoginScreen(onSuccess = {
                    navController.navigate(Routes.MAIN) {
                        popUpTo(Routes.MAIN) { inclusive = true }
                        launchSingleTop = true
                    }
                }, onGoToRegister = { navController.navigate(Routes.REGISTER) }) }
                composable(Routes.REGISTER) { RegisterScreen(onRegistrationSuccess = {
                    navController.popBackStack()
                }) }
            }
        }
    }
}

