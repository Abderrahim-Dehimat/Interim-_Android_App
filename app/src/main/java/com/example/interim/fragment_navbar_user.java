package com.example.interim;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;



public class fragment_navbar_user extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_navbar_user);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        // Pour afficher le premier fragment par défaut
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.bodyApp,
                    new OffersFragment()).commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {
                Fragment selectedFragment = null;

                if (item.getItemId() == R.id.navHome) {
                    selectedFragment = new OffersFragment();
                } else if (item.getItemId() == R.id.navSearch) {
                    selectedFragment = new searchFragment();
                } else if (item.getItemId() == R.id.navFavorite) {
                    selectedFragment = new FavoriteFragment();
                } else if (item.getItemId() == R.id.navProfile) {
                    selectedFragment = new ProfileFragment();
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.bodyApp,
                        selectedFragment).commit();

                return true;
            };
}