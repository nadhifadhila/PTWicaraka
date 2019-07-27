package com.example.ptwicaraka;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class bulanan extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button kembali3;
    Button submitbln;
    DatabaseReference reference;
    Spinner spinner2;
    EditText edt_jmltbg, edt_rt, edt_usahamikro, edt_pengecer;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";
    Integer id = new Random().nextInt();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulanan);
        getUsernameLocal();

        spinner2 = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.bulan, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter);
        spinner2.setOnItemSelectedListener(this);
        edt_jmltbg = findViewById(R.id.jmltbg);
        edt_rt = findViewById(R.id.rt);
        edt_usahamikro = findViewById(R.id.usahamikro);
        edt_pengecer = findViewById(R.id.pengecer);
        submitbln = findViewById(R.id.submitbln);

        submitbln.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference = FirebaseDatabase.getInstance().getReference()
                        .child("laporan_bulanan").child(username_key_new).child(id.toString());
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String selected = spinner2.getSelectedItem().toString();
                        dataSnapshot.getRef().child("id_bulanan").setValue(id.toString());
                        dataSnapshot.getRef().child("bulan").setValue(selected);
                        dataSnapshot.getRef().child("jml_tabung").setValue(edt_jmltbg.getText().toString());
                        dataSnapshot.getRef().child("rumah_tangga").setValue(edt_rt.getText().toString());
                        dataSnapshot.getRef().child("usaha_mikro").setValue(edt_usahamikro.getText().toString());
                        dataSnapshot.getRef().child("pengecer").setValue(edt_pengecer.getText().toString());

                        Intent intent = new Intent(bulanan.this, bulanan.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });


        kembali3 = findViewById(R.id.kembali3);
        kembali3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(bulanan.this, home.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private void getUsernameLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }

}
