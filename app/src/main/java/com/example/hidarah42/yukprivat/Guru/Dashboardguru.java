package com.example.hidarah42.yukprivat.Guru;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.hidarah42.yukprivat.Lainnya.Splashscreen;
import com.example.hidarah42.yukprivat.R;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class Dashboardguru extends AppCompatActivity {

    //Variabel global
    private String UserID,Nama;
    private BottomBar bottombar_guru;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboardguru);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //inisialisasi
        bottombar_guru = (BottomBar)findViewById(R.id.bottombarguru);

        //mengambil data UserID dan Nama
        Intent i = this.getIntent();
        UserID = i.getExtras().getString("UserID");
        Nama = i.getExtras().getString("Nama");
        Toast.makeText(this, "Selamat datang "+Nama, Toast.LENGTH_SHORT).show();

        //botombar guru
        bottombar_guru.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if(tabId==R.id.tab_notifikasiguru){
                    Intent i = new Intent(Dashboardguru.this,Notifikasiguru.class);
                    i.putExtra("UserID",UserID);
                    i.putExtra("Nama",Nama);
                    startActivity(i);
                }
                else if(tabId==R.id.tab_jadwalguru){
                    Intent i = new Intent(Dashboardguru.this,Jadwalguru.class);
                    i.putExtra("UserID",UserID);
                    i.putExtra("Nama", Nama);
                    startActivity(i);
                }
                else if(tabId==R.id.tab_profilguru){
                    Intent i = new Intent(Dashboardguru.this,Profilguru.class);
                    i.putExtra("UserID",UserID);
                    i.putExtra("Nama", Nama);
                    startActivity(i);
                }
                else if(tabId==R.id.tab_keluarguru){
                    alertdialog();
                }
            }
        });
    }

    //alertdialog
    private void alertdialog(){
        AlertDialog.Builder a_lert = new AlertDialog.Builder(Dashboardguru.this);
        a_lert.setMessage("Apakah anda yakin ingin keluar?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Splashscreen.splashauth.signOut();
                        startActivity(new Intent(Dashboardguru.this, Splashscreen.class));
                        finish();
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        AlertDialog alert = a_lert.create();
        alert.setTitle("Keluar");
        alert.show();
    }

    //backpress
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        System.exit(0);
    }
}
