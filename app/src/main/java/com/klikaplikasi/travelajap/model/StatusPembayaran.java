package com.klikaplikasi.travelajap.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatusPembayaran {

    private String id_penumpang;
    private String nama;
    private String jk;
    private String alamat;
    private String nohp;
    private String tempat_duduk;
    private String paket;
    private String jenis_pembayaran;
    private String kode_verifikasi;
    private String status;
    private String id_trayek;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    //JSON Error STATUS
    private String message;


    public String getId_penumpang() {
        return id_penumpang;
    }

    public void setId_penumpang(String id_penumpang) {
        this.id_penumpang = id_penumpang;
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

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNohp() {
        return nohp;
    }

    public void setNohp(String nohp) {
        this.nohp = nohp;
    }

    public String getTempat_duduk() {
        return tempat_duduk;
    }

    public void setTempat_duduk(String tempat_duduk) {
        this.tempat_duduk = tempat_duduk;
    }

    public String getPaket() {
        return paket;
    }

    public void setPaket(String paket) {
        this.paket = paket;
    }

    public String getJenis_pembayaran() {
        return jenis_pembayaran;
    }

    public void setJenis_pembayaran(String jenis_pembayaran) {
        this.jenis_pembayaran = jenis_pembayaran;
    }

    public String getKode_verifikasi() {
        return kode_verifikasi;
    }

    public void setKode_verifikasi(String kode_verifikasi) {
        this.kode_verifikasi = kode_verifikasi;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId_trayek() {
        return id_trayek;
    }

    public void setId_trayek(String id_trayek) {
        this.id_trayek = id_trayek;
    }
}
