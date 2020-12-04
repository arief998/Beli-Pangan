package com.example.belipangan.model;

public class Order {
    String uidBuyer, namaProduct, namaPembeli, alamatTujuan, status;
    int kuantitas, totalHarga;

    public Order(String uidBuyer, String namaProduct, String namaPembeli, String alamatTujuan, String status, int kuantitas, int totalHarga) {
        this.uidBuyer = uidBuyer;
        this.namaProduct = namaProduct;
        this.namaPembeli = namaPembeli;
        this.alamatTujuan = alamatTujuan;
        this.status = status;
        this.kuantitas = kuantitas;
        this.totalHarga = totalHarga;
    }

    public Order() {
    }

    public String getUidBuyer() {
        return uidBuyer;
    }

    public void setUidBuyer(String uidBuyer) {
        this.uidBuyer = uidBuyer;
    }

    public String getNamaProduct() {
        return namaProduct;
    }

    public void setNamaProduct(String namaProduct) {
        this.namaProduct = namaProduct;
    }

    public String getNamaPembeli() {
        return namaPembeli;
    }

    public void setNamaPembeli(String namaPembeli) {
        this.namaPembeli = namaPembeli;
    }

    public String getAlamatTujuan() {
        return alamatTujuan;
    }

    public void setAlamatTujuan(String alamatTujuan) {
        this.alamatTujuan = alamatTujuan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getKuantitas() {
        return kuantitas;
    }

    public void setKuantitas(int kuantitas) {
        this.kuantitas = kuantitas;
    }

    public int getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(int totalHarga) {
        this.totalHarga = totalHarga;
    }
}
