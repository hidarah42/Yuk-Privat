package com.example.hidarah42.yukprivat.Data;

/**
 * Created by hidarah42 on 14/08/17.
 */

public class Modelnotifikasi {

    public Modelnotifikasi(){}
    public String uid_guru,uid_murid,nama_murid,shari_les,hari_les,jam_les,matpel,harga_guru,alamat_murid,status_notifikasi,waktu_pemesanan,no_hp;
    public Modelnotifikasi(String uid_guru, String uid_murid, String nama_murid, String hari_les,String shari_les,String jam_les, String matpel, String harga_guru, String alamat_murid, String status_notifikasi, String waktu_pemesanan, String no_hp){
        this.uid_guru = uid_guru;
        this.uid_murid = uid_murid;
        this.nama_murid = nama_murid;
        this.shari_les = shari_les;
        this.hari_les = hari_les;
        this.jam_les = jam_les;
        this.matpel = matpel;
        this.harga_guru = harga_guru;
        this.alamat_murid=alamat_murid;
        this.status_notifikasi = status_notifikasi;
        this.waktu_pemesanan = waktu_pemesanan;
        this.no_hp = no_hp;
    }

    public String getUid_guru() {
        return uid_guru;
    }

    public String getUid_murid() {
        return uid_murid;
    }

    public String getNama_murid() {
        return nama_murid;
    }

    public String getShari_les() {
        return shari_les;
    }

    public String getHari_les() {
        return hari_les;
    }

    public String getJam_les() {
        return jam_les;
    }

    public String getMatpel() {
        return matpel;
    }

    public String getHarga_guru() {
        return harga_guru;
    }

    public String getAlamat_murid() {
        return alamat_murid;
    }

    public String getStatus_notifikasi() {
        return status_notifikasi;
    }

    public String getWaktu_pemesanan() {
        return waktu_pemesanan;
    }

    public String getNo_hp() {
        return no_hp;
    }
}

