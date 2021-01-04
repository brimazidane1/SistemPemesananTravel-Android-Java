package com.klikaplikasi.travelajap.model;

public class Penumpang {
    String nama;
    String jk;
    String umur;
    String alamat;
    String email;
    String nohp;
    String tempatDuduk;
    String paket;

    public Penumpang(String nama, String jk, String umur, String alamat, String email, String nohp, String tempatDuduk, String paket) {
        this.nama = nama;
        this.jk = jk;
        this.umur = umur;
        this.alamat = alamat;
        this.email = email;
        this.nohp = nohp;
        this.tempatDuduk = tempatDuduk;
        this.paket = paket;
    }

    public Penumpang() {
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJk() {
        return jk;
    }

    public void setJk(String jk) {
        this.jk = jk;
    }

    public String getUmur() {
        return umur;
    }

    public void setUmur(String umur) {
        this.umur = umur;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNohp() {
        return nohp;
    }

    public void setNohp(String nohp) {
        this.nohp = nohp;
    }

    public String getTempatDuduk() {
        return tempatDuduk;
    }

    public void setTempatDuduk(String tempatDuduk) {
        this.tempatDuduk = tempatDuduk;
    }

    public String getPaket() {
        return paket;
    }

    public void setPaket(String paket) {
        this.paket = paket;
    }
}
