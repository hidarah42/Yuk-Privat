package com.example.hidarah42.yukprivat.Murid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hidarah42.yukprivat.Data.DetailsModel;
import com.example.hidarah42.yukprivat.Lainnya.Intentpass;
import com.example.hidarah42.yukprivat.R;
import com.firebase.ui.auth.ui.User;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.w3c.dom.Text;

public class Hasilcariguru extends AppCompatActivity {

    //variabel global
    private String UserID,Matpel,Wilayah;
    private DatabaseReference databaserefrencehasilguru;
    private FirebaseListAdapter<DetailsModel>adapterpencarian;
    private ListView HGListtview;
    private TextView HGTextview;
    Query sort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hasilcariguru);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //ambil data
        Intent i = this.getIntent();
        Matpel = i.getExtras().getString("Matpel");
        Wilayah = i.getExtras().getString("Wilayah");

        //inisialisasi
        databaserefrencehasilguru = FirebaseDatabase.getInstance().getReference().child("pencarian").child(Matpel).child(Wilayah);
        HGListtview = (ListView)findViewById(R.id.lvhasilpencarian);
        HGTextview = (TextView)findViewById(R.id.tvjudulhasilcariguru);

        //HGTextview
        HGTextview.setText("Daftar guru "+Matpel+" di "+Wilayah);

        //Query sort
        sort = databaserefrencehasilguru.orderByChild("nama_guru");

        //methodsetuplistener
        setuplistener();

        //HGListview
        HGListtview.setAdapter(adapterpencarian);
    }

    public void setuplistener(){
        //membuat adapterpencarian
        adapterpencarian = new FirebaseListAdapter<DetailsModel>(this, DetailsModel.class, android.R.layout.simple_list_item_1,sort) {
            @Override
            protected void populateView(View v, final DetailsModel model, int position) {
                ((TextView)v.findViewById(android.R.id.text1)).setText(model.getNama_guru());
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new Intentpass(Hasilcariguru.this, model);
                    }
                });
            }
        };
    }
}
