package com.example.ptwicaraka;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class homeadmin extends AppCompatActivity {

    Button btn_stokadmin, btn_hrnadmin, btn_blnadmin, keluar1;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeadmin);

        btn_stokadmin = findViewById(R.id.btn_stokadmin);
        btn_stokadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(homeadmin.this, stokadmin.class);
                startActivity(i);
                finish();
            }
        });

        btn_hrnadmin = findViewById(R.id.btn_hrnadmin);
        btn_hrnadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(homeadmin.this, harianadmin.class);
                startActivity(i);
                finish();
            }
        });

        btn_blnadmin = findViewById(R.id.btn_blnadmin);
        btn_blnadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(homeadmin.this, bulananadmin.class);
                startActivity(i);
                finish();
            }
        });


        keluar1 = findViewById(R.id.keluar1);
        keluar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //menghapus data kepada local storage (handphone)
                SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY,MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(username_key,null);
                editor.apply();

                Intent intent = new Intent(homeadmin.this, login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                homeadmin.this.finish();
            }

        });
    }

}