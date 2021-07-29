package com.example.booking_pitch.data.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.booking_pitch.R;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.List;

public class AdapterRecyclerView extends RecyclerView.Adapter<AdapterRecyclerView.PitchViewHoler>{

    Context context;
    List<PitchClass> pitchClassList;

    public AdapterRecyclerView(Context context, List<PitchClass> pitchClassList) {
        this.context = context;
        this.pitchClassList = pitchClassList;
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public PitchViewHoler onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.get_all_pitch,parent,false);
        return new PitchViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PitchViewHoler holder, int position) {
        PitchClass pitchClass = pitchClassList.get(position);
        if (pitchClass == null){
            return;
        }
        holder.pitch_name.setText(pitchClass.getPitchName());
        holder.price.setText("Giá: "+numberMoney(pitchClass.getPrice())+" VND");
        holder.detail.setText("Chi tiết: "+pitchClass.getDetail());
        Glide.with(context)
                .load("http://datn-2021.herokuapp.com"+pitchClass.getImage())
                .into(holder.image);


    }

    @Override
    public int getItemCount() {
        if (pitchClassList!=null){
            return pitchClassList.size();
        }
        return 0;
    }

    public class PitchViewHoler extends RecyclerView.ViewHolder{
        ImageView image;
        TextView pitch_name ;
        TextView price ;
        TextView detail ;
        RecyclerView recyclerView;
        public PitchViewHoler(@NonNull @NotNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.img_ctSan);
            pitch_name = itemView.findViewById(R.id.tv_pitchName_all);
            price = itemView.findViewById(R.id.tv_price);
            detail = itemView.findViewById(R.id.detail_san);
            recyclerView = itemView.findViewById(R.id.rcv_get_all_pitch);
        }
    }
    public static String numberMoney(String number){
        DecimalFormat decimalFormat = new DecimalFormat("###,###,##0");
        return decimalFormat.format(Double.parseDouble(number));
    }
}
