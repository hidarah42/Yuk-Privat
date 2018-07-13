package com.example.hidarah42.yukprivat.Murid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.hidarah42.yukprivat.Data.Data_murid;
import com.example.hidarah42.yukprivat.Lainnya.CircleTransform;
import com.example.hidarah42.yukprivat.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Profilmurid extends AppCompatActivity {

    //variabel global
    private String UserID,Nama;
    private ImageView Potoprofil;
    private EditText PMNama,PMJK,PMAlamat,PMNoHP;
    private FirebaseDatabase databasenya = FirebaseDatabase.getInstance();
    private DatabaseReference dataaserefrencenya = databasenya.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilmurid);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //inisialisasi
        Potoprofil = (ImageView)findViewById(R.id.ppmurid);
        PMNama = (EditText)findViewById(R.id.edittextnamamurid);
        PMJK = (EditText)findViewById(R.id.edittextjeniskelaminmurid);
        PMAlamat = (EditText)findViewById(R.id.edittextalamatmurid);
        PMNoHP = (EditText)findViewById(R.id.edittextnohmurid);

        //mengambil data UserID
        Intent i = this.getIntent();
        UserID = i.getExtras().getString("UserID");
        Nama = i.getExtras().getString("Nama");

        //edittext tidak bisa diedit
        PMNama.setFocusable(false);
        PMJK.setFocusable(false);
        PMAlamat.setFocusable(false);
        PMNoHP.setFocusable(false);

        //mengambil data
        dataaserefrencenya.child("murid").child(UserID).getRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Data_murid value = dataSnapshot.getValue(Data_murid.class);
                Picasso.with(Profilmurid.this)
                        .load(value.getImage_murid())
                        .transform(new CircleTransform())
                        .into(Potoprofil);
                PMNama.setText(value.getNama_murid());
                PMJK.setText(value.getJk_murid());
                PMAlamat.setText(value.getAlamat_murid());
                PMNoHP.setText(value.getNohp_murid());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //membuat menu toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profil,menu);
        return  true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id==R.id.tab_editguru){
            Intent i = new Intent(Profilmurid.this,Editprofilmurid.class);
            i.putExtra("UserID",UserID);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    //kembali ke form sebelumnya
    @Override
    public void onBackPressed() {
        Intent i = new Intent(Profilmurid.this,Dashboardmurid.class);
        i.putExtra("UserID",UserID);
        i.putExtra("Nama",Nama);
        startActivity(i);
    }

}
