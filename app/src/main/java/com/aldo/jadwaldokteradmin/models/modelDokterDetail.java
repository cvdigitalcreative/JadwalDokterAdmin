package com.aldo.jadwaldokteradmin.models;

import java.util.ArrayList;

/**
 * Created by USER on 17/10/2017.
 */

public class modelDokterDetail {
    String nama;
    String alamat;
    String jeniskelamin;
    String notlp;
    ArrayList<String> rumahsakitlainnya;

    public modelDokterDetail() {
    }

    public modelDokterDetail(String nama, String alamat, String jeniskelamin, String notlp) {
        this.nama = nama;
        this.alamat = alamat;
        this.jeniskelamin = jeniskelamin;
        this.notlp = notlp;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public void setJeniskelamin(String jeniskelamin) {
        this.jeniskelamin = jeniskelamin;
    }

    public void setNotlp(String notlp) {
        this.notlp = notlp;
    }

    public void setRumahsakitlainnya(ArrayList<String> rumahsakitlainnya) {
        this.rumahsakitlainnya = rumahsakitlainnya;
    }

    public String getNama() {
        return nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getJeniskelamin() {
        return jeniskelamin;
    }

    public String getNotlp() {
        return notlp;
    }

    public ArrayList<String> getRumahsakitLainnya() {
        return rumahsakitlainnya;
    }
}
