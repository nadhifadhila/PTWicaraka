package com.example.ptwicaraka;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import com.example.ptwicaraka.adapter.RvHarianAdapter;
import com.example.ptwicaraka.model.ModelHarian;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class harianadmin extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "harianadmin";

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    Button back2, btn_cariharian, btn_editharian, btn_hapusharian;
    DatabaseReference reference, reference2, reference3;

    RecyclerView rvHarian;
    ArrayList<ModelHarian> list;
    RvHarianAdapter rvHarianAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_harianadmin);

        btn_cariharian = findViewById(R.id.btn_cariharian);
        btn_hapusharian = findViewById(R.id.btn_hapusharian);


        rvHarian = findViewById(R.id.rv_modelHarian);
        rvHarian.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();

        final Spinner pangkalanspin = findViewById(R.id.pangkalanspin);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                harianadmin.this, R.array.pangkalan, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pangkalanspin.setAdapter(adapter);
        pangkalanspin.setOnItemSelectedListener(harianadmin.this);

        mDisplayDate = (TextView) findViewById(R.id.tanggal);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        harianadmin.this,
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

        back2 = findViewById(R.id.back2);
        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(harianadmin.this, homeadmin.class);
                startActivity(i);
            }
        });

        btn_cariharian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String pangkalanselect = pangkalanspin.getSelectedItem().toString();
                final String tanggalselected = mDisplayDate.getText().toString();
                ModelHarian modelHarian = new ModelHarian();

                reference = FirebaseDatabase.getInstance().getReference()
                        .child("laporan_harian").child(pangkalanselect).child(tanggalselected);

                if (tanggalselected.equals("")){
                    Toast.makeText(harianadmin.this, "Pilih Tanggal Terlebih Dahulu!",
                            Toast.LENGTH_SHORT).show();
                } else{
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                            if (dataSnapshot.exists()){
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                                    ModelHarian p = dataSnapshot1.getValue(ModelHarian.class);
                                    list.add(p);
                                }
                                rvHarianAdapter = new RvHarianAdapter
                                        (harianadmin.this, list);
                                rvHarian.setAdapter(rvHarianAdapter);
                                rvHarianAdapter.notifyDataSetChanged();
                            }else {

                                Toast.makeText(harianadmin.this, "Data belum tersedia",
                                        Toast.LENGTH_SHORT).show();
                            }

                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }

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
