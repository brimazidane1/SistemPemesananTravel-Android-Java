package com.klikaplikasi.travelajap.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.klikaplikasi.travelajap.Config;
import com.klikaplikasi.travelajap.PencarianActivity;
import com.klikaplikasi.travelajap.R;
import com.klikaplikasi.travelajap.model.ItemTravel;
import com.klikaplikasi.travelajap.util.ImageUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cz.msebera.android.httpclient.Header;

public class UploadTransferProofActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    private static final int PERMISSION_REQUEST_STORAGE = 2;

    private TextView textViewKodeVerifikasi;
    private ImageView imageViewBuktiBayar;
    private Button buttonPilihBuktiBayar, buttonUploadBuktiBayar;
    private ProgressDialog progressDialog;

    private Uri uri;
    private String kodeVerifikasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_transfer_proof);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Unggah Bukti Bayar");

        textViewKodeVerifikasi = (TextView) findViewById(R.id.textViewKodeVerifikasi);
        imageViewBuktiBayar = (ImageView) findViewById(R.id.imageViewBuktiBayar);
        buttonPilihBuktiBayar = (Button) findViewById(R.id.buttonPilihBuktiBayar);
        buttonUploadBuktiBayar = (Button) findViewById(R.id.buttonUploadBuktiBayar);
        progressDialog = new ProgressDialog(this);

        Intent intent = getIntent();
        kodeVerifikasi = intent.getStringExtra("kodeVerifikasi");
        textViewKodeVerifikasi.setText("Kode Verifikasi : "+kodeVerifikasi);

        buttonPilihBuktiBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePhoto();
            }
        });

        buttonUploadBuktiBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(UploadTransferProofActivity.this);
                progressDialog.setTitle("Loading...");
                progressDialog.setMessage("Mengunggah Bukti Bayar");
                progressDialog.show();
                if(uri != null) {
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    String encoded = ImageUtil.bitmapToBase64String(bitmap, 100);
                    uploadBase64(encoded);
                }else {
                    Toast.makeText(UploadTransferProofActivity.this, "Silahkan pilih gambar lain", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void choosePhoto() {
        if (ContextCompat.checkSelfPermission(UploadTransferProofActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(UploadTransferProofActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(UploadTransferProofActivity.this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_STORAGE);

        }else{
            openGallery();
        }
    }

    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE);
    }

    private void uploadBase64(String imgBase64) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("photo", imgBase64);
        params.put("kode_verifikasi", kodeVerifikasi);

        client.post(Config.API_BUKTI_BAYAR, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
                if (statusCode == 204) {
                    Toast.makeText(UploadTransferProofActivity.this, "Bukti Transfer berhasil diunggah", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UploadTransferProofActivity.this, "Bukti Transfer diproses, silahkan hubungi aplikator", Toast.LENGTH_SHORT).show();
                }

                finish();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", "~> " + responseBody);
                if(progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
                if (statusCode == 409) {
                    String result = new String(responseBody);
                    try {
                        JSONObject jsonResponse = new JSONObject(result);

                        Toast.makeText(UploadTransferProofActivity.this, jsonResponse.getString("message"), Toast.LENGTH_SHORT).show();
                        finish();

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        Toast.makeText(UploadTransferProofActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(UploadTransferProofActivity.this, "Terjadi kegagalan sistem, silahkan hubungi pengembang", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if(data != null) {
                uri = data.getData();

                imageViewBuktiBayar.setVisibility(View.VISIBLE);
                imageViewBuktiBayar.setImageURI(uri);
                buttonUploadBuktiBayar.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    openGallery();
                }

                return;
            }
        }
    }
}