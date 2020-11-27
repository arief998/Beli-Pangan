package com.example.belipangan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.belipangan.fragment.HomeFragment;
import com.example.belipangan.fragment.PenjualanFragment;
import com.example.belipangan.fragment.ProductFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivitySeller extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private BottomNavigationView botNavigation;
    private Fragment selectedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_seller);

        mAuth = FirebaseAuth.getInstance();

        botNavigation = findViewById(R.id.botNavMenu);
        botNavigation.setOnNavigationItemSelectedListener(navListener);

        selectedFragment = new ProductFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, selectedFragment).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.menuProduct:
                    selectedFragment = new ProductFragment();
                    break;
                case R.id.menuHome:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.menuSelling:
                    selectedFragment = new PenjualanFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, selectedFragment).commit();

            return true;
        }
    };


}