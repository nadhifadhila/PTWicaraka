package com.example.ptwicaraka;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.content.Intent;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;


public class MainActivity extends Activity {
    //set waktu lama sPlashscreen
    //private static int LamaTampilSplash = 1000;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //MULAI KODING

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // KODING

        setContentView(R.layout.activity_main);


        //KODING LAGI

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                // to do auto generated stub
//                Intent apasih = new Intent(MainActivity.this, login.class);
//                startActivity(apasih);
//                finish();
//
//                // jeda setelah splashscren
//
//                this.selesai();
//            }
//
//            private void selesai() {
//                //auto
//            }
//        }, LamaTampilSplash);
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
