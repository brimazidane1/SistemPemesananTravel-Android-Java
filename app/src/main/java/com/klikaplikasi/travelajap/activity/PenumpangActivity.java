package com.klikaplikasi.travelajap.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.klikaplikasi.travelajap.Config;
import com.klikaplikasi.travelajap.MainActivity;
import com.klikaplikasi.travelajap.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.klikaplikasi.travelajap.R;
import com.klikaplikasi.travelajap.model.Penumpang;
import com.klikaplikasi.travelajap.model.PostPenumpang;
import com.klikaplikasi.travelajap.model.Trayek;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import cz.msebera.android.httpclient.Header;

public class PenumpangActivity extends AppCompatActivity {

    GridLayout mainGrid;
    CardView cardViewPassenger1, cardViewPassenger2, cardViewPassenger3, cardViewPassenger4, cardViewPassenger5, cardViewPassenger6, cardViewPassenger7, cardViewPassenger8;
    Spinner spinnerJenisPembayaran;
    EditText editTextNama1, editTextNama2, editTextNama3, editTextNama4, editTextNama5, editTextNama6, editTextNama7, editTextNama8;
    EditText editTextUmur1, editTextUmur2, editTextUmur3, editTextUmur4, editTextUmur5, editTextUmur6, editTextUmur7, editTextUmur8;
    Spinner editTextJK1, editTextJK2, editTextJK3, editTextJK4, editTextJK5, editTextJK6, editTextJK7, editTextJK8;
    EditText editTextAlamat1, editTextAlamat2, editTextAlamat3, editTextAlamat4, editTextAlamat5, editTextAlamat6, editTextAlamat7, editTextAlamat8;
    EditText editTextNohp1, editTextNohp2, editTextNohp3, editTextNohp4, editTextNohp5, editTextNohp6, editTextNohp7, editTextNohp8;
    EditText editTextEmail1, editTextEmail2, editTextEmail3, editTextEmail4, editTextEmail5, editTextEmail6, editTextEmail7, editTextEmail8;
    int totalPaket = 0;
    int totalBangku = 0;
    int totalPaketTerpilih = 0;
    ArrayList<String> arrayListBangku;
    ArrayList<String> arrayListPaket;
    ArrayList<String> arrayListNama;
    ArrayList<String> arrayListJk;
    ArrayList<String> arrayListUmur;
    ArrayList<String> arrayListAlamat;
    ArrayList<String> arrayListEmail;
    ArrayList<String> arrayListNohp;
    String paketTersedia;
    String idTrayek;
    CardView paket1, paket2, paket3, paket4, paket5;

    private Button buttonProses;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penumpang);
        getSupportActionBar().setTitle("Isi Data Diri");

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("extra");
        idTrayek = intent.getStringExtra("idTrayek");
        arrayListBangku = (ArrayList<String>) bundle.getSerializable("arrayBangku");
        totalBangku = Integer.parseInt(intent.getStringExtra("totalBangku"));

        initView();

        for (int i = 1; i <= totalBangku; i++) {
            if (i == 1)
                cardViewPassenger1.setVisibility(View.VISIBLE);
            if (i == 2)
                cardViewPassenger2.setVisibility(View.VISIBLE);
            if (i == 3)
                cardViewPassenger3.setVisibility(View.VISIBLE);
            if (i == 4)
                cardViewPassenger4.setVisibility(View.VISIBLE);
            if (i == 5)
                cardViewPassenger5.setVisibility(View.VISIBLE);
            if (i == 6)
                cardViewPassenger6.setVisibility(View.VISIBLE);
            if (i == 7)
                cardViewPassenger7.setVisibility(View.VISIBLE);
            if (i == 8)
                cardViewPassenger8.setVisibility(View.VISIBLE);
        }

        //inisialisasi warna paket
        paket1.setCardBackgroundColor(Color.parseColor("#000000"));
        paket2.setCardBackgroundColor(Color.parseColor("#000000"));
        paket3.setCardBackgroundColor(Color.parseColor("#000000"));
        paket4.setCardBackgroundColor(Color.parseColor("#000000"));
        paket5.setCardBackgroundColor(Color.parseColor("#000000"));

        paketTersedia = getIntent().getStringExtra("arrayPaket");
        String[] expl = paketTersedia.split(",");
        totalPaket = expl.length;
        arrayListPaket = new ArrayList<String>();

        //pengecekan paket tersedia
        for (int i = 0; i < expl.length; i++) {
            Log.d("Paket: ", "Masih ada Paket: " + expl[i]);

            switch (expl[i]) {
                case "1_1":
                    paket1.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    setToogleEvent(mainGrid, 0);
                    break;
                case "1_2":
                    paket2.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    setToogleEvent(mainGrid, 1);
                    break;
                case "1_3":
                    paket3.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    setToogleEvent(mainGrid, 2);
                    break;
                case "1_4":
                    paket4.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    setToogleEvent(mainGrid, 3);
                    break;
                case "1_5":
                    paket5.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    setToogleEvent(mainGrid, 4);
                    break;
                default:
//                    setToogleEvent(mainGrid, 1);
//                    cardView.setCardBackgroundColor(Color.parseColor("#000000"));
                    break;
            }
        }


        buttonProses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(PenumpangActivity.this);
                progressDialog.setTitle("Loading...");
                progressDialog.setMessage("Sedang Membuat Booking....");
                progressDialog.show();

                if (populateData()) {
                    AsyncHttpClient client = new AsyncHttpClient();
                    RequestParams params = new RequestParams();

                    String paymentType = spinnerJenisPembayaran.getSelectedItem().toString();
                    params.put("total_bangku", totalBangku);
                    params.put("id_trayek", idTrayek);
                    params.put("jenis_pembayaran", paymentType);
                    params.put("nama", Arrays.toString(arrayListNama.toArray()));
                    params.put("jk", Arrays.toString(arrayListJk.toArray()));
                    params.put("umur", Arrays.toString(arrayListUmur.toArray()));
                    params.put("alamat", Arrays.toString(arrayListAlamat.toArray()));
                    params.put("email", Arrays.toString(arrayListEmail.toArray()));
                    params.put("nohp", Arrays.toString(arrayListNohp.toArray()));
                    params.put("tempat_duduk", Arrays.toString(arrayListBangku.toArray()));
                    params.put("paket", Arrays.toString(arrayListPaket.toArray()));

                    client.post(Config.API_DATA_PENUMPANG, params, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            progressDialog.dismiss();
                            if (statusCode == 204) {
                                Intent intent = new Intent(PenumpangActivity.this, ThankYouActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            } else {
                                Toast.makeText(PenumpangActivity.this, "Terjadi kegagalan sistem, silahkan hubungi pengembang", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            progressDialog.dismiss();
                            if (statusCode == 409) {
                                String result = new String(responseBody);
                                try {
                                    JSONObject jsonResponse = new JSONObject(result);

                                    Toast.makeText(PenumpangActivity.this, jsonResponse.getString("message"), Toast.LENGTH_SHORT).show();
                                    finish();

                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            } else {
                                Toast.makeText(PenumpangActivity.this, "Terjadi kegagalan sistem, silahkan hubungi pengembang", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    });
                };


            }
        });

    }

    private void initView() {
        mainGrid = findViewById(R.id.gridPaketMain);
        buttonProses = findViewById(R.id.btnProses);
        paket1 = findViewById(R.id.paket_1);
        paket2 = findViewById(R.id.paket_2);
        paket3 = findViewById(R.id.paket_3);
        paket4 = findViewById(R.id.paket_4);
        paket5 = findViewById(R.id.paket_5);

        cardViewPassenger1 = findViewById(R.id.cardViewPassenger1);
        cardViewPassenger2 = findViewById(R.id.cardViewPassenger2);
        cardViewPassenger3 = findViewById(R.id.cardViewPassenger3);
        cardViewPassenger4 = findViewById(R.id.cardViewPassenger4);
        cardViewPassenger5 = findViewById(R.id.cardViewPassenger5);
        cardViewPassenger6 = findViewById(R.id.cardViewPassenger6);
        cardViewPassenger7 = findViewById(R.id.cardViewPassenger7);
        cardViewPassenger8 = findViewById(R.id.cardViewPassenger8);

        editTextNama1 = findViewById(R.id.penumpang_nama1);
        editTextNama2 = findViewById(R.id.penumpang_nama2);
        editTextNama3 = findViewById(R.id.penumpang_nama3);
        editTextNama4 = findViewById(R.id.penumpang_nama4);
        editTextNama5 = findViewById(R.id.penumpang_nama5);
        editTextNama6 = findViewById(R.id.penumpang_nama6);
        editTextNama7 = findViewById(R.id.penumpang_nama7);
        editTextNama8 = findViewById(R.id.penumpang_nama8);

        editTextUmur1 = findViewById(R.id.penumpang_umur1);
        editTextUmur2 = findViewById(R.id.penumpang_umur2);
        editTextUmur3 = findViewById(R.id.penumpang_umur3);
        editTextUmur4 = findViewById(R.id.penumpang_umur4);
        editTextUmur5 = findViewById(R.id.penumpang_umur5);
        editTextUmur6 = findViewById(R.id.penumpang_umur6);
        editTextUmur7 = findViewById(R.id.penumpang_umur7);
        editTextUmur8 = findViewById(R.id.penumpang_umur8);

        editTextJK1 = findViewById(R.id.jk1);
        editTextJK2 = findViewById(R.id.jk2);
        editTextJK3 = findViewById(R.id.jk3);
        editTextJK4 = findViewById(R.id.jk4);
        editTextJK5 = findViewById(R.id.jk5);
        editTextJK6 = findViewById(R.id.jk6);
        editTextJK7 = findViewById(R.id.jk7);
        editTextJK8 = findViewById(R.id.jk8);

        editTextAlamat1 = findViewById(R.id.penumpang_alamat1);
        editTextAlamat2 = findViewById(R.id.penumpang_alamat2);
        editTextAlamat3 = findViewById(R.id.penumpang_alamat3);
        editTextAlamat4 = findViewById(R.id.penumpang_alamat4);
        editTextAlamat5 = findViewById(R.id.penumpang_alamat5);
        editTextAlamat6 = findViewById(R.id.penumpang_alamat6);
        editTextAlamat7 = findViewById(R.id.penumpang_alamat7);
        editTextAlamat8 = findViewById(R.id.penumpang_alamat8);

        editTextNohp1 = findViewById(R.id.penumpang_nohp1);
        editTextNohp2 = findViewById(R.id.penumpang_nohp2);
        editTextNohp3 = findViewById(R.id.penumpang_nohp3);
        editTextNohp4 = findViewById(R.id.penumpang_nohp4);
        editTextNohp5 = findViewById(R.id.penumpang_nohp5);
        editTextNohp6 = findViewById(R.id.penumpang_nohp6);
        editTextNohp7 = findViewById(R.id.penumpang_nohp7);
        editTextNohp8 = findViewById(R.id.penumpang_nohp8);

        editTextEmail1 = findViewById(R.id.penumpang_email1);
        editTextEmail2 = findViewById(R.id.penumpang_email2);
        editTextEmail3 = findViewById(R.id.penumpang_email3);
        editTextEmail4 = findViewById(R.id.penumpang_email4);
        editTextEmail5 = findViewById(R.id.penumpang_email5);
        editTextEmail6 = findViewById(R.id.penumpang_email6);
        editTextEmail7 = findViewById(R.id.penumpang_email7);
        editTextEmail8 = findViewById(R.id.penumpang_email8);

        spinnerJenisPembayaran = findViewById(R.id.jenis_pembayaran);
    }

    private boolean populateData() {
        arrayListNama = new ArrayList<String>();
        arrayListJk = new ArrayList<String>();
        arrayListUmur = new ArrayList<String>();
        arrayListAlamat = new ArrayList<String>();
        arrayListEmail = new ArrayList<String>();
        arrayListNohp = new ArrayList<String>();
        try {
            for (int i = 1; i <= totalBangku; i++) {
                if (i == 1) {
                    if (editTextNama1.getText().toString().trim().equals(""))
                        throw new Exception("Kolom Nama tidak boleh kosong");
                    if (editTextJK1.getSelectedItem().toString().trim().equals(""))
                        throw new Exception("Kolom Jenis Kelamin tidak boleh kosong");
                    if (editTextUmur1.getText().toString().trim().equals(""))
                        throw new Exception("Kolom Umur tidak boleh kosong");
                    if (editTextAlamat1.getText().toString().trim().equals(""))
                        throw new Exception("Kolom Alamat tidak boleh kosong");
                    if (editTextEmail1.getText().toString().trim().equals(""))
                        throw new Exception("Kolom Email tidak boleh kosong");
                    if (editTextNohp1.getText().toString().trim().equals(""))
                        throw new Exception("Kolom No HP tidak boleh kosong");

                    arrayListNama.add(editTextNama1.getText().toString());
                    arrayListJk.add(editTextJK1.getSelectedItem().toString());
                    arrayListUmur.add(editTextUmur1.getText().toString());
                    arrayListAlamat.add(editTextAlamat1.getText().toString());
                    arrayListEmail.add(editTextEmail1.getText().toString());
                    arrayListNohp.add(editTextNohp1.getText().toString());
                }
                if (i == 2) {
                    if (editTextNama2.getText().toString().trim().equals(""))
                        throw new Exception("Kolom Nama tidak boleh kosong");
                    if (editTextJK2.getSelectedItem().toString().trim().equals(""))
                        throw new Exception("Kolom Jenis Kelamin tidak boleh kosong");
                    if (editTextUmur2.getText().toString().trim().equals(""))
                        throw new Exception("Kolom Umur tidak boleh kosong");
                    if (editTextAlamat2.getText().toString().trim().equals(""))
                        throw new Exception("Kolom Alamat tidak boleh kosong");
                    if (editTextEmail2.getText().toString().trim().equals(""))
                        throw new Exception("Kolom Email tidak boleh kosong");
                    if (editTextNohp2.getText().toString().trim().equals(""))
                        throw new Exception("Kolom No HP tidak boleh kosong");

                    arrayListNama.add(editTextNama2.getText().toString());
                    arrayListJk.add(editTextJK2.getSelectedItem().toString());
                    arrayListUmur.add(editTextUmur2.getText().toString());
                    arrayListAlamat.add(editTextAlamat2.getText().toString());
                    arrayListEmail.add(editTextEmail2.getText().toString());
                    arrayListNohp.add(editTextNohp2.getText().toString());
                }
                if (i == 3) {
                    if (editTextNama3.getText().toString().trim().equals(""))
                        throw new Exception("Kolom Nama tidak boleh kosong");
                    if (editTextJK3.getSelectedItem().toString().trim().equals(""))
                        throw new Exception("Kolom Jenis Kelamin tidak boleh kosong");
                    if (editTextUmur3.getText().toString().trim().equals(""))
                        throw new Exception("Kolom Umur tidak boleh kosong");
                    if (editTextAlamat3.getText().toString().trim().equals(""))
                        throw new Exception("Kolom Alamat tidak boleh kosong");
                    if (editTextEmail3.getText().toString().trim().equals(""))
                        throw new Exception("Kolom Email tidak boleh kosong");
                    if (editTextNohp3.getText().toString().trim().equals(""))
                        throw new Exception("Kolom No HP tidak boleh kosong");

                    arrayListNama.add(editTextNama3.getText().toString());
                    arrayListJk.add(editTextJK3.getSelectedItem().toString());
                    arrayListUmur.add(editTextUmur3.getText().toString());
                    arrayListAlamat.add(editTextAlamat3.getText().toString());
                    arrayListEmail.add(editTextEmail3.getText().toString());
                    arrayListNohp.add(editTextNohp3.getText().toString());
                }
                if (i == 4) {
                    if (editTextNama4.getText().toString().trim().equals(""))
                        throw new Exception("Kolom Nama tidak boleh kosong");
                    if (editTextJK4.getSelectedItem().toString().trim().equals(""))
                        throw new Exception("Kolom Jenis Kelamin tidak boleh kosong");
                    if (editTextUmur4.getText().toString().trim().equals(""))
                        throw new Exception("Kolom Umur tidak boleh kosong");
                    if (editTextAlamat4.getText().toString().trim().equals(""))
                        throw new Exception("Kolom Alamat tidak boleh kosong");
                    if (editTextEmail4.getText().toString().trim().equals(""))
                        throw new Exception("Kolom Email tidak boleh kosong");
                    if (editTextNohp4.getText().toString().trim().equals(""))
                        throw new Exception("Kolom No HP tidak boleh kosong");

                    arrayListNama.add(editTextNama4.getText().toString());
                    arrayListJk.add(editTextJK4.getSelectedItem().toString());
                    arrayListUmur.add(editTextUmur4.getText().toString());
                    arrayListAlamat.add(editTextAlamat4.getText().toString());
                    arrayListEmail.add(editTextEmail4.getText().toString());
                    arrayListNohp.add(editTextNohp4.getText().toString());
                }
                if (i == 5) {
                    if (editTextNama5.getText().toString().trim().equals(""))
                        throw new Exception("Kolom Nama tidak boleh kosong");
                    if (editTextJK5.getSelectedItem().toString().trim().equals(""))
                        throw new Exception("Kolom Jenis Kelamin tidak boleh kosong");
                    if (editTextUmur5.getText().toString().trim().equals(""))
                        throw new Exception("Kolom Umur tidak boleh kosong");
                    if (editTextAlamat5.getText().toString().trim().equals(""))
                        throw new Exception("Kolom Alamat tidak boleh kosong");
                    if (editTextEmail5.getText().toString().trim().equals(""))
                        throw new Exception("Kolom Email tidak boleh kosong");
                    if (editTextNohp5.getText().toString().trim().equals(""))
                        throw new Exception("Kolom No HP tidak boleh kosong");

                    arrayListNama.add(editTextNama5.getText().toString());
                    arrayListJk.add(editTextJK5.getSelectedItem().toString());
                    arrayListUmur.add(editTextUmur5.getText().toString());
                    arrayListAlamat.add(editTextAlamat5.getText().toString());
                    arrayListEmail.add(editTextEmail5.getText().toString());
                    arrayListNohp.add(editTextNohp5.getText().toString());
                }
                if (i == 6) {
                    if (editTextNama6.getText().toString().trim().equals(""))
                        throw new Exception("Kolom Nama tidak boleh kosong");
                    if (editTextJK6.getSelectedItem().toString().trim().equals(""))
                        throw new Exception("Kolom Jenis Kelamin tidak boleh kosong");
                    if (editTextUmur6.getText().toString().trim().equals(""))
                        throw new Exception("Kolom Umur tidak boleh kosong");
                    if (editTextAlamat6.getText().toString().trim().equals(""))
                        throw new Exception("Kolom Alamat tidak boleh kosong");
                    if (editTextEmail6.getText().toString().trim().equals(""))
                        throw new Exception("Kolom Email tidak boleh kosong");
                    if (editTextNohp6.getText().toString().trim().equals(""))
                        throw new Exception("Kolom No HP tidak boleh kosong");

                    arrayListNama.add(editTextNama6.getText().toString());
                    arrayListJk.add(editTextJK6.getSelectedItem().toString());
                    arrayListUmur.add(editTextUmur6.getText().toString());
                    arrayListAlamat.add(editTextAlamat6.getText().toString());
                    arrayListEmail.add(editTextEmail6.getText().toString());
                    arrayListNohp.add(editTextNohp6.getText().toString());
                }
                if (i == 7) {
                    if (editTextNama7.getText().toString().trim().equals(""))
                        throw new Exception("Kolom Nama tidak boleh kosong");
                    if (editTextJK7.getSelectedItem().toString().trim().equals(""))
                        throw new Exception("Kolom Jenis Kelamin tidak boleh kosong");
                    if (editTextUmur7.getText().toString().trim().equals(""))
                        throw new Exception("Kolom Umur tidak boleh kosong");
                    if (editTextAlamat7.getText().toString().trim().equals(""))
                        throw new Exception("Kolom Alamat tidak boleh kosong");
                    if (editTextEmail7.getText().toString().trim().equals(""))
                        throw new Exception("Kolom Email tidak boleh kosong");
                    if (editTextNohp7.getText().toString().trim().equals(""))
                        throw new Exception("Kolom No HP tidak boleh kosong");

                    arrayListNama.add(editTextNama7.getText().toString());
                    arrayListJk.add(editTextJK7.getSelectedItem().toString());
                    arrayListUmur.add(editTextUmur7.getText().toString());
                    arrayListAlamat.add(editTextAlamat7.getText().toString());
                    arrayListEmail.add(editTextEmail7.getText().toString());
                    arrayListNohp.add(editTextNohp7.getText().toString());
                }
                if (i == 8) {
                    if (editTextNama8.getText().toString().trim().equals(""))
                        throw new Exception("Kolom Nama tidak boleh kosong");
                    if (editTextJK8.getSelectedItem().toString().trim().equals(""))
                        throw new Exception("Kolom Jenis Kelamin tidak boleh kosong");
                    if (editTextUmur8.getText().toString().trim().equals(""))
                        throw new Exception("Kolom Umur tidak boleh kosong");
                    if (editTextAlamat8.getText().toString().trim().equals(""))
                        throw new Exception("Kolom Alamat tidak boleh kosong");
                    if (editTextEmail8.getText().toString().trim().equals(""))
                        throw new Exception("Kolom Email tidak boleh kosong");
                    if (editTextNohp8.getText().toString().trim().equals(""))
                        throw new Exception("Kolom No HP tidak boleh kosong");

                    arrayListNama.add(editTextNama8.getText().toString());
                    arrayListJk.add(editTextJK8.getSelectedItem().toString());
                    arrayListUmur.add(editTextUmur8.getText().toString());
                    arrayListAlamat.add(editTextAlamat8.getText().toString());
                    arrayListEmail.add(editTextEmail8.getText().toString());
                    arrayListNohp.add(editTextNohp8.getText().toString());
                }
            }
            return true;
        } catch (Exception e) {
            Toast.makeText(PenumpangActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
            return false;
        }
    }

    private void setToogleEvent(GridLayout mainGrid, Integer paket) {
        final CardView cardView = (CardView) mainGrid.getChildAt(paket);
        final int finalI = paket;
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cardView.getCardBackgroundColor().getDefaultColor() == -1) {
                    if (totalPaketTerpilih < totalBangku) {
                        //Change background color
                        cardView.setCardBackgroundColor(Color.parseColor("#00FF00"));
                        Toast.makeText(PenumpangActivity.this, "Anda memilih paket :" + (finalI + 1), Toast.LENGTH_SHORT).show();

                        switch (paket) {
                            case 0:
                                arrayListPaket.add("1_1");
                                break;
                            case 1:
                                arrayListPaket.add("1_2");
                                break;
                            case 2:
                                arrayListPaket.add("1_3");
                                break;
                            case 3:
                                arrayListPaket.add("1_4");
                                break;
                            case 4:
                                arrayListPaket.add("1_5");
                                break;
                            default:
                                break;
                        }
                        totalPaketTerpilih++;
                    } else {
                        Toast.makeText(PenumpangActivity.this, "Tidak dapat menambah bagasi melebihi jumlah kursi yang dibeli", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //Change background color
                    cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));

                    Toast.makeText(PenumpangActivity.this, "Anda batal memilih paket :" + (finalI + 1), Toast.LENGTH_SHORT).show();
                    switch (paket) {
                        case 0:
                            arrayListPaket.remove("1_1");
                            break;
                        case 1:
                            arrayListPaket.remove("1_2");
                            break;
                        case 2:
                            arrayListPaket.remove("1_3");
                            break;
                        case 3:
                            arrayListPaket.remove("1_4");
                            break;
                        case 4:
                            arrayListPaket.remove("1_5");
                            break;
                        default:
                            break;
                    }
                    totalPaketTerpilih--;
                }

            }
        });
//        }

    }

}