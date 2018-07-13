package com.example.hidarah42.yukprivat.Murid;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hidarah42.yukprivat.Lainnya.Login;
import com.example.hidarah42.yukprivat.Lainnya.Splashscreen;
import com.example.hidarah42.yukprivat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registermurid extends Activity {

    //variabel global
    private String SPersetujuan;
    private EditText Email,Pass,Pass2,Nama,NoHP;
    private CheckBox Persetujuan;
    private TextView Ketentuan;
    private Button Daftar;
    private FirebaseAuth Authnya;
    private DatabaseReference Database;
    private ProgressBar Progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registermurid);

        //inisialisasi
        Email = (EditText)findViewById(R.id.etemailregister);
        Pass = (EditText)findViewById(R.id.etpassregister);
        Pass2 = (EditText)findViewById(R.id.etpass2register);
        Nama = (EditText)findViewById(R.id.etnamaregister);
        NoHP = (EditText)findViewById(R.id.etnohpregister);
        Persetujuan = (CheckBox)findViewById(R.id.checkboxpersetujuan);
        Ketentuan = (TextView)findViewById(R.id.syaratpersetujuan);
        Daftar = (Button)findViewById(R.id.btn_register);
        Authnya = FirebaseAuth.getInstance();
        Database = FirebaseDatabase.getInstance().getReference().child("murid");
        Progressbar = (ProgressBar)findViewById(R.id.daftar_progressBar);

        //checkbox persetujuan
        Persetujuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPersetujuan = "YA";
            }
        });

        //syaratpersetujuan
        Ketentuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertdialog();
            }
        });

        //button daftar
        Daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Registerasi();
            }
        });

        //
    }

    //method registerasi
    private void Registerasi(){
        final String Eemail = Email.getText().toString().trim();
        final String Epass = Pass.getText().toString().trim();
        final String Epass2 = Pass2.getText().toString().trim();
        final String Enama = Nama.getText().toString().trim();
        final String Enohp = NoHP.getText().toString().trim();

        if((TextUtils.isEmpty(Eemail))||(TextUtils.isEmpty(Epass))||(TextUtils.isEmpty(Epass2))||
                TextUtils.isEmpty(Enama)||(TextUtils.isEmpty(Enohp))){
            Toast.makeText(this, "Field yang kosong harap diisi", Toast.LENGTH_SHORT).show();
        }
        else if(!Epass2.equals(Epass)){
            Toast.makeText(this, "Maaf, Password tidak sama", Toast.LENGTH_SHORT).show();
        }
        else if(!Persetujuan.isChecked()){
            Toast.makeText(this, "Maaf, Persetujuan harap di ceklis", Toast.LENGTH_SHORT).show();
        }
        else{
            Progressbar.setVisibility(View.VISIBLE);
            Authnya.createUserWithEmailAndPassword(Eemail,Epass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        String user_id = Authnya.getCurrentUser().getUid();
                        DatabaseReference curent_user = Database.child(user_id);
                        curent_user.child("uid_murid").setValue(user_id);
                        curent_user.child("email_murid").setValue(Eemail);
                        curent_user.child("password_murid").setValue(Epass);
                        curent_user.child("nama_murid").setValue(Enama);
                        curent_user.child("nohp_murid").setValue(Enohp);
                        curent_user.child("persetujuan_murid").setValue(SPersetujuan);

                        Email.setText("");
                        Pass.setText("");
                        Pass2.setText("");
                        Nama.setText("");
                        NoHP.setText("");
                        Persetujuan.setChecked(false);
                        Progressbar.setVisibility(View.GONE);
                        alaretdialogku();

                    }
                }
            });
        }
    }

    //messagedialog
    private void alaretdialogku(){
        AlertDialog.Builder alertdialognya = new AlertDialog.Builder(this);

        alertdialognya
                .setMessage("Pendftaran Sukses")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Registermurid.this,Login.class));
                    }
                });

        AlertDialog alertdialogbuilder = alertdialognya.create();
        alertdialogbuilder.show();
    }

    //alertdialog
    private void alertdialog(){
        AlertDialog.Builder a_lert = new AlertDialog.Builder(Registermurid.this);
        a_lert.setMessage("Dengan ini saya menyatakan bahwa data tersebut benar dan dapat dipertanggung jawabakan")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        AlertDialog alert = a_lert.create();
        alert.setTitle("Persetujuan");
        alert.show();
    }
}
