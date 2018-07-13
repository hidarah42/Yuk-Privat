package com.example.hidarah42.yukprivat.Data;

/**
 * Created by hidarah42 on 15/08/17.
 */

public class Modeljadwal {
    public Modeljadwal(){}
    public String image,matpel_les,jam_les,nama_guru,nama_murid,shari_les,hari_les,uid_guru,uid_murid,no_hp,alamat_murid;
    public Modeljadwal(String image,String matpel_les,String jam_les,String nama_guru,String nama_murid,String shari_les,String hari_les,String uid_guru,String uid_murid,String no_hp,String alamat_murid){
        this.image = image;
        this.matpel_les = matpel_les;
        this.jam_les = jam_les;
        this.nama_guru = nama_guru;
        this.shari_les = shari_les;
        this.hari_les = hari_les;
        this.uid_murid= uid_murid;
        this.uid_guru = uid_guru;
        this.no_hp = no_hp;
        this.alamat_murid = alamat_murid;
        this.nama_murid = nama_murid;
    }

    public String getShari_les() {
        return shari_les;
    }

    public String getMatpel_les() {
        return matpel_les;
    }

    public String getJam_les() {
        return jam_les;
    }

    public String getImage() {
        return image;
    }

    public String getHari_les() {
        return hari_les;
    }

    public String getNama_guru() {
        return nama_guru;
    }

    public String getNama_murid() {
        return nama_murid;
    }

    public String getUid_guru() {
        return uid_guru;
    }

    public String getUid_murid() {
        return uid_murid;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public String getAlamat_murid() {
        return alamat_murid;
    }
}
