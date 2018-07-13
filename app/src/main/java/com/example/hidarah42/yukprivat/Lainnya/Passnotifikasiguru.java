package com.example.hidarah42.yukprivat.Lainnya;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.hidarah42.yukprivat.Data.Modelnotifikasi;
import com.example.hidarah42.yukprivat.Guru.Profilnotifikasimurid;

/**
 * Created by hidarah42 on 14/08/17.
 */

public class Passnotifikasiguru {

    public Passnotifikasiguru(Activity activity, Modelnotifikasi model){
        //Deklarasi intent untuk pindah ke Profilnotifikasimurid
        Intent i = new Intent(activity, Profilnotifikasimurid.class);

        //membungkus semua informasi melalui bundle
        Bundle bundle = new Bundle();
        bundle.putString("UserIDGuru",model.getUid_guru());
        bundle.putString("UserIDMurid",model.getUid_murid());
        bundle.putString("Nama_murid",model.getNama_murid());
        bundle.putString("Shari_les",model.getShari_les());
        bundle.putString("Hari_les",model.getHari_les());
        bundle.putString("Jam_les",model.getJam_les());
        bundle.putString("Matpel",model.getMatpel());
        bundle.putString("Harga",model.getHarga_guru());
        bundle.putString("Alamat",model.getAlamat_murid());
        bundle.putString("NOHP",model.getNo_hp());



        //mengirimnya
        i.putExtras(bundle);
        activity.startActivity(i);

    }
}
