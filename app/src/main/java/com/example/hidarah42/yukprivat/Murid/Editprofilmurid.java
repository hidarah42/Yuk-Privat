package com.example.hidarah42.yukprivat.Murid;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.example.hidarah42.yukprivat.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class Editprofilmurid extends AppCompatActivity {

    //variabel global
    private String UserID,JKMurid;
    private Uri imageUrimurid;
    private ImageButton Ubahfotomurid;
    private EditText EMNama,EMAlamat,EMNoHP;
    private Spinner SpinnerJKmurid;
    private Button SimpanMurid;
    private ProgressBar Progresbarmurid;
    private StorageReference EMStoragerefrencemurid;
    private DatabaseReference EMDatabaseReferencemurid;

    private static final int GALLERRY_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editprofilmurid);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //inisalisasi
        Ubahfotomurid = (ImageButton)findViewById(R.id.ubahfotomurid);
        EMNama = (EditText)findViewById(R.id.editextubahnamamurid);
        EMAlamat = (EditText)findViewById(R.id.editextubahalamatmurid);
        EMNoHP = (EditText)findViewById(R.id.editextubahnohpmurid);
        SpinnerJKmurid = (Spinner)findViewById(R.id.spinnerubahjkmurid);
        SimpanMurid = (Button)findViewById(R.id.editsimpanmurid);
        Progresbarmurid = (ProgressBar)findViewById(R.id.edit_progressBarmurid);
        EMStoragerefrencemurid = FirebaseStorage.getInstance().getReference().child("murid");
        EMDatabaseReferencemurid = FirebaseDatabase.getInstance().getReference().child("murid");


        //alertdialog()
        alertdialog();

        //mengambil UserID
        Intent i = this.getIntent();
        UserID = i.getExtras().getString("UserID");

        //imagebutton
        Ubahfotomurid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleyIntent = new Intent();
                galleyIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleyIntent.setType("image/*");
                startActivityForResult(galleyIntent, GALLERRY_REQUEST);
            }
        });

        //SpinnerJKmurid
        SpinnerJKmurid.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i==1){
                    JKMurid = "Laki-Laki";
                }
                else if(i==2){
                    JKMurid = "Perempuan";
                }
                else{
                    JKMurid = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //button simpan
        SimpanMurid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpanmurid();
            }
        });

    }

    //untuk membuka gallery dan cropimage
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
                imageUrimurid = result.getUri();
                Ubahfotomurid.setImageURI(imageUrimurid);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    //alertdialog
    private void alertdialog(){
        AlertDialog.Builder a_lert = new AlertDialog.Builder(Editprofilmurid.this);
        a_lert.setMessage("Saat mengedit profil poto,nama,jenis kelamin,alamat dan juga no hp harap diisi. jika ada pertanyaan hubungi : 082298322771")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
        AlertDialog alert = a_lert.create();
        alert.setTitle("Petunjuk mengedit profil");
        alert.show();
    }

    //methodsimpan
    private void simpanmurid(){
        final String ENamamurid = EMNama.getText().toString().trim();
        final String EAlamat = EMAlamat.getText().toString().trim();
        final String ENohp = EMNoHP.getText().toString().trim();

        Progresbarmurid.setVisibility(View.VISIBLE);
        StorageReference filepath = EMStoragerefrencemurid.child(UserID).child(imageUrimurid.getLastPathSegment());
        filepath.putFile(imageUrimurid).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
                String url = downloadUrl.toString();

                EMDatabaseReferencemurid.child(UserID).child("image_murid").setValue(url);
                EMDatabaseReferencemurid.child(UserID).child("nama_murid").setValue(ENamamurid);
                EMDatabaseReferencemurid.child(UserID).child("jk_murid").setValue(JKMurid);
                EMDatabaseReferencemurid.child(UserID).child("alamat_murid").setValue(EAlamat);
                EMDatabaseReferencemurid.child(UserID).child("nohp_murid").setValue(ENohp);
                Progresbarmurid.setVisibility(View.GONE);
                finish();
            }
        });
    }
}
