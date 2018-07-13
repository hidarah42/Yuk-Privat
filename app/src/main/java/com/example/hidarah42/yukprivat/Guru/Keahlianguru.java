package com.example.hidarah42.yukprivat.Guru;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.example.hidarah42.yukprivat.Data.Data_guru;
import com.example.hidarah42.yukprivat.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Keahlianguru extends AppCompatActivity {

    //variabel global
    private String UserID;
    private EditText KGKehalian1,KGWilayah1,KGHarga1,KGKehalian2,KGWilayah2,KGHarga2,KGKehalian3,KGWilayah3,KGHarga3;
    private FirebaseDatabase databasenya = FirebaseDatabase.getInstance();
    private DatabaseReference databaserefrencenya = databasenya.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.keahlian);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //inisialisasi
        KGKehalian1 = (EditText)findViewById(R.id.edittextk1);
        KGWilayah1 = (EditText)findViewById(R.id.edittextw1);
        KGHarga1=(EditText)findViewById(R.id.edittexth1);
        KGKehalian2 = (EditText)findViewById(R.id.edittextk2);
        KGWilayah2 = (EditText)findViewById(R.id.edittextw2);
        KGHarga2=(EditText)findViewById(R.id.edittexth2);
        KGKehalian3 = (EditText)findViewById(R.id.edittextk3);
        KGWilayah3 = (EditText)findViewById(R.id.edittextw3);
        KGHarga3 =(EditText)findViewById(R.id.edittexth3);

        //membuat edittext tidak bisa diedit
        KGKehalian1.setFocusable(false);
        KGWilayah1.setFocusable(false);
        KGHarga1.setFocusable(false);
        KGKehalian2.setFocusable(false);
        KGWilayah2.setFocusable(false);
        KGHarga2.setFocusable(false);
        KGKehalian3.setFocusable(false);
        KGWilayah3.setFocusable(false);
        KGHarga3.setFocusable(false);

        //mengambil data UserID
        Intent i = this.getIntent();
        UserID = i.getExtras().getString("UserID");

        //mengambil databasenya
        databaserefrencenya.child("guru").child(UserID).getRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Data_guru lol = dataSnapshot.getValue(Data_guru.class);
                KGKehalian1.setText(lol.getKeahlian_guru1());
                KGWilayah1.setText(lol.getWilayah_guru1());
                KGHarga1.setText(lol.getHarga_guru1());
                KGKehalian2.setText(lol.getKeahlian_guru2());
                KGWilayah2.setText(lol.getWilayah_guru2());
                KGHarga2.setText(lol.getHarga_guru2());
                KGKehalian3.setText(lol.getKeahlian_guru3());
                KGWilayah3.setText(lol.getWilayah_guru3());
                KGHarga3.setText(lol.getHarga_guru3());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
