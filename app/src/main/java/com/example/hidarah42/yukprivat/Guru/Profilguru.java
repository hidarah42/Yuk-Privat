package com.example.hidarah42.yukprivat.Guru;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hidarah42.yukprivat.Data.Data_guru;
import com.example.hidarah42.yukprivat.Lainnya.CircleTransform;
import com.example.hidarah42.yukprivat.Murid.Profilhasilguru;
import com.example.hidarah42.yukprivat.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Profilguru extends AppCompatActivity {

    //variabel global
    private ImageView Potoprofil;
    private String UserID,Nama;
    private EditText NamaProfil,JenisKelamin,Pendidikanterakhir,NoHPguru,Deskripsi;
    private TextView Keahlian;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaserefrence = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilguru);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //insialisasi
        Potoprofil = (ImageView)findViewById(R.id.ppguru);
        NamaProfil = (EditText)findViewById(R.id.edittextnamaguru);
        JenisKelamin = (EditText)findViewById(R.id.edittextjeniskelamin);
        Pendidikanterakhir = (EditText)findViewById(R.id.pgetpendidikan);
        NoHPguru = (EditText)findViewById(R.id.pgetnohp);
        Deskripsi = (EditText)findViewById(R.id.edittextdeskripsi);
        Keahlian = (TextView)findViewById(R.id.tvtampilkeahlian);

        //membuat edittext tidak bisa diedit
        NamaProfil.setFocusable(false);
        JenisKelamin.setFocusable(false);
        Pendidikanterakhir.setFocusable(false);
        NoHPguru.setFocusable(false);
        Deskripsi.setFocusable(false);

        //mengambil data UserID dan Nama
        Intent i = this.getIntent();
        UserID = i.getExtras().getString("UserID");
        Nama = i.getExtras().getString("Nama");

        //mengambil data
        databaserefrence.child("guru").child(UserID).getRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Data_guru value = dataSnapshot.getValue(Data_guru.class);
                Picasso.with(Profilguru.this)
                        .load(value.getImage())
                        .transform(new CircleTransform())
                        .into(Potoprofil);
                NamaProfil.setText(value.getNama_guru());
                JenisKelamin.setText(value.getJk_guru());
                Pendidikanterakhir.setText(value.getPendidikan_guru());
                NoHPguru.setText(value.getNo_hp());
                Deskripsi.setText(value.getDeskripsi());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //pindah ke form keahlian
        Keahlian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Profilguru.this,Keahlianguru.class);
                i.putExtra("UserID",UserID);
                startActivity(i);
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
            Intent i = new Intent(Profilguru.this,Editprofilguru.class);
            i.putExtra("Nama",Nama);
            i.putExtra("UserID",UserID);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    //kembali ke form sebelumnya
    @Override
    public void onBackPressed() {
        Intent i = new Intent(Profilguru.this,Dashboardguru.class);
        i.putExtra("UserID",UserID);
        i.putExtra("Nama",Nama);
        startActivity(i);
    }
}
