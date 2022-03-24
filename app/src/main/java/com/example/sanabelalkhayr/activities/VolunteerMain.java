package com.example.sanabelalkhayr.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.sanabelalkhayr.R;
import com.example.sanabelalkhayr.utils.SharedPrefManager;
import com.google.android.material.navigation.NavigationView;

public class VolunteerMain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, DrawerLayout.DrawerListener{

    public Toolbar toolbar;

    public DrawerLayout drawerLayout;

    public NavController navController;

    public NavigationView navigationView;

    SharedPrefManager prefManager;

    int destination = R.id.menu_my_services;

    AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_volunteer);

        prefManager = SharedPrefManager.getInstance(this);

        setupNavigation();


    }

    private void setupNavigation() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerLayout = findViewById(R.id.my_drawer_layout);

        navigationView = findViewById(R.id.nav_view);

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);


        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.menu_my_services, R.id.menu_service_requests, R.id.menu_charitable_events,R.id.menu_profile, R.id.menu_report_problem).setOpenableLayout(drawerLayout).build();

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);

        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(this);

        drawerLayout.addDrawerListener(this);

    }



    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        menuItem.setChecked(true);

        int id = menuItem.getItemId();

        switch (id) {

            case R.id.menu_my_services:
                destination = R.id.menu_my_services;
                break;

            case R.id.menu_service_requests:
                destination = R.id.menu_service_requests;
                break;

            case R.id.menu_charitable_events:
                destination = R.id.menu_charitable_events;
                break;

            case R.id.menu_profile:
                destination = R.id.menu_profile;
                break;
            case R.id.menu_report_problem:
                destination = R.id.menu_report_problem;
                break;
            case R.id.menu_logout:
                SharedPrefManager.getInstance(this).logout();
                startActivity(new Intent(this, Login.class));
                finish();
                break;
        }

        drawerLayout.closeDrawers();

        return true;

    }

    @Override
    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {}

    @Override
    public void onDrawerOpened(@NonNull View drawerView) {}

    @Override
    public void onDrawerClosed(@NonNull View drawerView) {
        navController.navigate(destination);
    }

    @Override
    public void onDrawerStateChanged(int newState) {
    }
}