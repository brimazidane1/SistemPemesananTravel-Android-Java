package com.klikaplikasi.travelajap.fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.klikaplikasi.travelajap.PencarianActivity;
import com.klikaplikasi.travelajap.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class BerandaFragment extends Fragment {


    public BerandaFragment() {
        // Required empty public constructor
    }

    String[] kotaTujuan = {"Pekanbaru", "Dumai", "Palembang", "Solok", "Bangkinang", "Bukit Tinggi"};
    String[] kotaAsal = {"Pekanbaru", "Dumai", "Palembang", "Solok", "Bangkinang", "Bukit Tinggi"};
    String[] waktuBerangkat = {"Semua", "Pagi", "Malam"};
    ArrayAdapter<String> asalAdapter;
    ArrayAdapter<String> tujuanAdapter;
    ArrayAdapter<String> waktuAdapter;
    Spinner spinAsal, spinTujuan, spinWaktu;

    public String rute_asal ;
    public String rute_tujuan ;
    public String waktu_berangkat ;
    public String tglBerangkat ;


    DatePickerDialog.OnDateSetListener onDateSetListener;
    TimePickerDialog.OnTimeSetListener onTimeSetListener;
    EditText tgl_perjalanan; //jam_keberangkatan;
    Calendar cal;
    DatePickerDialog datePickerDialog;
//    TimePickerDialog timePickerDialog;
    Button btnCari;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_beranda, container, false);
        tgl_perjalanan = rootView.findViewById(R.id.val_tanggal_perjalanan);
//        jam_keberangkatan = rootView.findViewById(R.id.val_waktu_keberangkatan);
        tgl_perjalanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
//        jam_keberangkatan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showTimePicker();
//            }
//        });

        //Spinner Kota Tujuan
        spinTujuan = rootView.findViewById(R.id.spin_kota_tujuan);
        tujuanAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, kotaTujuan);
        tujuanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinTujuan.setAdapter(tujuanAdapter);
        spinTujuan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                parent.getItemAtPosition(position);
//                rute_tujuan = parent.getItemAtPosition(position).toString();
                rute_tujuan  = parent.getSelectedItem().toString();
                Log.d("spinTujuanOnSelec", rute_tujuan);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getActivity(), "Anda belum memilih Kota Tujuan", Toast.LENGTH_LONG).show();;
            }
        }
        );

        //Spinner Kota Asal
        spinAsal = rootView.findViewById(R.id.spin_kota_asal);
        asalAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, kotaAsal);
        asalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinAsal.setAdapter(asalAdapter);
        spinAsal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                parent.getItemAtPosition(position);
//                rute_asal = parent.getItemAtPosition(position).toString();
                rute_asal = parent.getSelectedItem().toString();
                Log.d("spinAsalOnSelec", rute_asal);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getActivity(), "Anda belum memilih Kota Asal", Toast.LENGTH_LONG).show();;
            }
            }
        );

        //Spinner Waktu Berangkat
        spinWaktu = rootView.findViewById(R.id.spin_waktu_berangkat);
        waktuAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, waktuBerangkat);
        waktuAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinWaktu.setAdapter(waktuAdapter);
        spinWaktu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                               @Override
                                               public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                   parent.getItemAtPosition(position);
//                                                   waktu_berangkat = parent.getItemAtPosition(position).toString();
                                                   waktu_berangkat = parent.getSelectedItem().toString();
                                                   Log.d("spinWaktuOnSelec", waktu_berangkat);
                                               }

                                               @Override
                                               public void onNothingSelected(AdapterView<?> parent) {
                                                   Toast.makeText(getActivity(), "Anda belum memilih Waktu Keberangkatan", Toast.LENGTH_LONG).show();;
                                               }
                                           }
        );


        btnCari = rootView.findViewById(R.id.btn_cari_travel);
        btnCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent result = new Intent(getActivity(), PencarianActivity.class);
                result.putExtra("rute_dari", spinAsal.getSelectedItem().toString());
                result.putExtra("rute_ke", spinTujuan.getSelectedItem().toString());
                result.putExtra("waktu", spinWaktu.getSelectedItem().toString());
                result.putExtra("tanggal", tglBerangkat);
                startActivity(result);
                Log.d("OnClickCari", rute_asal + rute_tujuan + waktu_berangkat + tglBerangkat);
            }
        });


        return rootView;
    }

    private void showDatePicker(){
        cal =  Calendar.getInstance();
        int tahun = cal.get(Calendar.YEAR);
        int bulan = cal.get(Calendar.MONTH);
        int hari  = cal.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(
                getActivity(),
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                onDateSetListener,
                tahun, bulan, hari);
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();
        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                tglBerangkat = year + "-" + month + "-" + dayOfMonth;
                tgl_perjalanan.setText(tglBerangkat);
                tglBerangkat = tglBerangkat;
            }
        };
    }

//    private void showTimePicker(){
//        cal =  Calendar.getInstance();
//        int jam = cal.get(Calendar.HOUR_OF_DAY);
//        int menit = cal.get(Calendar.MINUTE);
//
//        timePickerDialog = new TimePickerDialog(
//                getActivity(),
//                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
//                onTimeSetListener,
//                jam, menit, true);
//        timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        timePickerDialog.show();
//        onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
//            @Override
//            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                String jam_otw = hourOfDay + ":" + minute + " WIB";
//                jam_keberangkatan.setText(jam_otw);
//            }
//        };
//    }


}