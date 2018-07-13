package com.example.hidarah42.yukprivat.Lainnya;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.hidarah42.yukprivat.Data.DetailsModel;
import com.example.hidarah42.yukprivat.Murid.Profilhasilguru;


/**
 * Created by hidarah42 on 11/08/17.
 */

public class Intentpass {

    public Intentpass(Activity activity, DetailsModel model) {
        // Deklarasi Intent untuk pindah ke Profilhasilguru
        Intent i = new Intent(activity,Profilhasilguru.class);

        //membungkus semua informasi melalui bundle
        Bundle bundle = new Bundle();
        bundle.putString("Nama",model.getNama_guru());
        bundle.putString("UID",model.getUid_guru());
        bundle.putString("Harga",model.getHarga_guru());
        bundle.putString("Keahlian",model.getKeahlian_guru());
        bundle.putString("NOHP",model.getNo_hp());

        // Memasukkan bundle ke dalam Intent...
        // ...lalu menjalankan Intent
        i.putExtras(bundle);
        activity.startActivity(i);

    }

}
