package com.example.hidarah42.yukprivat.Lainnya;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hidarah42.yukprivat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends Activity {

    //variabel global
    private TextView Register;
    private EditText Email,Password;
    private Button Loginbtn;
    private ProgressBar Loginprogressbar;
    private FirebaseAuth loginmAuth = FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener loginmAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //inisialisasi
        Register = (TextView)findViewById(R.id.tvdaftar);
        Email = (EditText)findViewById(R.id.edittextlogin);
        Password = (EditText)findViewById(R.id.edittextpassword);
        Loginbtn = (Button)findViewById(R.id.buttonlogin);
        Loginprogressbar = (ProgressBar)findViewById(R.id.login_progressBar);

        //pindah ke option register
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,Optionregister.class));
            }
        });

        //login
        Loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
            }
        });

        //method untuk melakukan perpindahan activitn melalui authlistener
        loginmAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    Intent i = new Intent(Login.this, Splashscreen.class);
                    startActivity(i);
                }
            }
        };

    }

    //method login
    private void Login (){
        final String Eemail = Email.getText().toString().trim();
        final String Epassword = Password.getText().toString().trim();

        if((TextUtils.isEmpty(Eemail))||(TextUtils.isEmpty(Epassword))){
            Toast.makeText(this, "Maaf, Field yang kosong harus diisi", Toast.LENGTH_SHORT).show();
        }
        else{
            Loginprogressbar.setVisibility(View.VISIBLE);
            loginmAuth.signInWithEmailAndPassword(Eemail,Epassword).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Loginprogressbar.setVisibility(View.GONE);
                        startActivity(new Intent(Login.this,Splashscreen.class));
                        Toast.makeText(Login.this, "Login berhasil", Toast.LENGTH_SHORT).show();
                    } else{
                        Loginprogressbar.setVisibility(View.GONE);
                        Toast.makeText(Login.this, "Login gagal", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    //method intik mengaktifkan auth
    @Override
    protected void onStart() {
        super.onStart();
        loginmAuth.addAuthStateListener(loginmAuthListener);
    }

    //backpress
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        System.exit(0);
    }


}
