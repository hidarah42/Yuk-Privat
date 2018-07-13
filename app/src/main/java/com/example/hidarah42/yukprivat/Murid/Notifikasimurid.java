package com.example.hidarah42.yukprivat.Murid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hidarah42.yukprivat.Data.Modelnotifikasi;
import com.example.hidarah42.yukprivat.Data.Modelnotifikasimurid;
import com.example.hidarah42.yukprivat.Guru.Notifikasiguru;
import com.example.hidarah42.yukprivat.Lainnya.Passnotifikasiguru;
import com.example.hidarah42.yukprivat.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Notifikasimurid extends AppCompatActivity {

    //variabel global
    private String UserID,Nama;
    private ListView NMListview;
    private FirebaseListAdapter<Modelnotifikasimurid>adapternotifikasimurid;
    private DatabaseReference NMDatabaserefrencenotifikasi;
    Query sort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifikasimurid);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //mengambil data UserID
        Intent i = this.getIntent();
        UserID = i.getExtras().getString("UserID");
        Nama = i.getExtras().getString("Nama");

        //Inisialisasi
        NMListview = (ListView)findViewById(R.id.nmlistview);
        NMDatabaserefrencenotifikasi = FirebaseDatabase.getInstance().getReference().child("notifikasi").child(UserID);

        //Query sort
        sort = NMDatabaserefrencenotifikasi.orderByChild("nama_guru");

        //method setuplistener
        setuplistener();

        //NMListview
        NMListview.setAdapter(adapternotifikasimurid);

    }

    private void setuplistener(){
        //membuat adapternotofikasi
        adapternotifikasimurid= new FirebaseListAdapter<Modelnotifikasimurid>(this, Modelnotifikasimurid.class,R.layout.list_notifikasimurid,sort) {
            @Override
            protected void populateView(View v, final Modelnotifikasimurid model, int position) {
                ((TextView)v.findViewById(R.id.notifikasiMURID)).setText(model.getStatus_notifikasi());
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        NMDatabaserefrencenotifikasi.removeValue();
                    }
                });
            }
        };

    }

    //kembali ke form sebelumnya
    @Override
    public void onBackPressed() {
        Intent i = new Intent(Notifikasimurid.this,Dashboardmurid.class);
        i.putExtra("UserID",UserID);
        i.putExtra("Nama",Nama);
        startActivity(i);
    }

}
