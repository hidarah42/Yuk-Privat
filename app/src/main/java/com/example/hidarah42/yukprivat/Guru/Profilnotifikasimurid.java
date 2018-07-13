package com.example.hidarah42.yukprivat.Guru;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hidarah42.yukprivat.Data.Data_guru;
import com.example.hidarah42.yukprivat.Data.Data_murid;
import com.example.hidarah42.yukprivat.Lainnya.CircleTransform;
import com.example.hidarah42.yukprivat.Murid.Dashboardmurid;
import com.example.hidarah42.yukprivat.Murid.Detailpesan;
import com.example.hidarah42.yukprivat.Murid.Notifikasimurid;
import com.example.hidarah42.yukprivat.Murid.Profilhasilguru;
import com.example.hidarah42.yukprivat.R;
import com.firebase.ui.auth.ui.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Profilnotifikasimurid extends AppCompatActivity {

    //variabel global
    private String UserIDMurid, UserIDGuru, Nama, Matpel, Harga, Hari, Shari_les, Jam, Alamat, NOHP;
    private ImageView PNMPotoprofil;
    private EditText PNMNama, PNMJeniskelamin,PNMMatpel, PNMHarga, PNMHari, PNMJam, PNMAlamat;
    private Button PNMTolak, PNMCall, PNMTerima;
    private DatabaseReference PNMDatabaserefrencenotifikasi, PNMDatabaserefrensimurid, PNMDatabaserefrensiguru;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilnotifikasimurid);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //ambil data
        Bundle bundle = getIntent().getExtras();
        UserIDGuru = bundle.getString("UserIDGuru");
        UserIDMurid = bundle.getString("UserIDMurid");
        Nama = bundle.getString("Nama_murid");
        Matpel = bundle.getString("Matpel");
        Harga = bundle.getString("Harga");
        Shari_les = bundle.getString("Shari_les");
        Hari = bundle.getString("Hari_les");
        Jam = bundle.getString("Jam_les");
        NOHP = bundle.getString("NOHP");
        Alamat = bundle.getString("Alamat");


        //inisialisasi
        PNMPotoprofil = (ImageView) findViewById(R.id.pnmpotoprofil);
        PNMNama = (EditText) findViewById(R.id.pnmetnama);
        PNMJeniskelamin = (EditText) findViewById(R.id.pnmetjensikelamin);
        PNMMatpel = (EditText) findViewById(R.id.pnmetmatpel);
        PNMHarga = (EditText) findViewById(R.id.pnmetharga);
        PNMHari = (EditText) findViewById(R.id.pnmethari);
        PNMJam = (EditText) findViewById(R.id.pnmetjam);
        PNMAlamat = (EditText) findViewById(R.id.pnmetalamat);
        PNMTolak = (Button) findViewById(R.id.pnmbtntolak);
        PNMCall = (Button) findViewById(R.id.pnmbtncall);
        PNMTerima = (Button) findViewById(R.id.pnmbtnterima);
        PNMDatabaserefrensimurid = FirebaseDatabase.getInstance().getReference().child("murid").child(UserIDMurid);
        PNMDatabaserefrensiguru = FirebaseDatabase.getInstance().getReference().child("guru");
        PNMDatabaserefrencenotifikasi = FirebaseDatabase.getInstance().getReference().child("notifikasi");

        //edittext tidak bisa di edit
        PNMNama.setFocusable(false);
        PNMJeniskelamin.setFocusable(false);
        PNMMatpel.setFocusable(false);
        PNMHarga.setFocusable(false);
        PNMHari.setFocusable(false);
        PNMJam.setFocusable(false);
        PNMAlamat.setFocusable(false);

        //PNMDatabasemurid
        PNMDatabaserefrensimurid.getRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Data_murid DM = dataSnapshot.getValue(Data_murid.class);
                Picasso.with(Profilnotifikasimurid.this)
                        .load(DM.getImage_murid())
                        .transform(new CircleTransform())
                        .into(PNMPotoprofil);
                PNMNama.setText(Nama);
                PNMJeniskelamin.setText(DM.getJk_murid());
                PNMMatpel.setText(Matpel);
                PNMHarga.setText(Harga);
                PNMHari.setText(Hari);
                PNMJam.setText(Jam);
                PNMAlamat.setText(Alamat);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //PNMTolak
        PNMTolak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //alertdialog
                AlertDialog.Builder a_lert = new AlertDialog.Builder(Profilnotifikasimurid.this);
                a_lert.setMessage("Apa anda yakin ingin menolak?")
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //PNMDatabaserefrenceGuru
                                PNMDatabaserefrensiguru.child(UserIDGuru).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        //PNMDatabaserefrenceNotifikasi
                                        PNMDatabaserefrencenotifikasi.child(UserIDGuru).child(UserIDMurid).removeValue();
                                        //mengambil data
                                        Data_guru DG = dataSnapshot.getValue(Data_guru.class);
                                        PNMDatabaserefrencenotifikasi.child(UserIDMurid).child(UserIDGuru).child("status_notifikasi").setValue(DG.getNama_guru() + " : " + "Mohon maaf anda ditolak");
                                        Intent i = new Intent(Profilnotifikasimurid.this, Notifikasiguru.class);
                                        i.putExtra("UserID", UserIDGuru);
                                        i.putExtra("Nama", DG.getNama_guru());
                                        startActivity(i);
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }
                        });
                AlertDialog alert = a_lert.create();
                alert.show();
            }
        });

        //PNMCall
        PNMCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent panggil = new Intent(Intent.ACTION_DIAL);
                panggil.setData(Uri.parse("tel:" + NOHP));
                startActivity(panggil);
            }
        });

        //PNMTerima
        PNMTerima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //PNMDatabaserefrncemurid
                PNMDatabaserefrensimurid.getRef().addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Data_murid DM = dataSnapshot.getValue(Data_murid.class);
                        PNMDatabaserefrensimurid.child("jadwal").child(UserIDGuru).child("hari_les").setValue(Hari);
                        PNMDatabaserefrensimurid.child("jadwal").child(UserIDGuru).child("shari_les").setValue(Shari_les);
                        PNMDatabaserefrensimurid.child("jadwal").child(UserIDGuru).child("matpel_les").setValue(Matpel);
                        PNMDatabaserefrensimurid.child("jadwal").child(UserIDGuru).child("jam_les").setValue(Jam);
                        PNMDatabaserefrensimurid.child("jadwal").child(UserIDGuru).child("uid_guru").setValue(UserIDGuru);
                        PNMDatabaserefrensimurid.child("jadwal").child(UserIDGuru).child("uid_murid").setValue(UserIDMurid);
                        PNMDatabaserefrensiguru.child(UserIDGuru).child("jadwal").child(UserIDMurid).child("image").setValue(DM.getImage_murid());
                        PNMDatabaserefrensiguru.child(UserIDGuru).child("jadwal").child(UserIDMurid).child("no_hp").setValue(DM.getNohp_murid());

                        //PNMDatabaseguru
                        PNMDatabaserefrensiguru.child(UserIDGuru).getRef().addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Data_guru DG = dataSnapshot.getValue(Data_guru.class);
                                PNMDatabaserefrensimurid.child("jadwal").child(UserIDGuru).child("nama_guru").setValue(DG.getNama_guru());
                                PNMDatabaserefrensimurid.child("jadwal").child(UserIDGuru).child("image").setValue(DG.getImage());
                                PNMDatabaserefrensimurid.child("jadwal").child(UserIDGuru).child("no_hp").setValue(DG.getNo_hp());
                                PNMDatabaserefrensiguru.child(UserIDGuru).child("jadwal").child(UserIDMurid).child("nama_murid").setValue(Nama);
                                PNMDatabaserefrensiguru.child(UserIDGuru).child("jadwal").child(UserIDMurid).child("hari_les").setValue(Hari);
                                PNMDatabaserefrensiguru.child(UserIDGuru).child("jadwal").child(UserIDMurid).child("shari_les").setValue(Shari_les);
                                PNMDatabaserefrensiguru.child(UserIDGuru).child("jadwal").child(UserIDMurid).child("matpel_les").setValue(Matpel);
                                PNMDatabaserefrensiguru.child(UserIDGuru).child("jadwal").child(UserIDMurid).child("jam_les").setValue(Jam);
                                PNMDatabaserefrensiguru.child(UserIDGuru).child("jadwal").child(UserIDMurid).child("alamat_murid").setValue(Alamat);
                                PNMDatabaserefrensiguru.child(UserIDGuru).child("jadwal").child(UserIDMurid).child("uid_murid").setValue(UserIDMurid);
                                PNMDatabaserefrensiguru.child(UserIDGuru).child("jadwal").child(UserIDMurid).child("uid_guru").setValue(UserIDGuru);
                                PNMDatabaserefrencenotifikasi.child(UserIDMurid).child(UserIDGuru).child("status_notifikasi").setValue(DG.getNama_guru() + ":" + "Anda diterima sebagai murid");
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                //hapus notifikasi
                PNMDatabaserefrencenotifikasi.child(UserIDGuru).removeValue();

                //alertdialog
                AlertDialog.Builder a_lert = new AlertDialog.Builder(Profilnotifikasimurid.this);
                a_lert.setMessage("Berhasil")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                PNMDatabaserefrensiguru.child(UserIDGuru).getRef().addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        Data_guru DG = dataSnapshot.getValue(Data_guru.class);
                                        Intent a = new Intent(Profilnotifikasimurid.this,Dashboardguru.class);
                                        a.putExtra("UserID",DG.getUid_guru());
                                        a.putExtra("Nama",DG.getNama_guru());
                                        startActivity(a);
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }
                        });
                AlertDialog alert = a_lert.create();
                alert.show();
            }
        });

    }

}