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
            Glide.with(context)
                    .load(pro.getImage())
                    .into(viewHolder.img);
            String state = pro.getState();
            String _id = pro.get_id();
            String date = pro.getDate();
            String day = date.substring(0,2);
            String month = date.substring(2,4);
            String year = date.substring(4,8);
            if (!pro.getTotalPrice().equals("")){
                viewHolder.totalPrice.setText("Giá: "+numberMoney(pro.getTotalPrice())+" VND");
            }
            viewHolder.userName.setText("Người đặt: "+pro.getUserName());
            viewHolder.span.setText(pro.getSpan());
            viewHolder.warter.setText(pro.getQuantityWater()+ " Bình");
            viewHolder.userID.setText(pro.getUserID());
            viewHolder.pitchName.setText(pro.getPitchName());
            viewHolder.date.setText("Ngày: "+day + "-" +month + "-"+year);
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
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://datn-2021.herokuapp.com/api/pitch/user/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    RequestAPI requestAPI = retrofit.create(RequestAPI.class);

                    Call<PitchClass> call = requestAPI.updatePitch(_id,"2");
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
                            Log.e("loi", "adad");
                        }
                    });
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
