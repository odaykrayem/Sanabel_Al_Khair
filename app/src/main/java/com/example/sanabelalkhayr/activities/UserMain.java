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

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.sanabelalkhayr.R;
import com.example.sanabelalkhayr.utils.SharedPrefManager;
import com.google.android.material.navigation.NavigationView;

import java.util.HashSet;
import java.util.Set;


public class UserMain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, DrawerLayout.DrawerListener {

    public Toolbar toolbar;

    public DrawerLayout drawerLayout;

    public NavController navController;

    public NavigationView navigationView;

    SharedPrefManager prefManager;

    int destination = R.id.menu_donations;

    AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);

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


        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.menu_donations, R.id.menu_charitable_events, R.id.menu_my_orders, R.id.menu_profile).setOpenableLayout(drawerLayout).build();

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);

        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(this);

        drawerLayout.addDrawerListener(this);

    }

//    private int getSelectedMenu() {
//
//        int userType = Constants.USER_TYPE_MAIN;
//        int menuId = -1;
//
//        switch (userType){
//            case Constants.USER_TYPE_ADMIN:
//                menuId = R.menu.admin_nav_menu;
//                break;
//            case Constants.USER_TYPE_DONOR:
//                menuId = R.menu.donor_nav_menu;
//                break;
//            case Constants.USER_TYPE_MAIN:
//                menuId = R.menu.user_nav_menu;
//                break;
//            case Constants.USER_TYPE_VOLUNTEER:
//                menuId = R.menu.volunteer_nav_menu;
//                break;
//
//        }
//        return menuId;
//    }


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

            case R.id.menu_donations:
                destination = R.id.menu_donations;
                break;

            case R.id.menu_my_orders:
                destination = R.id.menu_my_orders;
                break;

            case R.id.menu_charitable_events:
                destination = R.id.menu_charitable_events;
                break;

            case R.id.menu_report_problem:
//                navController.navigate(R.id.charitableEventsFragment);
                break;

            case R.id.menu_share:
//                navController.navigate(R.id.ordersFragment);
                break;

        }

        drawerLayout.closeDrawers();

        return true;
    }


    @Override
    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(@NonNull View drawerView) {

    }

    @Override
    public void onDrawerClosed(@NonNull View drawerView) {
        navController.navigate(destination);
    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }
}