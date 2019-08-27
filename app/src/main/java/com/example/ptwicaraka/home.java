package com.example.ptwicaraka;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class home extends AppCompatActivity {
    TextView namapangkalan;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getUsernameLocal();

    Button btn_stok, btn_harian, btn_bulanan, keluar;

        namapangkalan = findViewById(R.id.namapangkalan);
        reference = FirebaseDatabase.getInstance().getReference().child("Users")
                .child(username_key_new);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                namapangkalan.setText("Pangkalan " + dataSnapshot.child("Nama_Pangkalan")
                        .getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_stok = findViewById(R.id.btn_stok);
        btn_stok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(home.this, stok.class);
                startActivity(i);
                finish();
            }
        });

        btn_harian = findViewById(R.id.btn_harian);
        btn_harian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(home.this, harian.class);
                startActivity(i);
                finish();
            }
        });

        btn_bulanan = findViewById(R.id.btn_bulanan);
        btn_bulanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(home.this, bulanan.class);
                startActivity(i);
                finish();
            }
        });

        keluar = findViewById(R.id.keluar);
        keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //menghapus data kepada local storage (handphone)
                SharedPreferences sharedPreferences = getSharedPreferences
                        (USERNAME_KEY,MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(username_key,null);
                editor.apply();

                Intent intent = new Intent(home.this, login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                home.this.finish();
            }


        });
    }
    private void getUsernameLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }

}