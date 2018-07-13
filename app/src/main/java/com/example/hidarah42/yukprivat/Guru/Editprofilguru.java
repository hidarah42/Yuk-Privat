package com.example.hidarah42.yukprivat.Guru;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.example.hidarah42.yukprivat.R;
import com.firebase.ui.auth.ui.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class Editprofilguru extends AppCompatActivity {

    //variabel global
    private EditText EGHarga1, EGHarga2, EGHarga3, EGNama,EGPendidikan,EGNoijazah,EGNomorhp,EGDeskripsi;
    private Button EGSimpan;
    private ImageButton ubahpprofil;
    private StorageReference EGStoragerefrence;
    private DatabaseReference EGDatabaserefrence,EGDatabaserefrencepencarian;
    private String UserID,JK,Matpel1, Matpel2, Matpel3, Wilayah1, Wilayah2, Wilayah3;
    private Uri imageUri = null;
    private CardView Cardviewkeahlian1, Cardviewkeahlian2, Cardviewkeahlian3;
    private Spinner SpinnerJK,Spinnerkeahlian, Spinnerkeahlian1, Spinnerkeahlian2, Spinnerkeahlian3,Spinnerwilayah1,Spinnerwilayah2,Spinnerwilayah3;
    private ProgressBar Progresbareditguru;

    private static final int GALLERRY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editprofilguru);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //inisialisasi
        EGStoragerefrence = FirebaseStorage.getInstance().getReference().child("guru");
        EGDatabaserefrence = FirebaseDatabase.getInstance().getReference().child("guru");
        EGDatabaserefrencepencarian = FirebaseDatabase.getInstance().getReference().child("pencarian");
        EGNama = (EditText)findViewById(R.id.egetnama);
        EGPendidikan = (EditText)findViewById(R.id.egetpendidikan);
        EGNoijazah = (EditText)findViewById(R.id.egetnoijazah);
        EGNomorhp = (EditText)findViewById(R.id.egetnomortelpon);
        EGDeskripsi = (EditText) findViewById(R.id.egetdeskripsi);
        ubahpprofil = (ImageButton) findViewById(R.id.editpotoprofil);
        EGSimpan = (Button) findViewById(R.id.simpaneditguru);
        Cardviewkeahlian1 = (CardView) findViewById(R.id.cardviewkeahlian1);
        Cardviewkeahlian2 = (CardView) findViewById(R.id.cardviewkeahlian2);
        Cardviewkeahlian3 = (CardView) findViewById(R.id.cardviewkeahlian3);
        Spinnerkeahlian = (Spinner) findViewById(R.id.spinnerkeahlian);
        Spinnerkeahlian1 = (Spinner) findViewById(R.id.spinnerkeahlian1);
        Spinnerwilayah1 = (Spinner)findViewById(R.id.spinnerwilayah1);
        Spinnerkeahlian2 = (Spinner) findViewById(R.id.spinnerkeahlian2);
        Spinnerwilayah2= (Spinner)findViewById(R.id.spinnerwilayah2);
        Spinnerkeahlian3 = (Spinner) findViewById(R.id.spinnerkeahlian3);
        Spinnerwilayah3 = (Spinner)findViewById(R.id.spinnerwilayah3);
        EGHarga1 = (EditText) findViewById(R.id.hargakeahlian1);
        EGHarga2 = (EditText) findViewById(R.id.hargakeahlian2);
        EGHarga3 = (EditText) findViewById(R.id.hargakeahlian3);
        SpinnerJK = (Spinner)findViewById(R.id.egspinnerjk);
        Progresbareditguru = (ProgressBar) findViewById(R.id.edit_progressBar);

        //komponen menghilang
        Cardviewkeahlian1.setVisibility(View.GONE);
        Cardviewkeahlian2.setVisibility(View.GONE);
        Cardviewkeahlian3.setVisibility(View.GONE);

        //alertdialog
        alertdialog();

        //mengambil data UserID
        Intent i = this.getIntent();
        UserID = i.getExtras().getString("UserID");

        //imagebutton
        ubahpprofil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleyIntent = new Intent();
                galleyIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleyIntent.setType("image/*");
                startActivityForResult(galleyIntent, GALLERRY_REQUEST);
            }
        });

        //SpinnerJK
        SpinnerJK.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == 1){
                    JK = "Laki-laki";
                }
                else if (position == 2){
                    JK= "Perempuan";
                }
                else{
                    JK=null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Spinnerkeahlian
        Spinnerkeahlian.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == 1) {
                    Cardviewkeahlian1.setVisibility(View.VISIBLE);
                } else if (position == 2) {
                    Cardviewkeahlian2.setVisibility(View.VISIBLE);
                } else if (position == 3) {
                    Cardviewkeahlian3.setVisibility(View.VISIBLE);
                } else {
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Spinnerkeahlian1
        Spinnerkeahlian1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 1) {
                    Matpel1 = "Bahasa_Indonesia";
                } else if (i == 2) {
                    Matpel1 = "Bahasa_Inggris";
                } else if (i == 3) {
                    Matpel1 = "Matematika_IPA";
                } else if (i == 4) {
                    Matpel1 = "Biologi";
                } else if (i == 5) {
                    Matpel1 = "Fisika";
                } else if (i == 6) {
                    Matpel1 = "Kimia";
                } else if (i == 7) {
                    Matpel1 = "Matematika_IPS";
                } else if (i == 8) {
                    Matpel1 = "Ekonomi";
                } else if (i == 9) {
                    Matpel1 = "Geografi";
                } else if (i == 10) {
                    Matpel1 = "Sosiologi";
                } else {
                    Matpel1 = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Spinnerwilayah1
        Spinnerwilayah1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 1){
                    Wilayah1 = "Beji";
                }
                else if (i==2){
                    Wilayah1 = "Bojongsari";
                }
                else if(i==3){
                    Wilayah1 = "Cilodong";
                }
                else if(i==4){
                    Wilayah1 = "Cimanggis";
                }
                else if (1==5){
                    Wilayah1= "Cinere";
                }
                else if (1==6){
                    Wilayah1= "Cipayung";
                }
                else if (i==7){
                    Wilayah1= "Limo";
                }
                else if (i==8){
                    Wilayah1= "Pancoran_mas";
                }
                else if(i==9){
                    Wilayah1 = "Sawangan";
                }
                else if(i==10){
                    Wilayah1= "Sukmajaya";
                }
                else if(i==11){
                    Wilayah1 = "Tapos";
                }
                else{
                    Wilayah1 = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Spinnerkeahlian2
        Spinnerkeahlian2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 1) {
                    Matpel2 = "Bahasa_Indonesia";
                } else if (i == 2) {
                    Matpel2 = "Bahasa_Inggris";
                } else if (i == 3) {
                    Matpel2 = "Matematika_IPA";
                } else if (i == 4) {
                    Matpel2 = "Biologi";
                } else if (i == 5) {
                    Matpel2 = "Fisika";
                } else if (i == 6) {
                    Matpel2 = "Kimia";
                } else if (i == 7) {
                    Matpel2 = "Matematika_IPS";
                } else if (i == 8) {
                    Matpel2 = "Ekonomi";
                } else if (i == 9) {
                    Matpel2 = "Geografi";
                } else if (i == 10) {
                    Matpel2 = "Sosiologi";
                } else {
                    Matpel2 = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Spinnerwilayah1
        Spinnerwilayah2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 1){
                    Wilayah2 = "Beji";
                }
                else if (i==2){
                    Wilayah2 = "Bojongsari";
                }
                else if(i==3){
                    Wilayah2 = "Cilodong";
                }
                else if(i==4){
                    Wilayah2 = "Cimanggis";
                }
                else if (1==5){
                    Wilayah2= "Cinere";
                }
                else if (1==6){
                    Wilayah2= "Cipayung";
                }
                else if (i==7){
                    Wilayah2= "Limo";
                }
                else if (i==8){
                    Wilayah2= "Pancoran_mas";
                }
                else if(i==9){
                    Wilayah2 = "Sawangan";
                }
                else if(i==10){
                    Wilayah2= "Sukmajaya";
                }
                else if(i==11){
                    Wilayah2 = "Tapos";
                }
                else {
                    Wilayah2 = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Spinnerkeahlian3
        Spinnerkeahlian3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 1) {
                    Matpel3 = "Bahasa_Indonesia";
                } else if (i == 2) {
                    Matpel3 = "Bahasa_Inggris";
                } else if (i == 3) {
                    Matpel3 = "Matematika_IPA";
                } else if (i == 4) {
                    Matpel3 = "Biologi";
                } else if (i == 5) {
                    Matpel3 = "Fisika";
                } else if (i == 6) {
                    Matpel3 = "Kimia";
                } else if (i == 7) {
                    Matpel3 = "Matematika_IPS";
                } else if (i == 8) {
                    Matpel3 = "Ekonomi";
                } else if (i == 9) {
                    Matpel3 = "Geografi";
                } else if (i == 10) {
                    Matpel3 = "Sosiologi";
                } else {
                    Matpel3 = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Spinnerwilayah1
        Spinnerwilayah3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 1){
                    Wilayah3 = "Beji";
                }
                else if (i==2){
                    Wilayah3 = "Bojongsari";
                }
                else if(i==3){
                    Wilayah3 = "Cilodong";
                }
                else if(i==4){
                    Wilayah3 = "Cimanggis";
                }
                else if (1==5){
                    Wilayah3= "Cinere";
                }
                else if (1==6){
                    Wilayah3= "Cipayung";
                }
                else if (i==7){
                    Wilayah3= "Limo";
                }
                else if (i==8){
                    Wilayah3= "Pancoran_mas";
                }
                else if(i==9){
                    Wilayah3 = "Sawangan";
                }
                else if(i==10){
                    Wilayah3= "Sukmajaya";
                }
                else if(i==11){
                    Wilayah3 = "Tapos";
                }
                else{
                    Wilayah3 = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //button simpan
        EGSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpan();
            }
        });

    }

    //untuk membuka galery dan crop image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERRY_REQUEST && resultCode == RESULT_OK) {
            Uri imageuri = data.getData();

            CropImage.activity(imageuri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageUri = result.getUri();
                ubahpprofil.setImageURI(imageUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }

    //method simpan
    public void simpan() {
        final String EdNama = EGNama.getText().toString();
        final String EdPendidikan = EGPendidikan.getText().toString();
        final String EdNOIjazah = EGNoijazah.getText().toString();
        final String EdHarga1 = EGHarga1.getText().toString();
        final String EdHarga2 = EGHarga2.getText().toString();
        final String EdHarga3 = EGHarga3.getText().toString();
        final String EdNOHP = EGNomorhp.getText().toString().trim();
        final String EdDesk = EGDeskripsi.getText().toString().trim();

        Progresbareditguru.setVisibility(View.VISIBLE);
        final StorageReference filepath = EGStoragerefrence.child(UserID).child(imageUri.getLastPathSegment());
        filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
                String url = downloadUrl.toString();
                EGDatabaserefrence.child(UserID).child("image").setValue(url);

                //keahlian1
                if(Matpel1 == null){
                    EGDatabaserefrence.child(UserID).child("keahlian_guru1").setValue(Matpel1);
                    EGDatabaserefrence.child(UserID).child("wilayah_guru1").setValue(Wilayah1);
                    EGDatabaserefrence.child(UserID).child("harga_guru1").setValue("Rp. "+EdHarga1);
                    EGDatabaserefrence.child(UserID).child("no_hp").setValue(EdNOHP);
                    EGDatabaserefrence.child(UserID).child("pendidikan_guru").setValue(EdPendidikan);
                    EGDatabaserefrencepencarian.child("Tidak_Ada").child(EdNama).setValue("Kesalahan");
                }
                else{
                    EGDatabaserefrence.child(UserID).child("keahlian_guru1").setValue(Matpel1);
                    EGDatabaserefrence.child(UserID).child("wilayah_guru1").setValue(Wilayah1);
                    EGDatabaserefrence.child(UserID).child("harga_guru1").setValue("Rp. "+EdHarga1);
                    EGDatabaserefrence.child(UserID).child("no_hp").setValue(EdNOHP);
                    EGDatabaserefrence.child(UserID).child("pendidikan_guru").setValue(EdPendidikan);
                    EGDatabaserefrencepencarian.child(Matpel1).child(Wilayah1).child(UserID).child("nama_guru").setValue(EdNama);
                    EGDatabaserefrencepencarian.child(Matpel1).child(Wilayah1).child(UserID).child("jk_guru").setValue(JK);
                    EGDatabaserefrencepencarian.child(Matpel1).child(Wilayah1).child(UserID).child("uid_guru").setValue(UserID);
                    EGDatabaserefrencepencarian.child(Matpel1).child(Wilayah1).child(UserID).child("harga_guru").setValue("Rp. "+EdHarga1);
                    EGDatabaserefrencepencarian.child(Matpel1).child(Wilayah1).child(UserID).child("keahlian_guru").setValue(Matpel1);
                    EGDatabaserefrencepencarian.child(Matpel1).child(Wilayah1).child(UserID).child("no_hp").setValue(EdNOHP);
                    EGDatabaserefrencepencarian.child(Matpel1).child(Wilayah1).child(UserID).child("pendidikan_guru").setValue(EdPendidikan);
                }

                //keahlian2
                if (Matpel2 == null){
                    EGDatabaserefrence.child(UserID).child("keahlian_guru2").setValue(Matpel2);
                    EGDatabaserefrence.child(UserID).child("wilayah_guru2").setValue(Wilayah2);
                    EGDatabaserefrence.child(UserID).child("harga_guru2").setValue("Rp. "+EdHarga2);
                    EGDatabaserefrence.child(UserID).child("no_hp").setValue(EdNOHP);
                    EGDatabaserefrence.child(UserID).child("pendidikan_guru").setValue(EdPendidikan);
                    EGDatabaserefrencepencarian.child("Tidak_Ada").child(EdNama).setValue("tidakinput");
                }
                else{
                    EGDatabaserefrence.child(UserID).child("keahlian_guru2").setValue(Matpel2);
                    EGDatabaserefrence.child(UserID).child("wilayah_guru2").setValue(Wilayah2);
                    EGDatabaserefrence.child(UserID).child("harga_guru2").setValue("Rp. "+EdHarga2);
                    EGDatabaserefrence.child(UserID).child("no_hp").setValue(EdNOHP);
                    EGDatabaserefrence.child(UserID).child("pendidikan_guru").setValue(EdPendidikan);
                    EGDatabaserefrencepencarian.child(Matpel2).child(Wilayah2).child(UserID).child("nama_guru").setValue(EdNama);
                    EGDatabaserefrencepencarian.child(Matpel2).child(Wilayah2).child(UserID).child("jk_guru").setValue(JK);
                    EGDatabaserefrencepencarian.child(Matpel2).child(Wilayah2).child(UserID).child("uid_guru").setValue(UserID);
                    EGDatabaserefrencepencarian.child(Matpel2).child(Wilayah2).child(UserID).child("harga_guru").setValue("Rp. "+EdHarga2);
                    EGDatabaserefrencepencarian.child(Matpel2).child(Wilayah2).child(UserID).child("keahlian_guru").setValue(Matpel2);
                    EGDatabaserefrencepencarian.child(Matpel2).child(Wilayah2).child(UserID).child("no_hp").setValue(EdNOHP);
                    EGDatabaserefrencepencarian.child(Matpel2).child(Wilayah2).child(UserID).child("pendidikan_guru").setValue(EdPendidikan);
                }

                //keahlian3
                if (Matpel3 == null){
                    EGDatabaserefrence.child(UserID).child("keahlian_guru3").setValue(Matpel3);
                    EGDatabaserefrence.child(UserID).child("wilayah_guru3").setValue(Wilayah3);
                    EGDatabaserefrence.child(UserID).child("harga_guru3").setValue("Rp. "+EdHarga3);
                    EGDatabaserefrence.child(UserID).child("no_hp").setValue(EdNOHP);
                    EGDatabaserefrence.child(UserID).child("pendidikan_guru").setValue(EdPendidikan);
                    EGDatabaserefrencepencarian.child("Tidak_Ada").child(EdNama).setValue("tidakinput");
                }
                else{
                    EGDatabaserefrence.child(UserID).child("keahlian_guru3").setValue(Matpel3);
                    EGDatabaserefrence.child(UserID).child("wilayah_guru3").setValue(Wilayah3);
                    EGDatabaserefrence.child(UserID).child("harga_guru3").setValue("Rp. "+EdHarga3);
                    EGDatabaserefrence.child(UserID).child("no_hp").setValue(EdNOHP);
                    EGDatabaserefrence.child(UserID).child("pendidikan_guru").setValue(EdPendidikan);
                    EGDatabaserefrencepencarian.child(Matpel3).child(Wilayah3).child(UserID).child("nama_guru").setValue(EdNama);
                    EGDatabaserefrencepencarian.child(Matpel3).child(Wilayah3).child(UserID).child("jk_guru").setValue(JK);
                    EGDatabaserefrencepencarian.child(Matpel3).child(Wilayah3).child(UserID).child("uid_guru").setValue(UserID);
                    EGDatabaserefrencepencarian.child(Matpel3).child(Wilayah3).child(UserID).child("harga_guru").setValue("Rp. "+EdHarga3);
                    EGDatabaserefrencepencarian.child(Matpel3).child(Wilayah3).child(UserID).child("keahlian_guru").setValue(Matpel3);
                    EGDatabaserefrencepencarian.child(Matpel3).child(Wilayah3).child(UserID).child("no_hp").setValue(EdNOHP);
                    EGDatabaserefrencepencarian.child(Matpel3).child(Wilayah3).child(UserID).child("pendidikan_guru").setValue(EdPendidikan);
                }

                //nama
                EGDatabaserefrence.child(UserID).child("nama_guru").setValue(EdNama);

                //jk
                EGDatabaserefrence.child(UserID).child("jk_guru").setValue(JK);

                //deskripsi
                EGDatabaserefrence.child(UserID).child("deskripsi").setValue(EdDesk);

                Progresbareditguru.setVisibility(View.GONE);
            }
        });

        finish();
    }

    //alertdialog
    private void alertdialog(){
        AlertDialog.Builder a_lert = new AlertDialog.Builder(Editprofilguru.this);
        a_lert.setMessage("Saat mengedit profil poto,keahlian,wilayah maupun harga/jam harus diisi. jika ada pertanyaan hubungi : 082298322771")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
        AlertDialog alert = a_lert.create();
        alert.setTitle("Petunjuk mengedit profil");
        alert.show();
    }

    //alertdialog
    private void alertdialog2(){
        AlertDialog.Builder a_lert = new AlertDialog.Builder(Editprofilguru.this);
        a_lert.setMessage("Untuk mengisi keahlian di field Lainnya harap huruf pertamanya menggunakan huruf besar, contoh: Pemograman ")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
        AlertDialog alert = a_lert.create();
        alert.setTitle("Petunjuk mengisi keahlian Lainnya");
        alert.show();
    }

}