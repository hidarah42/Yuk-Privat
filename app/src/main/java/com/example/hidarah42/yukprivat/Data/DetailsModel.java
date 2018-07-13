package com.example.hidarah42.yukprivat.Data;

/**
 * Created by hidarah42 on 11/08/17.
 */

public class DetailsModel {

    public DetailsModel(){}
    public String nama_guru,uid_guru,harga_guru,keahlian_guru,no_hp;
    public DetailsModel(String nama_guru, String uid_guru,String harga_guru,String keahlian_guru,String nohp){
        this.nama_guru = nama_guru;
        this.uid_guru = uid_guru;
        this.harga_guru = harga_guru;
        this.keahlian_guru = keahlian_guru;
        this.no_hp = nohp;

    }

    public String getNama_guru() {
        return nama_guru;
    }

    public String getUid_guru() {
        return uid_guru;
    }

    public String getHarga_guru() {
        return harga_guru;
    }

    public String getKeahlian_guru() {
        return keahlian_guru;
    }

    public String getNo_hp() {
        return no_hp;
    }
}