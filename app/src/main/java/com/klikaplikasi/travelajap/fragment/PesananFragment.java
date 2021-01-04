package com.klikaplikasi.travelajap.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.klikaplikasi.travelajap.Config;
import com.klikaplikasi.travelajap.R;
import com.klikaplikasi.travelajap.activity.UploadTransferProofActivity;
import com.klikaplikasi.travelajap.model.StatusPembayaran;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class PesananFragment extends Fragment {

    private static final int PICK_IMAGE = 1;
    private static final int PERMISSION_REQUEST_STORAGE = 2;

    ArrayList<StatusPembayaran> mListStatus;
    EditText inputKodeBayar;
    Button btn_verifkasi, buttonUploadBuktiBayar;
    ImageView imageViewBuktiBayar;
    TextView nama, sStatus, jenis;
    LinearLayout lytHasil;
    String id;
    ProgressDialog pDialog;
    Uri uri;

    public PesananFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pesanan, container, false);

            inputKodeBayar = view.findViewById(R.id.input_kode_transaksi);
            btn_verifkasi = view.findViewById(R.id.btn_verifikasi);
            nama = view.findViewById(R.id.penumpang_nama);
            sStatus = view.findViewById(R.id.penumpang_status_bayar);
            jenis = view.findViewById(R.id.penumpang_tipe_bayar);
            lytHasil = view.findViewById(R.id.lytStatus);
            buttonUploadBuktiBayar = view.findViewById(R.id.buttonUploadBuktiBayar);
            imageViewBuktiBayar = view.findViewById(R.id.imageViewBuktiBayar);
            pDialog  = new ProgressDialog(getActivity());
            mListStatus = new ArrayList<>();
            btn_verifkasi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pDialog.setTitle("Loading");
                    pDialog.setMessage("Memverifikasi data...");

                    pDialog.show();
                    getStatus(inputKodeBayar.getText().toString());
                }
            });
            buttonUploadBuktiBayar.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), UploadTransferProofActivity.class);
                    intent.putExtra("kodeVerifikasi", inputKodeBayar.getText().toString());
                    startActivity(intent);
                }
            });

        return view;
    }

    private void getStatus(String kodVer){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("kodver", kodVer);
        client.get(Config.API_CEK_PEMBAYARAN, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String (responseBody);

                if(pDialog.isShowing()){
                    pDialog.dismiss();
                }

                try{
                    JSONObject jsonResp = new JSONObject(result);
                    JSONArray status = jsonResp.getJSONArray(Config.STATUS_ARRAY_NAME);
                    JSONObject objJson = new JSONObject();

                    for(int i = 0; i < status.length(); i++){
                        objJson = status.getJSONObject(i);
                        lytHasil.setVisibility(View.VISIBLE);
                        if(Integer.parseInt(objJson.getString(Config.STATUS)) == 1){
                            nama.setVisibility(View.VISIBLE);
                            jenis.setVisibility(View.VISIBLE);
                            sStatus.setVisibility(View.VISIBLE);
                            nama.setText("Nama Penumpang: " + objJson.getString(Config.NAMA_PENUMPANG));
                            jenis.setText("Bayar Via: " + objJson.getString(Config.JENIS_PEMBAYARAN));
                            sStatus.setText("Status Pembayaran: Telah LUNAS" );
                            sStatus.setTextColor(Color.GREEN);
                        }else if(Integer.parseInt(objJson.getString(Config.STATUS)) == 0){
                            nama.setVisibility(View.VISIBLE);
                            jenis.setVisibility(View.VISIBLE);
                            sStatus.setVisibility(View.VISIBLE);
                            nama.setText("Nama Penumpang: " + objJson.getString(Config.NAMA_PENUMPANG));
                            jenis.setText("Bayar Via: " + objJson.getString(Config.JENIS_PEMBAYARAN));
                            sStatus.setText("Status Pembayaran: Menunggu Konfirmasi/Pembayaran");
                            sStatus.setTextColor(Color.RED);
                            if (objJson.getString("jenis_pembayaran").equals("Transfer"))
                                buttonUploadBuktiBayar.setVisibility(View.VISIBLE);
                        }else{
                            nama.setVisibility(View.GONE);
                            jenis.setVisibility(View.GONE);
                            sStatus.setVisibility(View.VISIBLE);
                            sStatus.setText(objJson.getString(Config.JSON_MSG));
                            sStatus.setTextColor(Color.RED);
                        }

                        if (objJson.getString("base64").length() > 0) {
                            String base64 = objJson.getString("base64").replace("data:image/png;base64,", "");
                            byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            imageViewBuktiBayar.setVisibility(View.VISIBLE);
                            imageViewBuktiBayar.setImageBitmap(decodedByte);
                            buttonUploadBuktiBayar.setVisibility(View.GONE);
                        } else
                            buttonUploadBuktiBayar.setVisibility(View.VISIBLE);
                            imageViewBuktiBayar.setVisibility(View.GONE);

                    }
                }catch (JSONException e){
                    Log.d("excDEBUGS", e.toString());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("failDEBUGS", error + "" + responseBody);
                if(pDialog.isShowing()){
                    pDialog.dismiss();
                }else{
                    pDialog.dismiss();
                }
            }
        });
    }


}