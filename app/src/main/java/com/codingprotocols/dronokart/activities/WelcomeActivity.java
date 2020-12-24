package com.codingprotocols.dronokart.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.codingprotocols.dronokart.R;
import com.google.firebase.auth.FirebaseAuth;

public class WelcomeActivity extends AppCompatActivity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 2000; //splash screen will be shown for 2 seconds

    Intent intent;
    FirebaseAuth mAuth;
    private final String TAG="WelcomeActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mAuth = FirebaseAuth.getInstance();
        new Handler().postDelayed(() -> {
            try{
                if (mAuth.getCurrentUser() != null) {
                    Log.d(TAG, "control send to Home");
                    intent = new Intent(this, HomeActivity.class);
                }
                else{
                    Log.d(TAG, "control send to LoginActivity");
                    intent = new Intent(this, LoginActivity.class);
                }
            }catch (Exception e){
                Log.e(TAG,"Welcome Activity failed to Select");
            }finally {
                startActivity(intent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}