package com.example.ptwicaraka;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ptwicaraka.model.ModelHarian;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditHarianAct extends AppCompatActivity {

    EditText tglharian, nama, alamat, kategori, ket, jmlbeli;
    DatabaseReference reference, reference2, reference3;
    public static String EXTRA_DATA = "extra_data";
    ModelHarian modelHarian;
    ModelHarian harian;
    String id, pangkalan, tanggal;
    Button btnEdit, btn_hapusharian;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_harian);

        nama = findViewById(R.id.nama);
        alamat = findViewById(R.id.alamat);
        jmlbeli = findViewById(R.id.jmlbeli);
        tglharian = findViewById(R.id.tglharian);
        kategori = findViewById(R.id.kategori);
        ket = findViewById(R.id.ket);
        btnEdit = findViewById(R.id.edit_harian);
        btn_hapusharian = findViewById(R.id.btn_hapusharian);

        Bundle bundle = getIntent().getExtras();
        final String id_harian = bundle.getString("id");
        final String pangkalan_baru = bundle.getString("pangkalan");
        final String tanggal_baru = bundle.getString("tanggal");

        reference = FirebaseDatabase.getInstance().getReference()
                .child("laporan_harian").child(pangkalan_baru)
                .child(tanggal_baru)
                .child(id_harian);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tglharian.setEnabled(false);
                nama.setText(dataSnapshot.child("nama_pembeli").getValue().toString());
                alamat.setText(dataSnapshot.child("alamat_pembeli").getValue().toString());
                jmlbeli.setText(dataSnapshot.child("jumlah_pembelian_tabung")
                        .getValue().toString());
                tglharian.setText(dataSnapshot.child("tanggal").getValue().toString());
                kategori.setText(dataSnapshot.child("kategori").getValue().toString());
                ket.setText(dataSnapshot.child("keterangan").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reference2 = FirebaseDatabase.getInstance().getReference()
                        .child("laporan_harian").child(pangkalan_baru)
                        .child(tanggal_baru)
                        .child(id_harian);
                reference2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("nama_pembeli")
                                .setValue(nama.getText().toString());
                        dataSnapshot.getRef().child("alamat_pembeli")
                                .setValue(alamat.getText().toString());
                        dataSnapshot.getRef().child("jumlah_pembelian_tabung")
                                .setValue(jmlbeli.getText().toString());
                        dataSnapshot.getRef().child("kategori")
                                .setValue(kategori.getText().toString());
                        dataSnapshot.getRef().child("keterangan")
                                .setValue(ket.getText().toString());
                        Toast.makeText(EditHarianAct.this, "Data Berhasil di Edit",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent
                                (EditHarianAct.this, harianadmin.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        btn_hapusharian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reference3 = FirebaseDatabase.getInstance().getReference("laporan_harian")
                        .child(pangkalan_baru).child(tanggal_baru).child(id_harian);
                reference3.removeValue();
                Toast.makeText(EditHarianAct.this, "Data Berhasil di Hapus",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditHarianAct.this, harianadmin.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
