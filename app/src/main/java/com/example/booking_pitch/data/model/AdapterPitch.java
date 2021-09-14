package com.example.booking_pitch.data.model;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.booking_pitch.R;
import com.example.booking_pitch.data.repository.RequestAPI;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.security.AccessController.getContext;

public class AdapterPitch extends BaseAdapter {
    public Context context;
    PitchClass pitchClass;
    public List<PitchClass> pitchClassList;

    public AdapterPitch(Context context, List<PitchClass> pitchClassList) {
        this.context = context;
        this.pitchClassList = pitchClassList;
    }

    @Override
    public int getCount() {
        return pitchClassList.size();
    }

    @Override
    public PitchClass getItem(int i) {
        return pitchClassList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        PitchClass pro = getItem(i);
            view = LayoutInflater.from(context).inflate(R.layout.pitch_get_all, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.btn_xacNhan = view.findViewById(R.id.btn_xacNhan);
            viewHolder.btn_huy = view.findViewById(R.id.btn_huy);
            viewHolder.pitchName = view.findViewById(R.id.tv_pitchName);
            viewHolder.userID = view.findViewById(R.id.tv_userID);
            viewHolder.date = view.findViewById(R.id.tv_date);
            viewHolder.totalPrice = view.findViewById(R.id.tv_totalPrice);
            viewHolder.umpire = view.findViewById(R.id.umpire_confim);
            viewHolder.tshirt = view.findViewById(R.id.tshirt_comfim);
            viewHolder.span= view.findViewById(R.id.tv_hour);
            viewHolder.water = view.findViewById(R.id.tv_water);
            viewHolder.img = view.findViewById(R.id.img_ctSan);
            viewHolder.userName = view.findViewById(R.id.tv_userName);

            String _id = pro.get_id();
            String date = pro.getDate();
            Log.e("TTT",date);
            String many_date[] = date.split("/");

            viewHolder.date.setText("Ngày: "+date);
            if (!pro.getTotalPrice().equals("")){
                viewHolder.totalPrice.setText("Giá: "+numberMoney(pro.getTotalPrice())+" VND");
            }
            Glide.with(context)
                    .load(pro.getImage())
                    .into(viewHolder.img);
            viewHolder.water.setText(pro.getQuantityWater()+" Bình");
            viewHolder.userName.setText("Người đặt: "+pro.getUserName());
            viewHolder.span.setText(pro.getSpan());
            viewHolder.pitchName.setText(pro.getPitchName());

            viewHolder.umpire.setChecked(pro.isUmpire());
            viewHolder.userID.setText(pro.getUserID());
            viewHolder.tshirt.setChecked(pro.isTshirt());
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://datn-2021.herokuapp.com/api/pitch/user/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            RequestAPI requestAPI = retrofit.create(RequestAPI.class);
            viewHolder.userID.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+pro.getUserID()));
                    context.startActivity(intent);
                }
            });
            viewHolder.btn_xacNhan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (pro.getDayOfWeek().isEmpty()){
                        Call<PitchClass> call = requestAPI.updatePitch(_id,"1","one");
                        call.enqueue(new Callback<PitchClass>() {
                            @Override
                            public void onResponse(Call<PitchClass> call, Response<PitchClass> response) {
                                for (int i = 0 ; i< pitchClassList.size(); i++){
                                    if (pitchClassList.get(i).get_id() == _id){
                                        Log.d("t","ok"+ _id);
                                        pitchClassList.remove(i);
                                        setDatachange(pitchClassList);
                                    }
                                }
                                Toast.makeText(context, "Xác nhận thành công", Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onFailure(Call<PitchClass> call, Throwable t) {
                                Toast.makeText(context, "Xác nhận thất bại", Toast.LENGTH_SHORT).show();
                                Log.e("loi", "one");
                            }
                        });
                    }else {
                        Call<PitchClass> call = requestAPI.updatePitch(pro.getCodeSpecial(),"1","many");
                        call.enqueue(new Callback<PitchClass>() {
                            @Override
                            public void onResponse(Call<PitchClass> call, Response<PitchClass> response) {
                                for (int i = 0 ; i< pitchClassList.size(); i++){
                                    if (pitchClassList.get(i).get_id() == _id){
                                        Log.d("t","ok"+ _id);
                                        pitchClassList.remove(i);
                                        setDatachange(pitchClassList);
                                    }
                                }
                                Toast.makeText(context, "Xác nhận thành công", Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onFailure(Call<PitchClass> call, Throwable t) {
                                Toast.makeText(context, "Xác nhận thất bại", Toast.LENGTH_SHORT).show();
                                Log.e("loi", "many");
                            }
                        });
                    }

                }
            });
            viewHolder.btn_huy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (pro.getDayOfWeek().isEmpty()){
                        Call<PitchClass> call = requestAPI.updatePitch(_id,"-1","one");
                        call.enqueue(new Callback<PitchClass>() {
                            @Override
                            public void onResponse(Call<PitchClass> call, Response<PitchClass> response) {
                                for (int i = 0 ; i< pitchClassList.size(); i++){
                                    if (pitchClassList.get(i).get_id() == _id){
                                        Log.d("t","ok"+ _id);
                                        pitchClassList.remove(i);
                                        setDatachange(pitchClassList);
                                    }
                                }
                                Toast.makeText(context, "Hủy thành công", Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onFailure(Call<PitchClass> call, Throwable t) {
                            }
                        });
                    }else {
                        Call<PitchClass> call = requestAPI.updatePitch(pro.getCodeSpecial(),"-1","many");
                        call.enqueue(new Callback<PitchClass>() {
                            @Override
                            public void onResponse(Call<PitchClass> call, Response<PitchClass> response) {
                                for (int i = 0 ; i< pitchClassList.size(); i++){
                                    if (pitchClassList.get(i).getCodeSpecial() == pro.getCodeSpecial()){
                                        Log.d("t","ok"+ pro.getCodeSpecial());
                                        pitchClassList.remove(i);
                                        setDatachange(pitchClassList);
                                    }
                                }
                                Toast.makeText(context, "Hủy thành công", Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onFailure(Call<PitchClass> call, Throwable t) {
                            }
                        });
                    }
                }
            });
            return view;
    }

    private class ViewHolder {
        TextView span;
        TextView pitchName;
        TextView userID;
        TextView date;
        TextView totalPrice;
        ImageView img;
        CheckBox umpire;
        CheckBox tshirt;
        TextView water;
        TextView userName;
        Button btn_xacNhan;
        Button btn_huy;
    }
    public static String numberMoney(String number){
        DecimalFormat decimalFormat = new DecimalFormat("###,###,##0");
        return decimalFormat.format(Double.parseDouble(number));
    }
    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void setDatachange(List<PitchClass> items) {
        this.pitchClassList = items;
        notifyDataSetChanged();
    }

}
