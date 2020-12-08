package com.example.belipangan.model;

public class Seller {
    private String nama, email, noTelpon, role, alamat;

    public Seller(String nama, String email, String noTelpon, String role, String alamat) {
        this.nama = nama;
        this.email = email;
        this.noTelpon = noTelpon;
        this.role = role;
        this.alamat = alamat;
    }

    public Seller() {
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNoTelpon() {
        return noTelpon;
    }

    public void setNoTelpon(String noTelpon) {
        this.noTelpon = noTelpon;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}
