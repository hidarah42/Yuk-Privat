package com.example.hidarah42.yukprivat.Guru;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hidarah42.yukprivat.Data.Data_murid;
import com.example.hidarah42.yukprivat.Data.Modeljadwal;
import com.example.hidarah42.yukprivat.Murid.Dashboardmurid;
import com.example.hidarah42.yukprivat.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Jadwalguru extends AppCompatActivity {

    //variabel global
    private String  UserID,Nama;
    private ListView JGListview;
    private DatabaseReference JGDatabaserefrnce,JGDatabaserefrncemurid,JGDbnotifikasi;
    private FirebaseListAdapter<Modeljadwal>JGAdapter;
    Query sort;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jadwalguru);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //mengambil data UserID dan Nama
        Intent i = this.getIntent();
        UserID = i.getExtras().getString("UserID");
        Nama = i.getExtras().getString("Nama");

        //inisialisasi
        JGListview = (ListView)findViewById(R.id.jglistview);
        JGDatabaserefrnce = FirebaseDatabase.getInstance().getReference().child("guru").child(UserID).child("jadwal");
        JGDatabaserefrncemurid = FirebaseDatabase.getInstance().getReference().child("murid");
        JGDbnotifikasi = FirebaseDatabase.getInstance().getReference().child("notifikasi");
        //Query sort
        sort  =JGDatabaserefrnce.orderByChild("shari_les");

        //setuplistener
        setuplistener();

        //JGListview
        JGListview.setAdapter(JGAdapter);
    }

    private void setuplistener(){
        JGAdapter = new FirebaseListAdapter<Modeljadwal>(this,Modeljadwal.class,R.layout.list_daftarmurid,sort) {
            @Override
            protected void populateView(final View v, final Modeljadwal model, int position) {
                JGDatabaserefrncemurid.child(model.getUid_murid()).getRef().addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Data_murid DM = dataSnapshot.getValue(Data_murid.class);
                        ((TextView)v.findViewById(R.id.ldmnama)).setText(DM.getNama_murid());
                        Picasso.with(Jadwalguru.this)
                                .load(DM.getImage_murid())
                                .into((ImageView) v.findViewById(R.id.ldmpp));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                ((TextView)v.findViewById(R.id.ldmmatpel)).setText(model.getMatpel_les());
                ((TextView)v.findViewById(R.id.ldmhari)).setText(model.getHari_les());
                ((TextView)v.findViewById(R.id.ldmjam)).setText(model.getJam_les());
                ((TextView)v.findViewById(R.id.ldmalamat)).setText(model.getAlamat_murid());
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        JGDatabaserefrncemurid.child(model.getUid_murid()).getRef().addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Data_murid DM = dataSnapshot.getValue(Data_murid.class);
                                Intent panggil = new Intent(Intent.ACTION_DIAL);
                                panggil.setData(Uri.parse("tel:" +DM.getNohp_murid()));
                                startActivity(panggil);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                });
                v.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        AlertDialog.Builder a_lert = new AlertDialog.Builder(Jadwalguru.this);
                        a_lert.setMessage("Apakah anda yakin ingin berhenti?")
                                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        JGDatabaserefrncemurid.child(model.getUid_murid()).child("jadwal").child(model.getUid_guru()).removeValue();
                                        JGDatabaserefrnce.child(model.getUid_murid()).removeValue();
                                        JGDbnotifikasi.child(model.getUid_murid()).child(model.getUid_guru()).child("status_notifikasi").setValue(Nama+": Anda telah dihapus");
                                        finish();
                                    }
                                })
                                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });

                        AlertDialog alert = a_lert.create();
                        alert.setTitle("Berhenti");
                        alert.show();
                        return false;
                    }
                });
                v.setLongClickable(true);
            }
        };
    }

    //kembali ke form sebelumnya
    @Override
    public void onBackPressed() {
        Intent i = new Intent(Jadwalguru.this,Dashboardguru.class);
        i.putExtra("UserID",UserID);
        i.putExtra("Nama",Nama);
        startActivity(i);
    }
}
