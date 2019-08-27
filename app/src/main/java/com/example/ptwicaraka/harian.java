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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Random;

public class harian extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "harian";

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    Button kembali2, submitharian;
    EditText edt_nama, edt_alamat, edt_ket, edt_jmlbeli;
    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";
    Integer id = new Random().nextInt();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_harian);
        getUsernameLocal();

        final Spinner spinner1 = findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource
                (harian.this, R.array.kategori, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(harian.this);

        mDisplayDate = (TextView) findViewById(R.id.tvDate1);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        harian.this,
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
                Log.d(TAG, "onDateSet : dd/mm/yyyy: " + dayOfMonth + "-" + month + "-" + year);

                String date = dayOfMonth + "-" + month + "-" + year;
                mDisplayDate.setText(date);
            }
        };

        edt_nama = findViewById(R.id.nama);
        edt_alamat = findViewById(R.id.alamat);
        edt_ket = findViewById(R.id.keterangan);
        edt_jmlbeli = findViewById(R.id.jmlbeli);
        submitharian = findViewById(R.id.submitharian);

        submitharian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String dateselect = mDisplayDate.getText().toString();
                reference = FirebaseDatabase.getInstance().getReference()
                        .child("laporan_harian").child(username_key_new)
                        .child(dateselect).child(id.toString());
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String selected = spinner1.getSelectedItem().toString();
                        dataSnapshot.getRef().child("id_harian").setValue(id.toString());
                        dataSnapshot.getRef().child("tanggal").setValue(dateselect);
                        dataSnapshot.getRef().child("nama_pembeli")
                                .setValue(edt_nama.getText().toString());
                        dataSnapshot.getRef().child("alamat_pembeli")
                                .setValue(edt_alamat.getText().toString());
                        dataSnapshot.getRef().child("kategori").setValue(selected);
                        dataSnapshot.getRef().child("keterangan")
                                .setValue(edt_ket.getText().toString());
                        dataSnapshot.getRef().child("jumlah_pembelian_tabung")
                                .setValue(edt_jmlbeli.getText().toString());
                        dataSnapshot.getRef().child("pangkalan").setValue(username_key_new);

                        Intent intent = new Intent(harian.this, harian.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        kembali2 = findViewById(R.id.kembali2);
        kembali2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(harian.this, home.class);
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


