package com.example.ptwicaraka;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {

    Button btn_login, btn_signup;
    EditText uname, pass;

    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //menginisiasi dan memanggil widget button pada file layout
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_signup = findViewById(R.id.btn_signup);
        uname = findViewById(R.id.uname);
        pass = findViewById(R.id.pass);

        btn_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                //Get values from EditText fields
                String username = uname.getText().toString();
                final String password = pass.getText().toString();

                if (uname.getText().toString().equals("") && pass.getText().toString().equals("")) {
                    Toast.makeText(login.this, "Mohon Isi Username & Password",
                            Toast.LENGTH_SHORT).show();
                } else {
                    reference = FirebaseDatabase.getInstance().getReference().child("Users")
                            .child(username);
                    if (username.equals("admin") && password.equals("admin123")) {

                        Toast.makeText(login.this, "Berhasil Masuk Akun!",
                                Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(login.this, homeadmin.class);
                        startActivity(i);
                        finish();

                    } else {
                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {

                                    //ambil data password dari firebase
                                    String passwordFromDatabase = dataSnapshot.child("Password")
                                            .getValue().toString();

                                    //validasi password dengan firebase
                                    if (password.equals(passwordFromDatabase)) {

                                        //simpan username (key) kepada local
                                        SharedPreferences sharedPreferences = getSharedPreferences
                                                (USERNAME_KEY, MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString(username_key, uname.getText().toString());
                                        editor.apply();

                                        //berpindah activity
                                        Intent gotohome = new Intent
                                                (login.this, home.class);
                                        startActivity(gotohome);
                                        finish();


                                    } else {
                                        Toast.makeText(login.this,
                                                "Password yang Anda Masukkan Salah!",
                                                Toast.LENGTH_SHORT).show();
                                    }


                                } else {
                                    Toast.makeText(login.this,
                                            "Username Tidak Ada!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                }


            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup = new Intent(login.this, SignupActivity.class);
                startActivity(signup);

            }
        });
    }
}

