package com.example.hidarah42.yukprivat.Lainnya;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.hidarah42.yukprivat.Guru.Registerguru;
import com.example.hidarah42.yukprivat.Murid.Registermurid;
import com.example.hidarah42.yukprivat.R;

public class Optionregister extends Activity {

    //variabel global
    private ImageButton guru,murid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.optionregister);

        //inisalisasi
        guru = (ImageButton)findViewById(R.id.guruoption);
        murid = (ImageButton)findViewById(R.id.muridoption);

        //tombol register guru
        guru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             startActivity(new Intent(Optionregister.this, Registerguru.class));
            }
        });

        //tombol register murid
        murid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Optionregister.this,Registermurid.class));
            }
        });
    }


}
