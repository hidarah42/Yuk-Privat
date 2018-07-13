package com.example.hidarah42.yukprivat.Guru;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hidarah42.yukprivat.Data.Data_guru;
import com.example.hidarah42.yukprivat.Lainnya.Login;
import com.example.hidarah42.yukprivat.Murid.Registermurid;
import com.example.hidarah42.yukprivat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class Registerguru extends Activity {

    //variabel global
    private String Persetujuan;
    private EditText RGEmail,RGPass,RGPass2,RGNama,RGNohp,RGPendidikan,RGNoijazah;
    private CheckBox RGPersetujuan;
    private TextView RGKententuan;
    private Button RGDaftar;
    private ProgressBar Progressbar;
    private FirebaseAuth RGAuth;
    private DatabaseReference RGDatabaserefrence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerguru);

        //inisialisasi
        RGEmail = (EditText)findViewById(R.id.rgetemail);
        RGPass = (EditText)findViewById(R.id.rgetpass);
        RGPass2 = (EditText)findViewById(R.id.rgetpass2);
        RGNama = (EditText)findViewById(R.id.rgetnama);
        RGNohp = (EditText)findViewById(R.id.rgetnohp);
        RGPendidikan = (EditText)findViewById(R.id.rgetpendidikanterakhir);
        RGNoijazah = (EditText)findViewById(R.id.rgetnomorijazah);
        RGPersetujuan = (CheckBox)findViewById(R.id.checkboxpersetujuan);
        RGKententuan = (TextView)findViewById(R.id.syaratpersetujuan);
        RGDaftar = (Button)findViewById(R.id.rgbtnregister);
        RGAuth = FirebaseAuth.getInstance();
        RGDatabaserefrence = FirebaseDatabase.getInstance().getReference().child("guru");
        Progressbar = (ProgressBar)findViewById(R.id.daftar_progressBar);

        //RGPersetujuan
        RGPersetujuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Persetujuan = "YA";
            }
        });

        //RGKetentuan
        RGKententuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertdialog();
            }
        });

        //RGDaftar
        RGDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Daftar();
            }
        });
    }

    //method Daftar
    private void Daftar(){
        final String Nama = RGNama.getText().toString().trim();
        final String Pass = RGPass.getText().toString().trim();
        final String Pass2 = RGPass2.getText().toString().trim();
        final String Email = RGEmail.getText().toString().trim();
        final String NoHP = RGNohp.getText().toString().trim();
        final String Pendidikan = RGPendidikan.getText().toString().trim();
        final String NoIjazah = RGNoijazah.getText().toString().trim();

        if((TextUtils.isEmpty(Email))||(TextUtils.isEmpty(Pass))||(TextUtils.isEmpty(Pass2))||
                TextUtils.isEmpty(Nama)||(TextUtils.isEmpty(NoHP))||(TextUtils.isEmpty(Pendidikan))||(TextUtils.isEmpty(NoIjazah))){
            Toast.makeText(this, "Field yang kosong harap diisi", Toast.LENGTH_SHORT).show();
        }
        else if(!Pass.equals(Pass2)){
            Toast.makeText(this, "Maaf, Password tidak sama", Toast.LENGTH_SHORT).show();
        }
        else if(!RGPersetujuan.isChecked()){
            Toast.makeText(this, "Maaf, Persetujuan harap di ceklis", Toast.LENGTH_SHORT).show();
        }
        else{
            Progressbar.setVisibility(View.VISIBLE);
            RGAuth.createUserWithEmailAndPassword(Email,Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        String user_id = RGAuth.getCurrentUser().getUid();
                        DatabaseReference current_user = RGDatabaserefrence.child(user_id);
                        current_user.child("uid_guru").setValue(user_id);
                        current_user.child("email_guru").setValue(Email);
                        current_user.child("password_guru").setValue(Pass);
                        current_user.child("nama_guru").setValue(Nama);
                        current_user.child("no_hp").setValue(NoHP);
                        current_user.child("pendidikan_guru").setValue(Pendidikan);
                        current_user.child("persetujuan_guru").setValue(Persetujuan);
                        current_user.child("nomor_ijazah").setValue(NoIjazah);

                        RGEmail.setText("");
                        RGPass.setText("");
                        RGPass2.setText("");
                        RGNama.setText("");
                        RGNohp.setText("");
                        RGPendidikan.setText("");
                        RGNoijazah.setText("");
                        RGPersetujuan.setChecked(false);
                        Progressbar.setVisibility(View.GONE);
                        alaretdialogku();
                    }
                }
            });
        }
    }

    //alertdialog
    private void alertdialog(){
        AlertDialog.Builder a_lert = new AlertDialog.Builder(Registerguru.this);
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

    //messagedialog
    private void alaretdialogku(){
        AlertDialog.Builder alertdialognya = new AlertDialog.Builder(this);

        alertdialognya
                .setMessage("Pendftaran Sukses")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Registerguru.this,Login.class));
                    }
                });

        AlertDialog alertdialogbuilder = alertdialognya.create();
        alertdialogbuilder.show();
    }
}
