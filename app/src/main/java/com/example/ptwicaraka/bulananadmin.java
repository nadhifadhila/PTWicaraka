package com.example.ptwicaraka;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class bulananadmin extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button back1, btncari, btn_edit, btn_hapus;
    DatabaseReference reference, reference2, reference3;
    EditText bulan, jmltbg, rmhtangga, usahamikro, pengecer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulananadmin);
        bulan = findViewById(R.id.bulan);
        jmltbg = findViewById(R.id.jmltbgbln);
        rmhtangga = findViewById(R.id.rumah);
        usahamikro = findViewById(R.id.mikro);
        pengecer = findViewById(R.id.ecer);

        final Spinner pangkalanspin = findViewById(R.id.pangkalanspin);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource
                (this, R.array.pangkalan, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pangkalanspin.setAdapter(adapter);
        pangkalanspin.setOnItemSelectedListener(this);

        final Spinner bulanspin = findViewById(R.id.bulanspin);
        ArrayAdapter<CharSequence> adapters = ArrayAdapter.createFromResource
                (this, R.array.bulan, android.R.layout.simple_spinner_item);
        adapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bulanspin.setAdapter(adapters);
        bulanspin.setOnItemSelectedListener(this);
        back1 = findViewById(R.id.back1);
        btncari = findViewById(R.id.cari);
        btn_edit = findViewById(R.id.btn_edit);
        btn_hapus = findViewById(R.id.btn_hapus);
        String pangkalanselect = pangkalanspin.getSelectedItem().toString();
        String bulanselect = bulanspin.getSelectedItem().toString();

        bulan.setEnabled(false);
        jmltbg.setEnabled(false);
        rmhtangga.setEnabled(false);
        usahamikro.setEnabled(false);
        pengecer.setEnabled(false);

        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(bulananadmin.this, homeadmin.class);
                startActivity(i);
            }
        });

        btncari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pangkalanselect = pangkalanspin.getSelectedItem().toString();
                String bulanselect = bulanspin.getSelectedItem().toString();
                bulan.setEnabled(false);
                jmltbg.setEnabled(true);
                rmhtangga.setEnabled(true);
                usahamikro.setEnabled(true);
                pengecer.setEnabled(true);

                reference = FirebaseDatabase.getInstance().getReference()
                        .child("laporan_bulanan").child(pangkalanselect).child(bulanselect);
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            bulan.setText(dataSnapshot.child("bulan").getValue().toString());
                            jmltbg.setText(dataSnapshot.child("jml_tabung").getValue().toString());
                            rmhtangga.setText(dataSnapshot.child("rumah_tangga")
                                    .getValue().toString());
                            usahamikro.setText(dataSnapshot.child("usaha_mikro")
                                    .getValue().toString());
                            pengecer.setText(dataSnapshot.child("pengecer").getValue().toString());

                        } else {
                            Toast.makeText(bulananadmin.this,
                                    "Laporan bulan ini belum di input!", Toast.LENGTH_SHORT)
                                    .show();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });


            }
        });

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pangkalanselect = pangkalanspin.getSelectedItem().toString();
                String bulanselect = bulanspin.getSelectedItem().toString();

                reference2 = FirebaseDatabase.getInstance().getReference()
                        .child("laporan_bulanan").child(pangkalanselect).child(bulanselect);
                reference2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("bulan")
                                .setValue(bulan.getText().toString());
                        dataSnapshot.getRef().child("jml_tabung")
                                .setValue(jmltbg.getText().toString());
                        dataSnapshot.getRef().child("rumah_tangga")
                                .setValue(rmhtangga.getText().toString());
                        dataSnapshot.getRef().child("usaha_mikro")
                                .setValue(usahamikro.getText().toString());
                        dataSnapshot.getRef().child("pengecer")
                                .setValue(pengecer.getText().toString());

                        Toast.makeText(bulananadmin.this, "Data Berhasil di Edit",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent
                                (bulananadmin.this, bulananadmin.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });


        btn_hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pangkalanselect = pangkalanspin.getSelectedItem().toString();
                String bulanselect = bulanspin.getSelectedItem().toString();

                reference3 = FirebaseDatabase.getInstance().getReference("laporan_bulanan")
                        .child(pangkalanselect).child(bulanselect);
                reference3.removeValue();
                Toast.makeText(bulananadmin.this, "Data Berhasil di Hapus",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(bulananadmin.this, bulananadmin.class);
                startActivity(intent);
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

}