package com.example.belipangan.model;

public class Product {
    private String nama, deskripsi, noTelpon, alamat, uID, kategori, imgUri;
    private int harga;

    public Product() {
    }

    public Product(String nama, String deskripsi, String noTelpon, String alamat, String uID, String kategori, int harga, String imgUri) {
        this.nama = nama;
        this.deskripsi = deskripsi;
        this.noTelpon = noTelpon;
        this.alamat = alamat;
        this.uID = uID;
        this.kategori = kategori;
        this.harga = harga;
        this.imgUri = imgUri;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getNoTelpon() {
        return noTelpon;
    }

    public void setNoTelpon(String noTelpon) {
        this.noTelpon = noTelpon;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) { this.harga = harga; }

    public String getuID() { return uID; }

    public void setuID(String uID) { this.uID = uID; }

    public String getKategori() { return kategori; }

    public void setKategori(String kategori) { this.kategori = kategori; }

    public void setImgUri(String imgUri) { this.imgUri = imgUri; }

    public String getImgUri() { return imgUri; }
}
