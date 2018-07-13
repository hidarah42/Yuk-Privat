package com.example.hidarah42.yukprivat.Guru;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hidarah42.yukprivat.Data.Data_guru;
import com.example.hidarah42.yukprivat.Data.Modelnotifikasi;
import com.example.hidarah42.yukprivat.Lainnya.Passnotifikasiguru;
import com.example.hidarah42.yukprivat.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Notifikasiguru extends AppCompatActivity {

    //variabel global
    private String UserID,Nama;
    private ListView NGListview;
    private DatabaseReference databaseReference;
    private FirebaseListAdapter<Modelnotifikasi> adapternotifikasi;
    Query sort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifikasiguru);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //mengambil
        Intent i = this.getIntent();
        UserID = i.getExtras().getString("UserID");
        Nama = i.getExtras().getString("Nama");

        //inisialisasi
        NGListview = (ListView)findViewById(R.id.lsnotifikasiguru);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("notifikasi").child(UserID);

        //Query sort
        sort = databaseReference.orderByChild("nama_murid");

        //setuplistener
        setuplistener();

        //NGListview
        NGListview.setAdapter(adapternotifikasi);

    }

    public void setuplistener(){
        //membuat adapternotofikasi

        adapternotifikasi= new FirebaseListAdapter<Modelnotifikasi>(this, Modelnotifikasi.class, R.layout.list_notifikasi,sort) {
            @Override
            protected void populateView(View v, final Modelnotifikasi model, int position) {
                ((TextView)v.findViewById(R.id.notifikasi)).setText(model.getStatus_notifikasi());
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new Passnotifikasiguru(Notifikasiguru.this,model);
                    }
                });
            }
        };
    }

    //kembali ke form sebelumnya
    @Override
    public void onBackPressed() {
        Intent i = new Intent(Notifikasiguru.this,Dashboardguru.class);
        i.putExtra("UserID",UserID);
        i.putExtra("Nama",Nama);
        startActivity(i);
    }
}
