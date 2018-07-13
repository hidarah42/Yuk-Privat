package com.example.hidarah42.yukprivat.Murid;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import java.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.hidarah42.yukprivat.Data.Data_murid;
import com.example.hidarah42.yukprivat.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

@RequiresApi(api = Build.VERSION_CODES.N)
public class Detailpesan extends AppCompatActivity {

    //variabel global
    private String UserID,UserIDGuru, Matpel, Harga,Hari,SHari, Waktu, Waktusekarang, Pesannotifikasi = "1 Permintaan menjadi guru";
    private int Jam, Menit;
    private Spinner DPSpinnerhari;
    private EditText DPNama,DPJam, DPMatpel, DPAlamat,DPNohp;
    private Button DPSetjam, DPPesan;
    private ProgressBar DPProgressbar;
    final Calendar Kalender = Calendar.getInstance();
    private DatabaseReference databaserefrence;
    private DatabaseReference ngambildatanamamurid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailpesan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //ambil data
        Intent i = this.getIntent();
        UserID = i.getExtras().getString("UserID");
        UserIDGuru = i.getExtras().getString("UserIDGuru");
        Matpel = i.getExtras().getString("Matpel");
        Harga = i.getExtras().getString("Harga");

        //inisialisasi
        DPNama = (EditText)findViewById(R.id.dpetnamamurid);
        DPSpinnerhari = (Spinner) findViewById(R.id.dpspinnerhari);
        DPJam = (EditText) findViewById(R.id.dpetjam);
        DPSetjam = (Button) findViewById(R.id.dpbtnsetjam);
        DPMatpel = (EditText) findViewById(R.id.dpetmatpel);
        DPNohp = (EditText)findViewById(R.id.dpetnohp);
        DPAlamat = (EditText) findViewById(R.id.dpetalamat);
        DPPesan = (Button) findViewById(R.id.dpbtnpesan);
        DPProgressbar = (ProgressBar) findViewById(R.id.dpprogressbar);
        databaserefrence = FirebaseDatabase.getInstance().getReference().child("notifikasi").child(UserIDGuru).child(UserID);
        ngambildatanamamurid = FirebaseDatabase.getInstance().getReference().child("murid").child(UserID);

        //method waktusekarang
        waktusekarang();

        //DPProgressbar
        DPProgressbar.setVisibility(View.GONE);

        //Edittext tidak bisa di edit
        DPJam.setFocusable(false);
        DPMatpel.setFocusable(false);
        DPNama.setFocusable(false);
        DPNohp.setFocusable(false);

        //Ngambildatanamamurid
        ngambildatanamamurid.getRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Data_murid DM = dataSnapshot.getValue(Data_murid.class);
                DPNama.setText(DM.getNama_murid());
                DPNohp.setText(DM.getNohp_murid());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //DPSpinnerhari
        DPSpinnerhari.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 1) {
                    SHari = "ASenin";
                    Hari = "Senin";
                } else if (i == 2) {
                    SHari = "BSelasa";
                    Hari = "Selasa";
                } else if (i == 3) {
                    SHari = "CRabu";
                    Hari = "Rabu";
                } else if (i == 4) {
                    SHari = "DKamis";
                    Hari = "Kamis";
                } else if (i == 5) {
                    SHari = "EJumat";
                    Hari = "Jumat";
                } else if (i == 6) {
                    SHari = "FSabtu";
                    Hari = "Sabtu";
                } else if (i == 7) {
                    SHari = "GMinggu";
                    Hari = "Minggu";
                } else {
                    Toast.makeText(Detailpesan.this, "Isilah data dengan benar", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //DPSetjam
        DPSetjam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Jam = Kalender.get(Calendar.HOUR_OF_DAY);
                Menit = Kalender.get(Calendar.MINUTE);

                TimePickerDialog timerpicker = new TimePickerDialog(Detailpesan.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int jam, int menit) {
                        Waktu = jam + ":" + menit;
                        DPJam.setText(Waktu);
                    }
                }, Jam, Menit, false);
                timerpicker.show();
            }
        });

        //DPMatpel
        DPMatpel.setText(Matpel);

        //DPPesan
        DPPesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pesan();
            }
        });

    }

    //method waktusekarang
    private void waktusekarang() {
        Waktusekarang = Kalender.getTime().toString();
    }

    //method pesan()
    private void pesan() {
        final String NOHP = DPNohp.getText().toString();
        final String Alamat = DPAlamat.getText().toString();
        final String Nama = DPNama.getText().toString();
        final String Sjam = DPJam.getText().toString();

        if (TextUtils.isEmpty(Sjam)||TextUtils.isEmpty(Alamat)){
            Toast.makeText(this, "Harap mengisi field yang kosong", Toast.LENGTH_SHORT).show();
        }

        else{
            DPProgressbar.setVisibility(View.VISIBLE);
            //databaserefrence
            databaserefrence.getRef().addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    databaserefrence.child("uid_guru").setValue(UserIDGuru);
                    databaserefrence.child("uid_murid").setValue(UserID);
                    databaserefrence.child("nama_murid").setValue(Nama);
                    databaserefrence.child("shari_les").setValue(SHari);
                    databaserefrence.child("hari_les").setValue(Hari);
                    databaserefrence.child("jam_les").setValue(Sjam);
                    databaserefrence.child("no_hp").setValue(NOHP);
                    databaserefrence.child("matpel").setValue(Matpel);
                    databaserefrence.child("harga_guru").setValue(Harga);
                    databaserefrence.child("alamat_murid").setValue(Alamat);
                    databaserefrence.child("status_notifikasi").setValue(Pesannotifikasi);
                    databaserefrence.child("waktu_pemesanan").setValue(Waktusekarang);

                    DPProgressbar.setVisibility(View.GONE);

                    //alertdialog
                    AlertDialog.Builder a_lert = new AlertDialog.Builder(Detailpesan.this);
                    a_lert.setMessage("Berhasil, Silahkan tunggu untuk penerimaan dari guru")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    DPNama.setText("");
                                    DPJam.setText("");
                                    DPMatpel.setText("");
                                    DPNohp.setText("");
                                    DPAlamat.setText("");
                                    Intent a = new Intent(Detailpesan.this,Dashboardmurid.class);
                                    a.putExtra("UserID",UserID);
                                    a.putExtra("Nama",Nama);
                                    a.putExtra("NoHP",NOHP);
                                    startActivity(a);
                                }
                            });
                    AlertDialog alert = a_lert.create();
                    alert.show();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

}