package com.example.hidarah42.yukprivat.Murid;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.hidarah42.yukprivat.Data.Data_guru;
import com.example.hidarah42.yukprivat.Lainnya.CircleTransform;
import com.example.hidarah42.yukprivat.Lainnya.Splashscreen;
import com.example.hidarah42.yukprivat.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.net.URI;

public class Profilhasilguru extends AppCompatActivity {

    //variabel global
    private String Nama,UserIDGuru,Keahlianguru,Hargaguru,UserID,Matpel,Wilayah,Nohp,test;
    private EditText PHGNAma,PHGJeniskelamin,PHGPendidikan,PHGMatpel,PHGHarga,PHGDeskripsi,PHGNohp;
    private ImageView PHGPotoprofil;
    private ImageButton PHGTelpon;
    private Button PHGPesan;
    private DatabaseReference PHGDatabaserefrence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilhasilguru);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //ambil data
        Bundle bundle= getIntent().getExtras();
        Nama = bundle.getString("Nama");
        UserIDGuru = bundle.getString("UID");
        Keahlianguru = bundle.getString("Keahlian");
        Hargaguru = bundle.getString("Harga");
        Nohp = bundle.getString("NOHP");
        test = Splashscreen.splashauth.getCurrentUser().getUid();


        //inisialisasi
        PHGPotoprofil = (ImageView)findViewById(R.id.phgpotoprofil);
        PHGNAma = (EditText)findViewById(R.id.phgedittextnama);
        PHGJeniskelamin = (EditText)findViewById(R.id.phgedittextjeniskelamin);
        PHGPendidikan = (EditText)findViewById(R.id.phgetpendidikan);
        PHGMatpel = (EditText)findViewById(R.id.phgedittextkeahlian);
        PHGHarga = (EditText)findViewById(R.id.phgedittextharga);
        PHGNohp = (EditText)findViewById(R.id.phgetnohp);
        PHGTelpon = (ImageButton)findViewById(R.id.phgimgtelpon);
        PHGDeskripsi = (EditText)findViewById(R.id.phgedittextdeskripsi);
        PHGPesan = (Button)findViewById(R.id.phgpesan);
        PHGDatabaserefrence = FirebaseDatabase.getInstance().getReference().child("guru").child(UserIDGuru);

        //Edittext tidak bisa di edit
        PHGNAma.setFocusable(false);
        PHGJeniskelamin.setFocusable(false);
        PHGMatpel.setFocusable(false);
        PHGHarga.setFocusable(false);
        PHGDeskripsi.setFocusable(false);
        PHGNohp.setFocusable(false);
        PHGPendidikan.setFocusable(false);

        //PHGTelpon
        PHGTelpon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent panggil = new Intent(Intent.ACTION_DIAL);
                panggil.setData(Uri.parse("tel:"+Nohp));
                startActivity(panggil);
            }
        });

        //PHGDatabaserefrence
        PHGDatabaserefrence.getRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Data_guru DG = dataSnapshot.getValue(Data_guru.class);
                Picasso.with(Profilhasilguru.this)
                        .load(DG.getImage())
                        .transform(new CircleTransform())
                        .into(PHGPotoprofil);
                PHGNAma.setText(DG.getNama_guru());
                PHGJeniskelamin.setText(DG.getJk_guru());
                PHGPendidikan.setText(DG.getPendidikan_guru());
                PHGMatpel.setText(Keahlianguru);
                PHGHarga.setText(Hargaguru);
                PHGNohp.setText(DG.getNo_hp());
                PHGDeskripsi.setText(DG.getDeskripsi());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //PHGPesan
        PHGPesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Profilhasilguru.this,Detailpesan.class);
                i.putExtra("UserID",test);
                i.putExtra("UserIDGuru",UserIDGuru);
                i.putExtra("Matpel",Keahlianguru);
                i.putExtra("Harga",Hargaguru);
                startActivity(i);
            }
        });
    }

}
