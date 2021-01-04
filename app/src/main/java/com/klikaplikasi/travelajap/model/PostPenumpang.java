package com.klikaplikasi.travelajap.model;

import java.util.ArrayList;

public class PostPenumpang {
    ArrayList<Penumpang> penumpangArrayList;
    String jenis_transfer;
    String id_trayek;

    public PostPenumpang(ArrayList<Penumpang> penumpangArrayList, String jenis_transfer, String id_trayek) {
        this.penumpangArrayList = penumpangArrayList;
        this.jenis_transfer = jenis_transfer;
        this.id_trayek = id_trayek;
    }

    public PostPenumpang() {
    }

    public ArrayList<Penumpang> getPenumpangArrayList() {
        return penumpangArrayList;
    }

    public void setPenumpangArrayList(ArrayList<Penumpang> penumpangArrayList) {
        this.penumpangArrayList = penumpangArrayList;
    }

    public String getJenis_transfer() {
        return jenis_transfer;
    }

    public void setJenis_transfer(String jenis_transfer) {
        this.jenis_transfer = jenis_transfer;
    }

    public String getId_trayek() {
        return id_trayek;
    }

    public void setId_trayek(String id_trayek) {
        this.id_trayek = id_trayek;
    }
}
