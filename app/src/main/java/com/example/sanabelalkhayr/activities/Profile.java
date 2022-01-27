package com.example.sanabelalkhayr.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.sanabelalkhayr.R;
import com.example.sanabelalkhayr.model.User;
import com.example.sanabelalkhayr.utils.SharedPrefManager;

public class Profile extends AppCompatActivity {

    Button mLogoutBtn;

    TextView mUserNameTV;
    TextView mPhoneTV;
    TextView mAddressTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);



        mLogoutBtn = findViewById(R.id.logout);

        mUserNameTV = findViewById(R.id.user_name_text_view);
        mPhoneTV = findViewById(R.id.phone_text_view);
        mAddressTV = findViewById(R.id.address_text_view);

        User user = SharedPrefManager.getInstance(this).getUserData();

        mUserNameTV.setText(user.getUserName());
        mPhoneTV.setText(user.getPhone());
        mAddressTV.setText(user.getAddress());

        mLogoutBtn.setOnClickListener(v -> {
            logOut();
        });


    }

    public void logOut(){
        SharedPrefManager.getInstance(this).logout();
        PackageManager packageManager = this.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(this.getPackageName());
        ComponentName componentName = intent.getComponent();
        Intent mainIntent = Intent.makeRestartActivityTask(componentName);
        startActivity(mainIntent);
        Runtime.getRuntime().exit(0);
    }
}