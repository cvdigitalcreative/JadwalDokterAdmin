package com.aldo.jadwaldokteradmin.models;

/**
 * Created by USER on 22/08/2017.
 */

public class modelRumahSakit {
    String nama;
    String alamat;
    int ImageResourceID;

    public int getImageResourceID() {
        return ImageResourceID;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public void setImageResourceID(int imageResourceID) {
        ImageResourceID = imageResourceID;
    }

    public String getNama() {
        return nama;
    }

    public String getAlamat() {
        return alamat;
    }
}
