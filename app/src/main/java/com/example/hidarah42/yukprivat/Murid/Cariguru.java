package com.example.hidarah42.yukprivat.Murid;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.hidarah42.yukprivat.R;

public class Cariguru extends AppCompatActivity {

    //variabel global
    String[] arraymatpel,arraywilayah;
    private String UserID,Nama,Matpel,Wilayah;
    private AutoCompleteTextView CGAutocompletematpel,CGAutocompletewilayahnya;
    private Button CGButtoncari;
    private ProgressBar CGProgressbar;
    private ArrayAdapter<String> adaptermatpel,adapterwilahyah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cariguru);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //inisialisasi
        arraymatpel = getResources().getStringArray(R.array.matapelajaran);
        arraywilayah = getResources().getStringArray(R.array.wilayahnya);
        CGAutocompletematpel = (AutoCompleteTextView)findViewById(R.id.autocomplitmatpel);
        CGAutocompletewilayahnya = (AutoCompleteTextView)findViewById(R.id.autocomplitwilayah);
        CGButtoncari = (Button)findViewById(R.id.buttoncariguru);
        CGProgressbar = (ProgressBar)findViewById(R.id.cariguru_progressBar);
        adaptermatpel = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arraymatpel);
        adapterwilahyah = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arraywilayah);

        //CGProgressbar
        CGProgressbar.setVisibility(View.GONE);

        //setlistadapter
        CGAutocompletematpel.setAdapter(adaptermatpel);
        CGAutocompletewilayahnya.setAdapter(adapterwilahyah);

        //mengambil data UserID
        Intent i = this.getIntent();
        UserID = i.getExtras().getString("UserID");
        Nama = i.getExtras().getString("Nama");

        //CGAutocompletematpel
        CGAutocompletematpel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //CGAutocompletewilayah
        CGAutocompletewilayahnya.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //CGButtoncari
        CGButtoncari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String EMatpel = CGAutocompletematpel.getText().toString();
                final String EWilayah = CGAutocompletewilayahnya.getText().toString();
                Matpel = EMatpel;
                Wilayah = EWilayah;

                CGProgressbar.setVisibility(View.VISIBLE);

                //kirim data Matpel dan Wilayah
                Intent i = new Intent(Cariguru.this,Hasilcariguru.class);
                i.putExtra("Matpel",Matpel);
                i.putExtra("Wilayah",Wilayah);
                startActivity(i);

                CGProgressbar.setVisibility(View.GONE);
                CGAutocompletematpel.setText("");
                CGAutocompletewilayahnya.setText("");

            }
        });


    }

    //kembali ke form sebelumnya
    @Override
    public void onBackPressed() {
        Intent i = new Intent(Cariguru.this,Dashboardmurid.class);
        i.putExtra("UserID",UserID);
        i.putExtra("Nama",Nama);
        startActivity(i);
    }
}
