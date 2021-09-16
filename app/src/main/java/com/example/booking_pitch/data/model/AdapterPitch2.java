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
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.booking_pitch.R;
import com.example.booking_pitch.data.repository.RequestAPI;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.security.AccessController.getContext;

public class AdapterPitch2 extends BaseAdapter {
    public Context context;
    PitchClass pitchClass;
    public List<PitchClass> pitchClassList;
    List<String> all_date = new ArrayList<>();
    public AdapterPitch2(Context context, List<PitchClass> pitchClassList) {
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
        view = LayoutInflater.from(context).inflate(R.layout.get_all_pitch2, viewGroup, false);
        viewHolder = new ViewHolder();
        viewHolder.pitchName = view.findViewById(R.id.tv_pitchName2);
        viewHolder.userID = view.findViewById(R.id.tv_userID2);
        viewHolder.date = view.findViewById(R.id.tv_date2);
        viewHolder.totalPrice = view.findViewById(R.id.tv_totalPrice2);
        viewHolder.umpire = view.findViewById(R.id.umpire_confim2);
        viewHolder.tshirt = view.findViewById(R.id.tshirt_comfim2);
        viewHolder.span= view.findViewById(R.id.tv_hour2);
        viewHolder.water = view.findViewById(R.id.tv_water2);
        viewHolder.userName = view.findViewById(R.id.tv_userName2);
        String _id = pro.get_id();
        String date = pro.getDate();

        if (pro.getCodeSpecial().isEmpty()){
            String day = date.substring(0,2);
            String month = date.substring(2,4);
            String year = date.substring(4,8);
            viewHolder.date.setText("Ngày: "+day +"-"+month+"-"+year);
        }else {
            String date3="";
            List<String> many_date = new ArrayList<>(Arrays.asList(date.split("/")));
            Log.e("date",many_date+"");
            for (String date5 : many_date){
                String day = date5.substring(0,2);
                String month = date5.substring(2,4);
                String year = date5.substring(4,8);
                String date6 = ", "+day +"-" +month +"-"+year+", ";
                date3 += date6;
                Log.e("date",date5+"");
            }
            viewHolder.date.setText("15/09/2021"+date3);
        }

        if (!pro.getTotalPrice().equals("")){
            viewHolder.totalPrice.setText("Giá: "+numberMoney(pro.getTotalPrice())+" VND");
        }
        if (pro.getSpan().equals("1")){
            viewHolder.span.setText("Ca 1: 7:00 - 9:00");
        }else if (pro.getSpan().equals("2")){
            viewHolder.span.setText("Ca 2: 9:30 - 11:30");
        }else if (pro.getSpan().equals("3")){
            viewHolder.span.setText("Ca 3: 13:30 - 15:30");
        }else if (pro.getSpan().equals("4")){
            viewHolder.span.setText("Ca 4: 16:00 - 18:00");
        }else if (pro.getSpan().equals("5")){
            viewHolder.span.setText("Ca 5: 19:00 - 21:00");
        }
        viewHolder.water.setText(pro.getQuantityWater()+" Bình");
        viewHolder.userName.setText("Người đặt: "+pro.getUserName());
        viewHolder.pitchName.setText(pro.getPitchName());
        viewHolder.umpire.setChecked(pro.isUmpire());
        viewHolder.userID.setText(pro.getUserID());
        viewHolder.tshirt.setChecked(pro.isTshirt());
        viewHolder.userID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+pro.getUserID()));
                context.startActivity(intent);
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
        CheckBox umpire;
        CheckBox tshirt;
        TextView water;
        TextView userName;
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
