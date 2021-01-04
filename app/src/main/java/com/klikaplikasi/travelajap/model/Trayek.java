package com.klikaplikasi.travelajap.model;

import org.json.JSONObject;

public class Trayek {
    public String id_trayek;
    public String tanggal;
    public String waktu;
    public String sisa_tempat_duduk;
    public String sisa_paket;
    public String id_rute;
    public String id_armada;
    public String rute_dari;
    public String rute_ke;
    public String harga;

    public Trayek() {
    }

    public String getId_trayek() {
        return id_trayek;
    }

    public void setId_trayek(String id_trayek) {
        this.id_trayek = id_trayek;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getSisa_tempat_duduk() {
        return sisa_tempat_duduk;
    }

    public void setSisa_tempat_duduk(String sisa_tempat_duduk) {
        this.sisa_tempat_duduk = sisa_tempat_duduk;
    }

    public String getSisa_paket() {
        return sisa_paket;
    }

    public void setSisa_paket(String sisa_paket) {
        this.sisa_paket = sisa_paket;
    }

    public String getId_rute() {
        return id_rute;
    }

    public void setId_rute(String id_rute) {
        this.id_rute = id_rute;
    }

    public String getId_armada() {
        return id_armada;
    }

    public void setId_armada(String id_armada) {
        this.id_armada = id_armada;
    }

    public String getRute_dari() {
        return rute_dari;
    }

    public void setRute_dari(String rute_dari) {
        this.rute_dari = rute_dari;
    }

    public String getRute_ke() {
        return rute_ke;
    }

    public void setRute_ke(String rute_ke) {
        this.rute_ke = rute_ke;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }
}
