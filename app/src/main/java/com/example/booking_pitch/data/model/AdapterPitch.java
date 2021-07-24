package com.example.booking_pitch.data.model;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.example.booking_pitch.R;
import com.example.booking_pitch.data.repository.RequestAPI;

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
//            viewHolder.pitchID = view.findViewById(R.id.tv_pitchID);
            viewHolder.pitchName = view.findViewById(R.id.tv_pitchName);
//            viewHolder.span = view.findViewById(R.id.tv_span);
            viewHolder.userID = view.findViewById(R.id.tv_userID);
            viewHolder.date = view.findViewById(R.id.tv_date);
//            viewHolder.state = view.findViewById(R.id.tv_state);
            viewHolder.totalPrice = view.findViewById(R.id.tv_totalPrice);
//            viewHolder.price = view.findViewById(R.id.tv_price);
            viewHolder.umpire = view.findViewById(R.id.umpire_confim);
//            viewHolder.quantityWater = view.findViewById(R.id.tv_quantityWater);
//            viewHolder.priceWater = view.findViewById(R.id.tv_priceWater);
//            viewHolder.image = view.findViewById(R.id.tv_image);
            viewHolder.tshirt = view.findViewById(R.id.tshirt_comfim);
//            viewHolder.detail = view.findViewById(R.id.tv_detail);
//            viewHolder.createBy = view.findViewById(R.id.tv_createBy);


        String _id = pro.get_id();
        String date = pro.getDate();
        String day = date.substring(0,2);
        String month = date.substring(2,4);
        String year = date.substring(4,8);
//        viewHolder.pitchID.setText(pro.getPitchID());
        viewHolder.pitchName.setText(pro.getPitchName());
        viewHolder.totalPrice.setText("Giá: "+pro.getTotalPrice());
        viewHolder.date.setText(day + "-" +month + "-"+year);
        viewHolder.umpire.setChecked(pro.isUmpire());
//        viewHolder.span.setText(pro.getSpan());
        viewHolder.userID.setText(pro.getUserID());

//        viewHolder.state.setText(pro.getState());
//        viewHolder.price.setText(pro.getPrice());
//        viewHolder.quantityWater.setText(pro.getQuantityWater());
//        viewHolder.priceWater.setText(pro.getPriceWater());
//        viewHolder.image.setText(pro.getImage());
        viewHolder.tshirt.setChecked(pro.isTshirt());
//        viewHolder.detail.setText(pro.getDetail());
//        viewHolder.createBy.setText(pro.getCreateBy());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://datn-2021.herokuapp.com/api/pitch/user/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestAPI requestAPI = retrofit.create(RequestAPI.class);

        viewHolder.btn_xacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<PitchClass> call = requestAPI.updatePitch(_id,"1");
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
                        Log.e("loi", "adad");
                    }
                });
            }
        });
        viewHolder.btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<PitchClass> call = requestAPI.updatePitch(_id,"1");
                call.enqueue(new Callback<PitchClass>() {
                    @Override
                    public void onResponse(Call<PitchClass> call, Response<PitchClass> response) {
                        Toast.makeText(context, "Hủy thành công", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(Call<PitchClass> call, Throwable t) {

                    }
                });
            }
        });
        return view;
    }

    private class ViewHolder {
        TextView pitchID;
        TextView pitchName;
        TextView span;
        TextView userID;
        TextView date;
        TextView state;
        TextView totalPrice;
        TextView price;
        CheckBox umpire;
        TextView quantityWater;
        TextView priceWater;
        TextView image;
        CheckBox tshirt;
        TextView detail;
        TextView createBy;
        Button btn_xacNhan;
        Button btn_huy;
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
