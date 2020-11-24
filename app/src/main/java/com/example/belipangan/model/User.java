package com.example.belipangan.model;

public class User {
    private String nama;
    private String email;
    private String noTelpon;

    public User(String nama, String email, String noTelpon) {
        this.nama = nama;
        this.email = email;
        this.noTelpon = noTelpon;
    }

    public User() {
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
}
