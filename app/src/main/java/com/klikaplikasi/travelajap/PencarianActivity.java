package com.klikaplikasi.travelajap;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.klikaplikasi.travelajap.adapter.TravelAdapter;
import com.klikaplikasi.travelajap.model.ItemTravel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class PencarianActivity extends AppCompatActivity {

    RecyclerView rvTravel;
    ArrayList<ItemTravel> mListTravel;
    TravelAdapter travelAdapter;
    ProgressDialog progressDialog;

    String rute_dari, rute_ke, tanggal, waktu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pencarian);
        Intent fromPencarian = getIntent();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Pencarian Travel");
        rute_dari =  fromPencarian.getStringExtra("rute_dari");
        rute_ke =  fromPencarian.getStringExtra("rute_ke");
        waktu = fromPencarian.getStringExtra("waktu");
        tanggal = fromPencarian.getStringExtra("tanggal");
        mListTravel = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        rvTravel = findViewById(R.id.rv_travel);
        rvTravel.setHasFixedSize(true);
        rvTravel.setLayoutManager(new LinearLayoutManager(this));
        getTravelAvailable(rute_dari, rute_ke, waktu, tanggal);
        Log.d("OnCreateCariAc", rute_dari + rute_ke + waktu + tanggal);
        progressDialog = new ProgressDialog(PencarianActivity.this);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Sedang Mencari Data....");
        progressDialog.show();
    }

    private void getTravelAvailable(String rute_dari, String rute_ke, String waktu, String tanggal) {
        String RD = rute_dari.toLowerCase();
        String RK = rute_ke.toLowerCase();
        String WT = waktu.toLowerCase();


        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("rute_dari", RD);
        params.put("rute_ke", RK);
        params.put("waktu", WT);
        if(tanggal == "null"){
            params.put("tanggal", "");
            Log.d("TGLisNULL", " YES NULL" + tanggal);
        }else{
            params.put("tanggal", tanggal);
            Log.d("TGLisNULL", " NOT NULL " + tanggal);
        }

        client.get(Config.API_CARI_TRAVEL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
                String result = new String(responseBody);
                try {
                    JSONObject jsonResponse = new JSONObject(result);
                    JSONArray travel = jsonResponse.getJSONArray(Config.CARI_TRAVEL_ARRAY_NAME);
                    JSONObject objJson = new JSONObject();
                    for (int i = 0; i < travel.length(); i++) {
                        objJson = travel.getJSONObject(i);
                        ItemTravel objTravel = new ItemTravel();
                        objTravel.setId_trayek(objJson.getString(Config.ID_TRAYEK));
                        objTravel.setTanggal(objJson.getString(Config.TGL_BERANGKAT));
                        objTravel.setWaktu(objJson.getString(Config.WAKTU_BERANGKAT));
                        objTravel.setSisa_tempat_duduk(objJson.getString(Config.AVAIL_SEAT));
                        objTravel.setSisa_paket(objJson.getString(Config.AVAIL_PACKG));
                        objTravel.setId_rute(objJson.getString(Config.ID_RUTE));
                        objTravel.setId_armada(objJson.getString(Config.ID_ARMADA));
                        objTravel.setRute_dari(objJson.getString(Config.RUTE_DARI));
                        objTravel.setRute_ke(objJson.getString(Config.RUTE_KE));
                        objTravel.setHarga(objJson.getString(Config.HARGA_TRAVEL));
                        objTravel.setMobil(objJson.getString(Config.MOBIL_TRAVEL));
                        objTravel.setNo_pol(objJson.getString(Config.NOPOL_TRAVEL));
                        objTravel.setDriver(objJson.getString(Config.DRIVER_TRAVEL));
                        objTravel.setNohp(objJson.getString(Config.NOHP_TRAVEL));
                        objTravel.setId_travel(objJson.getString(Config.ID_TRAVEL));
                        objTravel.setNama_travel(objJson.getString(Config.NAMA_TRAVEL));
                        mListTravel.add(objTravel);
                    }

//                    Log.d("GOOD", "Response" + result);
                    displayData();

                }catch (JSONException e) {
                    Log.d("JSONExcep", "~> " + e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", "~> " + responseBody);
                if(progressDialog.isShowing()){
                    progressDialog.dismiss();
                }else{
                    progressDialog.dismiss();
                }
            }
        });
    }

    private void displayData() {
        rvTravel.setLayoutManager(new LinearLayoutManager(this));
        travelAdapter = new TravelAdapter(getApplicationContext(), mListTravel, R.layout.card_travel);
        rvTravel.setAdapter(travelAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(PencarianActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}