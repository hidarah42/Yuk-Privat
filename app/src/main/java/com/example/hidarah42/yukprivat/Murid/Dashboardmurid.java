package com.example.hidarah42.yukprivat.Murid;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hidarah42.yukprivat.Data.Data_guru;
import com.example.hidarah42.yukprivat.Data.Data_murid;
import com.example.hidarah42.yukprivat.Data.Modeljadwal;
import com.example.hidarah42.yukprivat.Lainnya.Splashscreen;
import com.example.hidarah42.yukprivat.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.squareup.picasso.Picasso;

public class Dashboardmurid extends AppCompatActivity {

    //variabel global
    public String UserID, Nama;
    private BottomBar bottombarmurid;
    private ListView DMrListview;
    private DatabaseReference DMrDB,DMrDatabaserefrence,DMrDatabaserefrenceguru,DMrNotifikasi;
    private FirebaseListAdapter<Modeljadwal> DMrAdapter;
    Query sort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboardmurid);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //mengambil data UserID dan Nama
        Intent i = this.getIntent();
        UserID = i.getExtras().getString("UserID");
        Nama = i.getExtras().getString("Nama");
        Toast.makeText(this, "Selamat datang " + Nama, Toast.LENGTH_SHORT).show();

        //inisialisasi
        DMrDB = FirebaseDatabase.getInstance().getReference().child("murid");
        DMrDatabaserefrence = FirebaseDatabase.getInstance().getReference().child("murid").child(UserID).child("jadwal");
        DMrDatabaserefrenceguru = FirebaseDatabase.getInstance().getReference().child("guru");
        DMrNotifikasi = FirebaseDatabase.getInstance().getReference().child("notifikasi");
        DMrListview = (ListView)findViewById(R.id.dmrListview);
        bottombarmurid = (BottomBar) findViewById(R.id.bottombarmurid);

        //bottombar murid
        bottombarmurid.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_notifikasi) {
                    DMrDB.child(UserID).getRef().addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Data_murid DM = dataSnapshot.getValue(Data_murid.class);
                            Intent i = new Intent(Dashboardmurid.this,Notifikasimurid.class);
                            i.putExtra("Nama",DM.getNama_murid());
                            i.putExtra("UserID",DM.getUid_murid());
                            startActivity(i);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                } else if (tabId == R.id.tab_cariguru) {
                    DMrDB.child(UserID).getRef().addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Data_murid DM = dataSnapshot.getValue(Data_murid.class);
                            Intent i = new Intent(Dashboardmurid.this,Cariguru.class);
                            i.putExtra("Nama",DM.getNama_murid());
                            i.putExtra("UserID",DM.getUid_murid());
                            startActivity(i);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                } else if (tabId == R.id.tab_profil) {
                    DMrDB.child(UserID).getRef().addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Data_murid DM = dataSnapshot.getValue(Data_murid.class);
                            Intent i = new Intent(Dashboardmurid.this,Profilmurid.class);
                            i.putExtra("Nama",DM.getNama_murid());
                            i.putExtra("UserID",DM.getUid_murid());
                            startActivity(i);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                } else if (tabId == R.id.tab_keluar) {
                    alertdialog();
                }
            }
        });

        //Query sort
        sort = DMrDatabaserefrence.orderByChild("shari_les");

        //setuplistener
        setuplistener();

        //DMrListview
        DMrListview.setAdapter(DMrAdapter);
    }

    //setuplistener
    private void setuplistener(){
        DMrAdapter = new FirebaseListAdapter<Modeljadwal>(this,Modeljadwal.class,R.layout.list_daftarguru,sort) {
            @Override
            protected void populateView(final View v, final Modeljadwal model, int position) {
                DMrDatabaserefrenceguru.child(model.getUid_guru()).getRef().addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Data_guru DG = dataSnapshot.getValue(Data_guru.class);
                        Picasso.with(Dashboardmurid.this)
                                .load(DG.getImage())
                                .into((ImageView) v.findViewById(R.id.ldgpp));
                        ((TextView) v.findViewById(R.id.ldgnama)).setText(DG.getNama_guru());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                ((TextView) v.findViewById(R.id.ldgmatpel)).setText(model.getMatpel_les());
                ((TextView) v.findViewById(R.id.ldghari)).setText(model.getHari_les());
                ((TextView) v.findViewById(R.id.ldgjam)).setText(model.getJam_les());
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DMrDatabaserefrenceguru.child(model.getUid_guru()).getRef().addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Data_guru DG = dataSnapshot.getValue(Data_guru.class);
                                Intent panggil = new Intent(Intent.ACTION_DIAL);
                                panggil.setData(Uri.parse("tel:" + DG.getNo_hp()));
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
                            AlertDialog.Builder a_lert = new AlertDialog.Builder(Dashboardmurid.this);
                            a_lert.setMessage("Apakah anda yakin ingin berhenti?")
                                    .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            DMrDatabaserefrenceguru.child(model.getUid_guru()).child("jadwal").child(model.getUid_murid()).removeValue();
                                            DMrDatabaserefrence.child(model.getUid_guru()).removeValue();
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

    //alertdialog
    private void alertdialog(){
        AlertDialog.Builder a_lert = new AlertDialog.Builder(Dashboardmurid.this);
        a_lert.setMessage("Apakah anda yakin ingin keluar?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Splashscreen.splashauth.signOut();
                        startActivity(new Intent(Dashboardmurid.this, Splashscreen.class));
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
