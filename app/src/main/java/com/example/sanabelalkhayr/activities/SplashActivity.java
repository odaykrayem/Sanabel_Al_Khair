package com.example.sanabelalkhayr.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import com.example.sanabelalkhayr.R;
import com.example.sanabelalkhayr.utils.SharedPrefManager;
import com.example.sanabelalkhayr.utils.Constants;

public class SplashActivity extends AppCompatActivity {

    public static final int TIME_TO_START = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler(Looper.getMainLooper()).postDelayed(() -> nextPhase(), TIME_TO_START);
    }

    private void nextPhase() {
        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            switch(SharedPrefManager.getInstance(this).getUserType()){
                case Constants.USER_TYPE_DONOR:
                    startActivity(new Intent(this, DonorMain.class));
                    break;
                case Constants.USER_TYPE_MAIN:
                    startActivity(new Intent(this, UserMain.class));
                    break;
                case Constants.USER_TYPE_VOLUNTEER:
                    startActivity(new Intent(this, VolunteerMain.class));
                    break;
                case Constants.USER_TYPE_ADMIN:
                    startActivity(new Intent(this, AdminMain.class));
                    break;
            }
        }
        finish();
    }
}