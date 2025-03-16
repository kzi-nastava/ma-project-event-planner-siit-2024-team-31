package com.example.ep2024;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import com.example.ep2024.config.SecureStorage;
import com.example.ep2024.di.AppComponent;
import com.example.ep2024.di.DaggerAppComponent;
import com.example.ep2024.di.modules.AppModule;
import com.example.ep2024.di.modules.NetworkModule;
import com.example.ep2024.di.modules.RepositoryModule;
import com.example.ep2024.di.modules.SecureStorageModule;
import com.example.ep2024.domain.model.Role;
import com.example.ep2024.domain.model.user.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    @Inject
    SecureStorage secureStorage;
    private NavController navController;
    private BottomNavigationView navView;
    private AppComponent appComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        navView = findViewById(R.id.bottom_navigation);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);

        appComponent = DaggerAppComponent.builder()
                .networkModule(new NetworkModule())
                .repositoryModule(new RepositoryModule())
                .secureStorageModule(new SecureStorageModule())
                .appModule(new AppModule(this))
                        .build();

        appComponent.inject(this);

        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
        }

        updateBottomNavigationMenu();

        navView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.navigation_main) {
                navController.navigate(R.id.mainPageFragment);
                return true;
            } else if (itemId == R.id.navigation_login) {
                navController.navigate(R.id.loginFragment);
                return true;
            } else if (itemId == R.id.navigation_registration) {
                navController.navigate(R.id.registrationFragment);
                return true;
            } else if (itemId == R.id.navigation_profile) {
                navController.navigate(R.id.profileFragment);
                return true;
            } else if (itemId == R.id.navigation_services) {
                navController.navigate(R.id.myServicesFragment);
                return true;
            } else if (itemId == R.id.navigation_settings) {
                // TODO: settings
                // navController.navigate(R.id.settingsFragment);
                return true;
            } else {
                return false;
            }
        });
    }

    public void updateBottomNavigationMenu() {
        navView.getMenu().clear();

        String userJson = secureStorage.getUser();
        if (userJson == null) {
            navView.inflateMenu(R.menu.bottom_nav_menu);
        } else {
            User user = new Gson().fromJson(userJson, User.class);
            Role userRole = user.getRole();
            if (userRole != null) {
                switch (userRole) {
                    case ADMIN:
                        navView.inflateMenu(R.menu.bottom_nav_menu_admin);
                        break;
                    case USER:
                        navView.inflateMenu(R.menu.bottom_nav_menu_ak);
                        break;
                    case OD:
                        navView.inflateMenu(R.menu.bottom_nav_menu_od);
                        break;
                    case PUP:
                        navView.inflateMenu(R.menu.bottom_nav_menu_pup);
                        break;
                    default:
                        navView.inflateMenu(R.menu.bottom_nav_menu);
                        break;
                }
            } else {
                navView.inflateMenu(R.menu.bottom_nav_menu);
            }
        }
    }

    public void logoutUser() {
        secureStorage.clearUser();
        updateBottomNavigationMenu();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

}