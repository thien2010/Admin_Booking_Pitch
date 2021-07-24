package com.example.booking_pitch.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.example.booking_pitch.R;
import com.example.booking_pitch.databinding.ActivityAdminBinding;
import com.example.booking_pitch.databinding.ActivityHomeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminActivity extends AppCompatActivity {

    private ActivityAdminBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navViewAdmin = findViewById(R.id.nav_view_admin);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home_admin, R.id.navigation_quanLyLich, R.id.navigation_thongKe,R.id.navigation_acount)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_home_admin);
        NavigationUI.setupWithNavController(binding.navViewAdmin, navController);
    }
}