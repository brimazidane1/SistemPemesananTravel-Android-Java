package com.klikaplikasi.travelajap;

import java.io.Serializable;

public class Config implements Serializable {

    public static final String SERVER_URL = "http://192.168.43.182/travelajap/api/";

    public static final String API_CEK_PEMBAYARAN =  SERVER_URL + "cek_status_pembayaran/";
    public static final String API_CARI_TRAVEL =  SERVER_URL + "daftar_trayek/";
    public static final String API_PILIH_TEMPAT_DUDUK =  SERVER_URL + "pilih_tempat_duduk/";
    public static final String API_DATA_PENUMPANG =  SERVER_URL + "data_penumpang/";
    public static final String API_BUKTI_BAYAR =  SERVER_URL + "bukti_bayar/";

    public static final String STATUS_ARRAY_NAME = "status_pembayaran";
    public static final String CARI_TRAVEL_ARRAY_NAME = "daftar_trayek";

//    Untuk status pembayaran
    public static final String  ID_PENUMPANG = "id_penumpang";
    public static final String  NAMA_PENUMPANG = "nama";
    public static final String  JENIS_PEMBAYARAN = "jenis_pembayaran";
    public static final String  STATUS = "status";
    public static final String  JSON_MSG = "message";

//    Untuk pencarian travel
    public static final String TRAYEK = "trayek";
    public static final String ID_TRAYEK = "id_trayek";
    public static final String TGL_BERANGKAT = "tanggal";
    public static final String WAKTU_BERANGKAT = "waktu";
    public static final String AVAIL_SEAT = "sisa_tempat_duduk";
    public static final String AVAIL_PACKG = "sisa_paket";
    public static final String ID_RUTE = "id_rute";
    public static final String ID_ARMADA = "id_armada";
    public static final String RUTE_DARI = "rute_dari";
    public static final String RUTE_KE = "rute_ke";
    public static final String HARGA_TRAVEL = "harga";
    public static final String MOBIL_TRAVEL = "mobil";
    public static final String NOPOL_TRAVEL = "no_pol";
    public static final String DRIVER_TRAVEL = "driver";
    public static final String NOHP_TRAVEL = "nohp";
    public static final String ID_TRAVEL = "id_travel";
    public static final String NAMA_TRAVEL = "nama_travel";
    public static final String JUMLAH_TRAYEK = "jumlah_trayek";



}
