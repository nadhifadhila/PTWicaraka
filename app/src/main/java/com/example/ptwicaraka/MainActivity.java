package com.example.ptwicaraka;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.content.Intent;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;


public class MainActivity extends Activity {

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        getUsernameLocal();

    }

    private void getUsernameLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY,MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key,"");

        if (username_key_new.isEmpty()){
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent goGetStarted = new Intent(MainActivity.this, login.class);
                    startActivity(goGetStarted);
                    finish();
                }
            },2000);
        }else{
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent goGetStarted = new Intent(MainActivity.this, home.class);
                    startActivity(goGetStarted);
                    finish();
                }
            },2000);
        }

    }
}
