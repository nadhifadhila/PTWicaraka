package com.example.ptwicaraka;

import android.app.DatePickerDialog;
import android.content.Intent;
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

public class stokadmin extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "stokadmin";

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    Button back3, btn_caristok, btn_hapusstok, btn_editstok;
    EditText tglstok, jmltbgstok, sisatbg, sisatbgisi, sisatbgksg, sisatbgbcr;
    DatabaseReference reference, reference2, reference3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stokadmin);
        btn_caristok = findViewById(R.id.btn_caristok);
        btn_editstok = findViewById(R.id.btn_editstok);
        btn_hapusstok = findViewById(R.id.btn_hapusstok);
        tglstok = findViewById(R.id.tglstok);
        jmltbgstok = findViewById(R.id.jmltbgstok);
        sisatbg = findViewById(R.id.sisatbg);
        sisatbgisi = findViewById(R.id.sisatbgisi);
        sisatbgksg = findViewById(R.id.sisatbgksg);
        sisatbgbcr = findViewById(R.id.sisatbgbcr);

        tglstok.setEnabled(false);
        jmltbgstok.setEnabled(false);
        sisatbg.setEnabled(false);
        sisatbgisi.setEnabled(false);
        sisatbgksg.setEnabled(false);
        sisatbgbcr.setEnabled(false);

        final Spinner pangkalanspin = findViewById(R.id.pangkalanspin);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource
                (stokadmin.this, R.array.pangkalan, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pangkalanspin.setAdapter(adapter);
        pangkalanspin.setOnItemSelectedListener(stokadmin.this);

        mDisplayDate = (TextView) findViewById(R.id.tanggal);
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        stokadmin.this,
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

        back3 = findViewById(R.id.back3);
        back3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(stokadmin.this, homeadmin.class);
                startActivity(i);
            }
        });

        btn_caristok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pangkalanselect = pangkalanspin.getSelectedItem().toString();
                final String tanggalselected = mDisplayDate.getText().toString();
                tglstok.setEnabled(false);
                jmltbgstok.setEnabled(true);
                sisatbg.setEnabled(true);
                sisatbgisi.setEnabled(true);
                sisatbgksg.setEnabled(true);
                sisatbgbcr.setEnabled(true);

                reference = FirebaseDatabase.getInstance().getReference()
                        .child("stok_tabung").child(pangkalanselect).child(tanggalselected);
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            tglstok.setText(dataSnapshot.child("tanggal").getValue().toString());
                            jmltbgstok.setText(dataSnapshot.child("jumlah_tabung")
                                    .getValue().toString());
                            sisatbg.setText(dataSnapshot.child("sisa_tabung")
                                    .getValue().toString());
                            sisatbgisi.setText(dataSnapshot.child("sisa_tabung_isi")
                                    .getValue().toString());
                            sisatbgksg.setText(dataSnapshot.child("sisa_tabung_kosong")
                                    .getValue().toString());
                            sisatbgbcr.setText(dataSnapshot.child("sisa_tabung_bocor")
                                    .getValue().toString());

                        } else {
                            Toast.makeText(stokadmin.this,
                                    "Laporan tanggal ini belum di input!", Toast.LENGTH_SHORT)
                                    .show();

                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        btn_editstok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pangkalanselect = pangkalanspin.getSelectedItem().toString();
                final String tanggalselected = mDisplayDate.getText().toString();

                reference2 = FirebaseDatabase.getInstance().getReference()
                        .child("stok_tabung").child(pangkalanselect).child(tanggalselected);
                reference2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("tanggal")
                                .setValue(tglstok.getText().toString());
                        dataSnapshot.getRef().child("jumlah_tabung")
                                .setValue(jmltbgstok.getText().toString());
                        dataSnapshot.getRef().child("sisa_tabung")
                                .setValue(sisatbg.getText().toString());
                        dataSnapshot.getRef().child("sisa_tabung_isi")
                                .setValue(sisatbgisi.getText().toString());
                        dataSnapshot.getRef().child("sisa_tabung_kosong")
                                .setValue(sisatbgksg.getText().toString());
                        dataSnapshot.getRef().child("sisa_tabung_bocor")
                                .setValue(sisatbgbcr.getText().toString());

                        Toast.makeText(stokadmin.this, "Data berhasil di edit",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(stokadmin.this, stokadmin.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        btn_hapusstok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pangkalanselect = pangkalanspin.getSelectedItem().toString();
                final String tanggalselected = mDisplayDate.getText().toString();

                reference3 = FirebaseDatabase.getInstance().getReference("stok_tabung")
                        .child(pangkalanselect).child(tanggalselected);
                reference3.removeValue();
                Toast.makeText(stokadmin.this, "Data berhasil di hapus",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(stokadmin.this, stokadmin.class);
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
