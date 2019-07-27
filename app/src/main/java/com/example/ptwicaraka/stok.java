package com.example.ptwicaraka;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class stok extends AppCompatActivity {

    private static final String TAG = "Stok";

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    Button kembali1, submitstok;
    EditText edt_jmltabung, edt_sisa, edt_sisaisi, edt_sisaksg, edt_sisabcr;
    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";
    Integer id = new Random().nextInt();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stok);
        getUsernameLocal();
        mDisplayDate = (TextView) findViewById(R.id.tvDate);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        stok.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                Log.d(TAG, "onDateSet : dd/mm/yyyy: " + dayOfMonth + "/" + month + "/" + year);

                String date = dayOfMonth + "/" + month + "/" + year;
                mDisplayDate.setText(date);
            }
        };

        edt_jmltabung = findViewById(R.id.jmltabung);
        edt_sisa = findViewById(R.id.sisa);
        edt_sisaisi = findViewById(R.id.sisaisi);
        edt_sisaksg = findViewById(R.id.sisaksg);
        edt_sisabcr = findViewById(R.id.sisabcr);
        submitstok = findViewById(R.id.submitstok);

        submitstok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference = FirebaseDatabase.getInstance().getReference()
                        .child("stok_tabung").child(username_key_new).child(id.toString());
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        CharSequence selected = mDisplayDate.getText();
                        dataSnapshot.getRef().child("id_stok").setValue(id.toString());
                        dataSnapshot.getRef().child("tanggal").setValue(selected);
                        dataSnapshot.getRef().child("jumlah_tabung").setValue(edt_jmltabung.getText().toString());
                        dataSnapshot.getRef().child("sisa_tabung").setValue(edt_sisa.getText().toString());
                        dataSnapshot.getRef().child("sisa_tabung_isi").setValue(edt_sisaisi.getText().toString());
                        dataSnapshot.getRef().child("sisa_tabung_kosong").setValue(edt_sisaksg.getText().toString());
                        dataSnapshot.getRef().child("sisa_tabung_bocor").setValue(edt_sisabcr.getText().toString());

                        Intent intent = new Intent(stok.this, stok.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });


        kembali1 = findViewById(R.id.kembali1);
        kembali1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(stok.this, home.class);
                startActivity(i);
            }
        });
    }
    private void getUsernameLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}
