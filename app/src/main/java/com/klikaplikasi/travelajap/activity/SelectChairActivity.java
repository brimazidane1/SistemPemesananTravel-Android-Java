package com.klikaplikasi.travelajap.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.klikaplikasi.travelajap.Config;
import com.klikaplikasi.travelajap.PencarianActivity;
import com.klikaplikasi.travelajap.R;
import com.klikaplikasi.travelajap.model.PostPenumpang;
import com.klikaplikasi.travelajap.model.Trayek;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class SelectChairActivity extends AppCompatActivity {

    GridLayout mainGrid;
    Double hargaKursi = 0.0;
    Double totalBayar = 0.0;
    int totalKursi = 0;
    TextView totalHarga;
    TextView totalBKursi;
    String kursiTersedia;
    String paketTersedia;
    String Id_Trayek;
    CardView kursi1, kursi2, kursi3, kursi4, kursi5, kursi6, kursi7;
    String ik1;
    String ik2;
    String ik3;
    String ik4;
    String ik5;
    String ik6;
    String ik7;
    ArrayList<String> seatTakenList;

    private Button buttonBook;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_chair);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Pilih Kursi");

        progressDialog = new ProgressDialog(SelectChairActivity.this);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Sedang Memuat Data....");
        progressDialog.show();

        mainGrid = findViewById(R.id.gridSeatMain);
        totalHarga = findViewById(R.id.total_cost);
        totalBKursi = findViewById(R.id.total_seats);
        buttonBook = findViewById(R.id.btnBook);
        kursi1 = findViewById(R.id.kursi_1);
        kursi2 = findViewById(R.id.kursi_2);
        kursi3 = findViewById(R.id.kursi_3);
        kursi4 = findViewById(R.id.kursi_4);
        kursi5 = findViewById(R.id.kursi_5);
        kursi6 = findViewById(R.id.kursi_6);
        kursi7 = findViewById(R.id.kursi_7);

        buttonBook.setEnabled(false);

        Intent availChair = getIntent();
        Id_Trayek = availChair.getStringExtra("id_trayek");

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put(Config.ID_TRAYEK, Id_Trayek);

        seatTakenList = new ArrayList<String>();

        client.get(Config.API_PILIH_TEMPAT_DUDUK, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                try {
                    JSONObject jsonResponse = new JSONObject(result);

                    Trayek trayek = parseTrayekObject(jsonResponse.getJSONObject(Config.TRAYEK));

                    kursiTersedia = trayek.getSisa_tempat_duduk();
                    paketTersedia = trayek.getSisa_paket();
                    hargaKursi =  Double.parseDouble(trayek.getHarga());

                    System.out.println(trayek.getId_trayek());

                    initiateUI();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println(error.getMessage());
                progressDialog.dismiss();
            }
        });

    }

    private Trayek parseTrayekObject(JSONObject jsonObject) throws JSONException {
        Trayek trayek = new Trayek();
        trayek.setId_trayek(jsonObject.getString("id_trayek"));
        trayek.setHarga(jsonObject.getString("harga"));
        trayek.setTanggal(jsonObject.getString("tanggal"));
        trayek.setWaktu(jsonObject.getString("waktu"));
        trayek.setRute_dari(jsonObject.getString("rute_dari"));
        trayek.setRute_ke(jsonObject.getString("rute_ke"));
        trayek.setSisa_paket(jsonObject.getString("sisa_paket"));
        trayek.setSisa_tempat_duduk(jsonObject.getString("sisa_tempat_duduk"));
        trayek.setId_armada(jsonObject.getString("id_armada"));
        trayek.setId_rute(jsonObject.getString("id_rute"));
        return trayek;
    }

    private void initiateUI() {
        //inisialisasi warna kursi
        kursi1.setCardBackgroundColor(Color.parseColor("#000000"));
        kursi2.setCardBackgroundColor(Color.parseColor("#000000"));
        kursi3.setCardBackgroundColor(Color.parseColor("#000000"));
        kursi4.setCardBackgroundColor(Color.parseColor("#000000"));
        kursi5.setCardBackgroundColor(Color.parseColor("#000000"));
        kursi6.setCardBackgroundColor(Color.parseColor("#000000"));
        kursi7.setCardBackgroundColor(Color.parseColor("#000000"));

        ik1 = "0";
        ik2 = "0";
        ik3 = "0";
        ik4 = "0";
        ik5 = "0";
        ik6 = "0";
        ik7 = "0";

        String[] expl = kursiTersedia.split(",");

        //pengecekan kursi tersedia
        for (String s : expl) {
            Log.d("Kursi: ", "Masih ada Kursi: " + s);

            switch (s) {
                case "1_1":
                    kursi1.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    setToogleEvent(mainGrid, 0);
                    ik1 = "0";
                    break;
                case "2_1":
                    kursi2.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    setToogleEvent(mainGrid, 3);
                    ik2 = "0";
                    break;
                case "2_2":
                    kursi3.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    setToogleEvent(mainGrid, 4);
                    ik3 = "0";
                    break;
                case "2_3":
                    kursi4.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    setToogleEvent(mainGrid, 5);
                    ik4 = "0";
                    break;
                case "3_1":
                    kursi5.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    setToogleEvent(mainGrid, 6);
                    ik5 = "0";
                    break;
                case "3_2":
                    kursi6.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    setToogleEvent(mainGrid, 7);
                    ik6 = "0";
                    break;
                case "3_3":
                    kursi7.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    setToogleEvent(mainGrid, 8);
                    ik7 = "0";
                    break;
                default:
//                    setToogleEvent(mainGrid, 1);
//                    cardView.setCardBackgroundColor(Color.parseColor("#000000"));
                    break;
            }

//            if ( == String.valueOf(expl[i])){
//                final CardView cardView = (CardView) mainGrid.getChildAt(i);
//                cardView.setCardBackgroundColor(Color.parseColor("#000000"));
//            }
        }

        buttonBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] ambilkursi = {ik1, ik2, ik3, ik4, ik5, ik6, ik7};
                String totalHarga1 = totalHarga.getText().toString().trim();
                String totalBKursi1 = totalBKursi.getText().toString().trim();
                Intent pPenumpang = new Intent(SelectChairActivity.this, PenumpangActivity.class);
                pPenumpang.putExtra("idTrayek", Id_Trayek);
                pPenumpang.putExtra("totalBayar", totalHarga1);
                pPenumpang.putExtra("arrayPaket",  paketTersedia);
                pPenumpang.putExtra("totalBangku", totalBKursi1);
                Bundle extra = new Bundle();
                extra.putSerializable("arrayBangku", seatTakenList);
                pPenumpang.putExtra("extra", extra);
                Log.e("BookClick:", "" + Id_Trayek + " " + totalHarga1 + " " + " " + Arrays.toString(ambilkursi));
//                Log.e("Cobacoba", "" + Arrays.toString(ambilkursi));
                startActivity(pPenumpang);
            }
        });
    }

    private void setToogleEvent(GridLayout mainGrid, Integer kursi) {
        //Loop all child item of Main Grid
//        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            //You can see , all child item is CardView , so we just cast object to CardView
            final CardView cardView = (CardView) mainGrid.getChildAt(kursi);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (cardView.getCardBackgroundColor().getDefaultColor() == -1) {
                        //Change background color
                        cardView.setCardBackgroundColor(Color.parseColor("#00FF00"));
                        totalBayar += hargaKursi;
                        ++totalKursi;
//                        fSeat = finalI + 1;

                        switch(kursi) {
                            case 0:
                                seatTakenList.add("1_1");
//                                ik1 = "1_1";
                                break;
                            case 3:
                                seatTakenList.add("2_1");
//                                ik2 = "2_1";
                                break;
                            case 4:
                                seatTakenList.add("2_2");
//                                ik3 = "2_2";
                                break;
                            case 5:
                                seatTakenList.add("2_3");
//                                ik4 = "2_3";
                                break;
                            case 6:
                                seatTakenList.add("3_1");
//                                ik5 = "3_1";
                                break;
                            case 7:
                                seatTakenList.add("3_2");
//                                ik6 = "3_2";
                                break;
                            case 8:
                                seatTakenList.add("3_3");
//                                ik7 = "3_3";
                                break;
                            default:
                                break;
                        }
                        Toast.makeText(SelectChairActivity.this, "Anda memilih kursi :" + (kursi==0 ? kursi+1 : kursi-1), Toast.LENGTH_SHORT).show();
                    } else {
                        //Change background color
                        cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                        totalBayar -= hargaKursi;
                        --totalKursi;
                        Toast.makeText(SelectChairActivity.this, "Anda batal memilih kursi :" + (kursi), Toast.LENGTH_SHORT).show();
                        switch(kursi) {
                            case 0:
                                seatTakenList.remove("1_1");
//                                ik1 = "0";
                                break;
                            case 3:
                                seatTakenList.remove("2_1");
//                                ik2 = "0";
                                break;
                            case 4:
                                seatTakenList.remove("2_2");
//                                ik3 = "0";
                                break;
                            case 5:
                                seatTakenList.remove("2_3");
//                                ik4 = "0";
                                break;
                            case 6:
                                seatTakenList.remove("3_1");
//                                ik5 = "0";
                                break;
                            case 7:
                                seatTakenList.remove("3_2");
//                                ik6 = "0";
                                break;
                            case 8:
                                seatTakenList.remove("3_3");
//                                ik7 = "0";
                                break;
                            default:
                                break;
                        }
                        Toast.makeText(SelectChairActivity.this, "Anda membatalkan kursi :" + (kursi==0 ? kursi+1 : kursi-1), Toast.LENGTH_SHORT).show();
                    }

                    totalHarga.setText("" + totalBayar);
                    totalBKursi.setText("" + totalKursi);
                    buttonBook.setEnabled(totalKursi != 0);
                }
            });
//        }

    }
}