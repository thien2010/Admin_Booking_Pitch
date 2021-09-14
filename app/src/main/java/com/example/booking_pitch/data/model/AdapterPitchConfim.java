package com.example.booking_pitch.data.model;

import android.content.Context;
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
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdapterPitchConfim extends BaseAdapter {
    public Context context;
    public List<PitchClass> pitchClassList;

    public AdapterPitchConfim(Context context, List<PitchClass> pitchClassList) {
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
            view = LayoutInflater.from(context).inflate(R.layout.pitch_get_all_xac_nhan, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.userID = view.findViewById(R.id.tv_userName);
            viewHolder.btn_confim = view.findViewById(R.id.btn_done);
            viewHolder.pitchName = view.findViewById(R.id.tv_pitchName);
            viewHolder.date = view.findViewById(R.id.tv_date);
            viewHolder.totalPrice = view.findViewById(R.id.tv_totalPrice);
            viewHolder.umpire = view.findViewById(R.id.umpire_confim);
            viewHolder.tshirt = view.findViewById(R.id.tshirt_comfim);
            viewHolder.warter = view.findViewById(R.id.tv_water_confim);
            viewHolder.span = view.findViewById(R.id.tv_hour);
            viewHolder.img = view.findViewById(R.id.img_ctSan1);
            viewHolder.userName = view.findViewById(R.id.userName_0);
            viewHolder.btn_huy_dd = view.findViewById(R.id.btn_huy_dd);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://datn-2021.herokuapp.com/api/pitch/user/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            RequestAPI requestAPI = retrofit.create(RequestAPI.class);
            Glide.with(context)
                    .load(pro.getImage())
                    .into(viewHolder.img);
            String state = pro.getState();
            String _id = pro.get_id();
            String date = pro.getDate();
            Log.e("TTT",date);
            String many_date[] = date.split("/");
//            for (String d : many_date){
//                String day = d.substring(0,2);
//                String month = d.substring(2,4);
//                String year = d.substring(4,8);
//                viewHolder.date.setText("Ngày: "+day + "-" +month + "-"+year);
//                Log.e("TTT","Ngày: "+day + "-" +month + "-"+year);
//                viewHolder.date.setText(d);
//            }
            viewHolder.date.setText("Ngày: "+date);
            if (!pro.getTotalPrice().equals("")){
                viewHolder.totalPrice.setText("Giá: "+numberMoney(pro.getTotalPrice())+" VND");
            }
            viewHolder.userName.setText("Người đặt: "+pro.getUserName());
            viewHolder.span.setText(pro.getSpan());
            viewHolder.warter.setText(pro.getQuantityWater()+ " Bình");
            viewHolder.userID.setText(pro.getUserID());
            viewHolder.pitchName.setText(pro.getPitchName());
            viewHolder.tshirt.setChecked(pro.isTshirt());
            viewHolder.umpire.setChecked(pro.isUmpire());
            viewHolder.userID.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+pro.getUserID()));
                    context.startActivity(intent);
                }
            });
            viewHolder.btn_confim.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (pro.getDayOfWeek().isEmpty()){
                        Call<PitchClass> call = requestAPI.updatePitch(_id,"2","one");
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
                    }else {
                        Call<PitchClass> call = requestAPI.updatePitch(pro.getCodeSpecial(),"2","many");
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
                }
            });
            viewHolder.btn_huy_dd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (pro.getDayOfWeek().isEmpty()){
                        Call<PitchClass> call = requestAPI.updatePitch(_id,"3","one");
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
                                Toast.makeText(context, "Hủy thất bại", Toast.LENGTH_SHORT).show();
                                Log.e("loi", "adad");
                            }
                        });
                    }else {
                        Call<PitchClass> call = requestAPI.updatePitch(pro.getCodeSpecial(),"3","many");
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
                                Toast.makeText(context, "Hủy thất bại", Toast.LENGTH_SHORT).show();
                                Log.e("loi", "adad");
                            }
                        });
                    }
                }
            });
        return view;
    }

    private class ViewHolder {
        TextView pitchName;
        TextView userID;
        TextView date;
        TextView totalPrice;
        CheckBox umpire;
        CheckBox tshirt;
        TextView warter;
        ImageView img;
        Button btn_confim;
        Button btn_huy_dd;
        TextView span;
        TextView userName;
    }
    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
    public static String numberMoney(String number){
        DecimalFormat decimalFormat = new DecimalFormat("###,###,##0");
        return decimalFormat.format(Double.parseDouble(number));
    }
    public void setDatachange(List<PitchClass> items) {
        this.pitchClassList = items;
        notifyDataSetChanged();
    }
}
