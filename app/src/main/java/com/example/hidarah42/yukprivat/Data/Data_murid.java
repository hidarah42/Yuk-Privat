package com.example.hidarah42.yukprivat.Data;

public class Data_murid {
    public String uid_murid,image_murid,jk_murid,alamat_murid,email_murid,password_murid,nama_murid,nohp_murid,hari_les,jam_les,matpel_les,nama_guru,shari_les;

    public Data_murid(){}

    public  Data_murid(String uid_murid,String image_murid,String jk_murid,String alamat_murid,String email_murid,String password_murid,String nama_murid,String nohp_murid,String hari_les,String shari_les,String jam_les,String matpel_les,String nama_guru){
        this.image_murid = image_murid;
        this.uid_murid = uid_murid;
        this.jk_murid = jk_murid;
        this.alamat_murid = alamat_murid;
        this.email_murid = email_murid;
        this.password_murid = password_murid;
        this.nama_murid = nama_murid;
        this.nohp_murid = nohp_murid;
        this.shari_les = shari_les;
        this.hari_les = hari_les;
        this.jam_les = jam_les;
        this.matpel_les = matpel_les;
        this.nama_guru = nama_guru;
    }

    public String getUid_murid() {
        return uid_murid;
    }

    public String getJk_murid() {
        return jk_murid;
    }

    public String getAlamat_murid() {
        return alamat_murid;
    }

    public String getEmail_murid() {
        return email_murid;
    }

    public String getPassword_murid() {
        return password_murid;
    }

    public String getNama_murid() {
        return nama_murid;
    }

    public String getNohp_murid() {
        return nohp_murid;
    }

    public String getImage_murid() {
        return image_murid;
    }

    public String getHari_les() {
        return hari_les;
    }

    public String getShari_les() {
        return shari_les;
    }

    public String getJam_les() {
        return jam_les;
    }

    public String getMatpel_les() {
        return matpel_les;
    }

    public String getNama_guru() {
        return nama_guru;
    }
}
