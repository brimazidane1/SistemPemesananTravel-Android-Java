package com.klikaplikasi.travelajap.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.klikaplikasi.travelajap.R;
import com.klikaplikasi.travelajap.activity.SelectChairActivity;
import com.klikaplikasi.travelajap.model.ItemTravel;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class TravelAdapter extends RecyclerView.Adapter<TravelAdapter.ItemRowHolder> {

    private ArrayList<ItemTravel> dataList;
    private int rowLayout;
    private Context mContex;

    public TravelAdapter(Context context, ArrayList<ItemTravel> dataList, int rowLayout){
        this.dataList = dataList;
        this.rowLayout =  rowLayout;
        this.mContex = context;
    }


    @NonNull
    @Override
    public TravelAdapter.ItemRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_travel, parent, false);

        return new ItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TravelAdapter.ItemRowHolder holder, int position) {



        final ItemTravel  singleItem = dataList.get(position);
        String trvlDate = singleItem.getTanggal();
        String trvlWaktu = singleItem.getWaktu();
        holder.trvTrayek.setText(singleItem.getRute_dari() + " - " + singleItem.getRute_ke());
        holder.trvPrice.setText("Rp. " + singleItem.getHarga());
        holder.trvDate.setText("Waktu Berangkat " + trvlDate + " | " + trvlWaktu );
        holder.trvTrayek.setText(singleItem.getNama_travel() + " | " + singleItem.getMobil() + " | " + singleItem.getNo_pol());
        Picasso.get().load(R.drawable.placeholder).placeholder(R.drawable.placeholder).into(holder.image);
        holder.btnSelectChair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chooseChair = new Intent(v.getContext(), SelectChairActivity.class);
                chooseChair.putExtra("id_trayek", singleItem.getId_trayek());
                chooseChair.putExtra("kursi", singleItem.getSisa_tempat_duduk());
                chooseChair.putExtra("bagasi", singleItem.getSisa_paket());
                chooseChair.putExtra("harga", singleItem.getHarga());
                v.getContext().startActivity(chooseChair);
            }
        });



    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }


    class ItemRowHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView trvRute, trvPrice, trvDate, trvTrayek;
        LinearLayout lyt_parent;
        Button btnSelectChair;

        ItemRowHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.travelImage);
            trvRute = itemView.findViewById(R.id.travelRute);
            trvPrice = itemView.findViewById(R.id.travelPrice);
            trvDate = itemView.findViewById(R.id.travelDate);
            trvTrayek = itemView.findViewById(R.id.travelTrayek);
            lyt_parent = itemView.findViewById(R.id.lyt_parent);
            btnSelectChair = itemView.findViewById(R.id.btnChooseTravel);

        }
    }
}
