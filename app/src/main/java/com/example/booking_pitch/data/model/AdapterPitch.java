package com.example.booking_pitch.data.model;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import com.example.booking_pitch.InsertPitch;
import com.example.booking_pitch.Login.LoginActivity;
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

public class AdapterPitch extends BaseAdapter {
    public Context context;
    ProgressDialog progressDialog;
    PitchClass pitchClass;
    public List<PitchClass> pitchClassList;
    List<String> all_date = new ArrayList<>();
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
            if (pro.getCodeSpecial().isEmpty()){
                String day = date.substring(0,2);
                String month = date.substring(2,4);
                String year = date.substring(4,8);
                viewHolder.date.setText("Ng??y: "+day +"-"+month+"-"+year);
            }else {
                String date3="";
                List<String> many_date = new ArrayList<>(Arrays.asList(date.split("/")));
                Log.e("date",many_date+"");
                for (int y=0; y<many_date.size();y++){
                    String day = many_date.get(y).substring(0,2);
                    String month = many_date.get(y).substring(2,4);
                    String year = many_date.get(y).substring(4,8);
                    String date6 = "";
                    if ( y == many_date.size()-1 ){
                        date6 = day +"/" +month +"/"+year;
                    }else {
                        date6 = day +"/" +month +"/"+year+", ";
                    }
                    date3 += date6;
                }
                viewHolder.date.setText(date3);
            }

            if (!pro.getTotalPrice().equals("")){
                viewHolder.totalPrice.setText("Gi??: "+numberMoney(pro.getTotalPrice())+" VND");
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
            Glide.with(context)
                    .load("https://datn-2021.herokuapp.com"+pro.getImage())
                    .into(viewHolder.img);
            viewHolder.water.setText(pro.getQuantityWater()+" B??nh");
            viewHolder.userName.setText("Ng?????i ?????t: "+pro.getUserName());
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("X??c nh???n l???ch ?????t: " +pro.getPitchName()+" - "+ viewHolder.span.getText().toString())
                            .setNegativeButton("?????ng ??", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    progressDialog = new ProgressDialog(context);
                                    progressDialog.setMessage("??ang x??c nh???n...!");
                                    progressDialog.show();
                                    if (pro.getDayOfWeek().isEmpty()){
                                        Call<PitchClass> call = requestAPI.updatePitch(_id,"1","one","admin");
                                        call.enqueue(new Callback<PitchClass>() {
                                            @Override
                                            public void onResponse(Call<PitchClass> call, Response<PitchClass> response) {
                                                PitchClass pitchClass = response.body();
                                                for (int i = 0 ; i< pitchClassList.size(); i++){
                                                    if (pitchClassList.get(i).get_id() == _id){
                                                        Log.d("t","ok"+ _id);
                                                        pitchClassList.remove(i);
                                                        setDatachange(pitchClassList);
                                                        progressDialog.cancel();
                                                    }
                                                }
                                                Toast.makeText(context,"X??c nh???n th??nh c??ng", Toast.LENGTH_SHORT).show();
                                            }
                                            @Override
                                            public void onFailure(Call<PitchClass> call, Throwable t) {
                                                Toast.makeText(context, "X??c nh???n th???t b???i", Toast.LENGTH_SHORT).show();
                                                Log.e("loi", "one");
                                                progressDialog.cancel();
                                            }
                                        });
                                    }else {
                                        Call<PitchClass> call = requestAPI.updatePitch(pro.getCodeSpecial(),"1","many","admin");
                                        call.enqueue(new Callback<PitchClass>() {
                                            @Override
                                            public void onResponse(Call<PitchClass> call, Response<PitchClass> response) {
                                                PitchClass pitchClass = response.body();
                                                for (int i = 0 ; i< pitchClassList.size(); i++){
                                                    if (pitchClassList.get(i).get_id() == _id){
                                                        Log.d("t","ok"+ _id);
                                                        pitchClassList.remove(i);
                                                        setDatachange(pitchClassList);
                                                        progressDialog.cancel();
                                                    }
                                                }
                                                Toast.makeText(context, "X??c nh???n th??nh c??ng", Toast.LENGTH_SHORT).show();
                                            }
                                            @Override
                                            public void onFailure(Call<PitchClass> call, Throwable t) {
                                                Toast.makeText(context, "X??c nh???n th???t b???i", Toast.LENGTH_SHORT).show();
                                                Log.e("loi", "many");
                                                progressDialog.cancel();
                                            }
                                        });
                                    }
                                }
                            })
                            .setPositiveButton("Tr??? v???", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    builder.create().show();
                }
            });
            viewHolder.btn_huy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("H???y l???ch ?????t: " +pro.getPitchName()+" - "+ viewHolder.span.getText().toString())
                            .setNegativeButton("?????ng ??", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    progressDialog = new ProgressDialog(context);
                                    progressDialog.setMessage("??ang h???y l???ch...!");
                                    progressDialog.show();
                                    if (pro.getDayOfWeek().isEmpty()){
                                        Call<PitchClass> call = requestAPI.updatePitch(_id,"-1","one","admin");
                                        call.enqueue(new Callback<PitchClass>() {
                                            @Override
                                            public void onResponse(Call<PitchClass> call, Response<PitchClass> response) {
                                                PitchClass pitchClass = response.body();
                                                for (int i = 0 ; i< pitchClassList.size(); i++){
                                                    if (pitchClassList.get(i).get_id() == _id){
                                                        Log.d("t","ok"+ _id);
                                                        pitchClassList.remove(i);
                                                        setDatachange(pitchClassList);
                                                        progressDialog.cancel();
                                                    }
                                                }
                                                Toast.makeText(context, "H???y th??nh c??ng", Toast.LENGTH_SHORT).show();
                                            }
                                            @Override
                                            public void onFailure(Call<PitchClass> call, Throwable t) {
                                                progressDialog.cancel();
                                            }
                                        });
                                    }else {
                                        Call<PitchClass> call = requestAPI.updatePitch(pro.getCodeSpecial(),"-1","many","admin");
                                        call.enqueue(new Callback<PitchClass>() {
                                            @Override
                                            public void onResponse(Call<PitchClass> call, Response<PitchClass> response) {
                                                PitchClass pitchClass = response.body();
                                                for (int i = 0 ; i< pitchClassList.size(); i++){
                                                    if (pitchClassList.get(i).getCodeSpecial() == pro.getCodeSpecial()){
                                                        Log.d("t","ok"+ pro.getCodeSpecial());
                                                        pitchClassList.remove(i);
                                                        setDatachange(pitchClassList);
                                                        progressDialog.cancel();
                                                    }
                                                }
                                                Toast.makeText(context, "H???y th??nh c??ng", Toast.LENGTH_SHORT).show();
                                            }
                                            @Override
                                            public void onFailure(Call<PitchClass> call, Throwable t) {
                                                progressDialog.cancel();
                                            }
                                        });
                                    }
                                }
                            })
                            .setPositiveButton("Tr??? v???", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.create().show();
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
        ListView lv_date;
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
