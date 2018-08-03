package com.aldo.jadwaldokteradmin.models;

/**
 * Created by USER on 07/08/2017.
 */

public class modelAdmin {
    String nama;
    String email;

    public modelAdmin(String nama, String email) {
        this.nama = nama;
        this.email = email;
    }

    public String getNama() {
        return nama;
    }

    public String getEmail() {
        return email;
    }
}
