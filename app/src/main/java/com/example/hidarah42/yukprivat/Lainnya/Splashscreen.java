package com.example.hidarah42.yukprivat.Lainnya;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.widget.Toast;

import com.example.hidarah42.yukprivat.Data.Data_guru;
import com.example.hidarah42.yukprivat.Data.Data_murid;
import com.example.hidarah42.yukprivat.Guru.Dashboardguru;
import com.example.hidarah42.yukprivat.Murid.Dashboardmurid;
import com.example.hidarah42.yukprivat.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Splashscreen extends Activity {

    //Variabel global
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference splashDatabaseReference = database.getReference();
    public static FirebaseAuth splashauth = FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener splashmAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        splashmAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user==null){
                    Toast.makeText(Splashscreen.this, "Anda belum login", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Splashscreen.this,Login.class));
                }
                else{
                    //cek user
                    final String debug = user.getUid();
                    splashDatabaseReference.child("murid").child(debug).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Data_murid mrd = dataSnapshot.getValue(Data_murid.class);
                            try{
                                Intent i = new Intent(Splashscreen.this,Dashboardmurid.class);
                                i.putExtra("UserID", mrd.getUid_murid());
                                i.putExtra("Nama",mrd.getNama_murid());
                                startActivity(i);
                            }catch (Exception e){
                                splashDatabaseReference.child("guru").child(debug).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        Data_guru gr = dataSnapshot.getValue(Data_guru.class);
                                        Intent i = new Intent(Splashscreen.this,Dashboardguru.class);
                                        i.putExtra("UserID", gr.getUid_guru());
                                        i.putExtra("Nama",gr.getNama_guru());
                                        startActivity(i);
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    }
            }
        };
    }
    //mengaktifkan auth
    @Override
    public void onStart() {
        super.onStart();
        splashauth.addAuthStateListener(splashmAuthListener);
    }

}
