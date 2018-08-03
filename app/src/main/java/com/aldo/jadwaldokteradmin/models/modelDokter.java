package com.aldo.jadwaldokteradmin.models;

/**
 * Created by USER on 07/08/2017.
 */

public class modelDokter {
    String nama;
    String spesialis;
    String jadwal;
    String lokasi;
    String jenis_kelamin;
    String file_foto;
    //int ImageResourceID;


    public modelDokter() {
    }

    public modelDokter(String nama, String spesialis, String jadwal, String lokasi, String jenis_kelamin, String file_foto) {
        this.nama = nama;
        this.spesialis = spesialis;
        this.jadwal = jadwal;
        this.lokasi = lokasi;
        this.jenis_kelamin = jenis_kelamin;
        this.file_foto = file_foto;
    }

    public String getFile_foto() {
        return file_foto;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public String getNama() {
        return nama;
    }

    public String getSpesialis() {
        return spesialis;
    }

    public String getJadwal() {
        return jadwal;
    }

    public String getLokasi() {
        return lokasi;
    }
}
