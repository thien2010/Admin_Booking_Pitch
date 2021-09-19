package com.example.booking_pitch.data.model;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.booking_pitch.Admin.PitchActivity;
import com.example.booking_pitch.InsertPitch;
import com.example.booking_pitch.R;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.List;

public class AdapterAllUser extends RecyclerView.Adapter<AdapterAllUser.UserViewHoler>{

    Context context;
    List<Users> usersList;

    public AdapterAllUser(Context context, List<Users> users) {
        this.context = context;
        this.usersList = users;
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public UserViewHoler onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_all_user,parent,false);
        return new UserViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull UserViewHoler holder, int position) {
        Users users = usersList.get(position);
        if (usersList == null){
            return;
        }
        holder.user_id.setText("SDT: " + users.getUserID());
        holder.user_name.setText("Tên thành viên: "+users.getUserName());
        holder.user_quantityCancel.setText("Số sân đã hủy: "+users.getQuantityCancel());
        holder.user_quantityKeeping.setText("Số sân đang đặt: "+users.getQuantityKeeping());
        holder.user_quantityWating.setText("Số sân đang chờ: "+users.getQuantityWating());
        holder.user_quantityDone.setText("Số sân đã đá: "+users.getQuantityDone());


    }

    @Override
    public int getItemCount() {
        if (usersList!=null){
            return usersList.size();
        }
        return 0;
    }

    public class UserViewHoler extends RecyclerView.ViewHolder{
        TextView user_id ;
        TextView user_name ;
        TextView user_quantityCancel ;
        TextView user_quantityWating ;
        TextView user_quantityKeeping ;
        TextView user_quantityDone ;
        RecyclerView recyclerView;
        public UserViewHoler(@NonNull @NotNull View itemView) {
            super(itemView);
            user_id = itemView.findViewById(R.id.user_id2);
            user_name = itemView.findViewById(R.id.user_name2);
            user_quantityCancel = itemView.findViewById(R.id.user_quantityCancel);
            user_quantityWating = itemView.findViewById(R.id.user_quantityWating);
            user_quantityKeeping = itemView.findViewById(R.id.user_quantityKeeping);
            user_quantityDone = itemView.findViewById(R.id.user_quantityDone);
            recyclerView = itemView.findViewById(R.id.rcv_alluser);
        }
    }
    public static String numberMoney(String number){
        DecimalFormat decimalFormat = new DecimalFormat("###,###,##0");
        return decimalFormat.format(Double.parseDouble(number));
    }
}
