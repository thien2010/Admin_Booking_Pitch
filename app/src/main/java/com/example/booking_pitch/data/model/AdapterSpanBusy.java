package com.example.booking_pitch.data.model;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.booking_pitch.Admin.PitchActivity;
import com.example.booking_pitch.Admin.admin_home.AdminFragment_home;
import com.example.booking_pitch.R;
import com.example.booking_pitch.data.repository.RequestAPI;
import com.example.booking_pitch.data.repository.ResponeGetDetailBusy;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdapterSpanBusy extends RecyclerView.Adapter<AdapterSpanBusy.SpanBusy>{
    Context context;
    List<SpanBusyClass> allBusyList;
    List<PitchClass> pitchClassList;
    ProgressDialog progressDialog;
    public AdapterSpanBusy(Context context, List<SpanBusyClass> allBusyList) {
        this.context = context;
        this.allBusyList = allBusyList;
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public SpanBusy onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_pitch_all_busy,parent,false);
        return new SpanBusy(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SpanBusy holder, int position) {
        SpanBusyClass spanBusyClass = allBusyList.get(position);
        if (spanBusyClass == null){
            return;
        }
        holder.pitch_name.setText(spanBusyClass.getPitchName());
        holder.span1.setBackgroundResource(R.drawable.all_busy);
        holder.span2.setBackgroundResource(R.drawable.all_busy);
        holder.span3.setBackgroundResource(R.drawable.all_busy);
        holder.span4.setBackgroundResource(R.drawable.all_busy);
        holder.span5.setBackgroundResource(R.drawable.all_busy);






        for (int i = 0; i < spanBusyClass.getSpanBusy().size() ; i++){
            if (spanBusyClass.getSpanBusy().get(i).equals("1")) {
                holder.span1.setBackgroundResource(R.drawable.red);
                holder.span1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressDialog = new ProgressDialog(context);
                        progressDialog.setMessage("Đang tải...!");
                        progressDialog.show();
                        String pitch_name = spanBusyClass.getPitchName();
                        String date1 = spanBusyClass.getDate();
                        RequestBody name = RequestBody.create(MediaType.parse("multipart/form-data"),pitch_name);
                        RequestBody date = RequestBody.create(MediaType.parse("multipart/form-data"),date1);
                        RequestBody span = RequestBody.create(MediaType.parse("multipart/form-data"),"1");
                        getDetailBusy(name,date,span);
                    }
                });
            }else if (spanBusyClass.getSpanBusy().get(i).equals("2")) {
                holder.span2.setBackgroundResource(R.drawable.red);
                holder.span2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressDialog = new ProgressDialog(context);
                        progressDialog.setMessage("Đang tải...!");
                        progressDialog.show();
                        String pitch_name = spanBusyClass.getPitchName();
                        String date1 = spanBusyClass.getDate();
                        RequestBody name = RequestBody.create(MediaType.parse("multipart/form-data"),pitch_name);
                        RequestBody date = RequestBody.create(MediaType.parse("multipart/form-data"),date1);
                        RequestBody span = RequestBody.create(MediaType.parse("multipart/form-data"),"2");
                        getDetailBusy(name,date,span);
                    }
                });
            }else if (spanBusyClass.getSpanBusy().get(i).equals("3")) {
                holder.span3.setBackgroundResource(R.drawable.red);
                holder.span3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressDialog = new ProgressDialog(context);
                        progressDialog.setMessage("Đang tải...!");
                        progressDialog.show();
                        String pitch_name = spanBusyClass.getPitchName();
                        String date1 = spanBusyClass.getDate();
                        RequestBody name = RequestBody.create(MediaType.parse("multipart/form-data"),pitch_name);
                        RequestBody date = RequestBody.create(MediaType.parse("multipart/form-data"),date1);
                        RequestBody span = RequestBody.create(MediaType.parse("multipart/form-data"),"3");
                        getDetailBusy(name,date,span);
                    }
                });
            }else if (spanBusyClass.getSpanBusy().get(i).equals("4")) {
                holder.span4.setBackgroundResource(R.drawable.red);
                holder.span4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressDialog = new ProgressDialog(context);
                        progressDialog.setMessage("Đang tải...!");
                        progressDialog.show();
                        String pitch_name = spanBusyClass.getPitchName();
                        String date1 = spanBusyClass.getDate();
                        RequestBody name = RequestBody.create(MediaType.parse("multipart/form-data"),pitch_name);
                        RequestBody date = RequestBody.create(MediaType.parse("multipart/form-data"),date1);
                        RequestBody span = RequestBody.create(MediaType.parse("multipart/form-data"),"4");
                        getDetailBusy(name,date,span);
                    }
                });
            }else if (spanBusyClass.getSpanBusy().get(i).equals("5")) {
                holder.span5.setBackgroundResource(R.drawable.red);
                holder.span5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressDialog = new ProgressDialog(context);
                        progressDialog.setMessage("Đang tải...!");
                        progressDialog.show();
                        String pitch_name = spanBusyClass.getPitchName();
                        String date1 = spanBusyClass.getDate();
                        RequestBody name = RequestBody.create(MediaType.parse("multipart/form-data"),pitch_name);
                        RequestBody date = RequestBody.create(MediaType.parse("multipart/form-data"),date1);
                        RequestBody span = RequestBody.create(MediaType.parse("multipart/form-data"),"5");
                        getDetailBusy(name,date,span);
                    }
                });
            }
        }
    }
    @Override
    public int getItemCount() {
        if (allBusyList!=null){
            return allBusyList.size();
        }
        return 0;
    }

    public class SpanBusy extends RecyclerView.ViewHolder{
        TextView pitch_name ;
        TextView span1,span2,span3,span4,span5 ;
        RecyclerView recyclerView;
        public SpanBusy(@NonNull @NotNull View itemView) {
            super(itemView);
            pitch_name = itemView.findViewById(R.id.pitch_name_busy);
            span1 = itemView.findViewById(R.id.ca1);
            span2 = itemView.findViewById(R.id.ca2);
            span3 = itemView.findViewById(R.id.ca3);
            span4 = itemView.findViewById(R.id.ca4);
            span5 = itemView.findViewById(R.id.ca5);
            recyclerView = itemView.findViewById(R.id.rcv_spanbusy1);
        }
    }
    public void getDetailBusy(RequestBody pitchName, RequestBody date, RequestBody span){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://datn-2021.herokuapp.com/api/pitch/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestAPI requestAPI = retrofit.create(RequestAPI.class);
        Call<ResponeGetDetailBusy> call = requestAPI.getDetailBusy(pitchName, date, span);
        call.enqueue(new Callback<ResponeGetDetailBusy>() {
            @Override
            public void onResponse(Call<ResponeGetDetailBusy> call, Response<ResponeGetDetailBusy> response) {
                ResponeGetDetailBusy pitchClass = response.body();
                if (!pitchClass.isSuccess() == false){
                    pitchClassList = new ArrayList<>(Arrays.asList(pitchClass.getData()));
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    View view = LayoutInflater.from(context).inflate(R.layout.get_detail_busy, null);
                    builder.setView(view);
                    TextView pitchName = view.findViewById(R.id.tv_pitchName3);
                    TextView userID = view.findViewById(R.id.tv_userID3);
                    TextView date = view.findViewById(R.id.tv_date3);
                    TextView totalPrice = view.findViewById(R.id.tv_totalPrice3);
                    CheckBox umpire = view.findViewById(R.id.umpire_confim3);
                    CheckBox tshirt = view.findViewById(R.id.tshirt_comfim3);
                    TextView warter = view.findViewById(R.id.tv_water3);
                    ImageView img = view.findViewById(R.id.img_ctSan3);
                    TextView span = view.findViewById(R.id.tv_hour3);
                    TextView userName = view.findViewById(R.id.tv_userName3);
                    TextView state = view.findViewById(R.id.tv_state);

                    pitchName.setText(pitchClassList.get(0).getPitchName());
                    userID.setText(pitchClassList.get(0).getUserID());
                    userID.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel:"+pitchClassList.get(0).getUserID()));
                            context.startActivity(intent);
                        }
                    });

                    String state_1 = pitchClassList.get(0).getState();
                    if (state_1.equals("0")){
                        state.setText("Đang chờ xác nhận");
                    }else if (state_1.equals("1")){
                        state.setText("Đã xác nhận");
                    }
                    String date1 = pitchClassList.get(0).getDate();
                    String day = date1.substring(0,2);
                    String month = date1.substring(2,4);
                    String year = date1.substring(4,8);
                    date.setText("Ngày: "+day +"-"+month+"-"+year);

                    totalPrice.setText(numberMoney(pitchClassList.get(0).getTotalPrice())+" VND");
                    umpire.setChecked(pitchClassList.get(0).isUmpire());
                    tshirt.setChecked(pitchClassList.get(0).isTshirt());
                    warter.setText(pitchClassList.get(0).getQuantityWater() + " Bình");

                    if (pitchClassList.get(0).getSpan().equals("1")){
                        span.setText("Ca 1: 7:00 - 9:00");
                    }else if (pitchClassList.get(0).getSpan().equals("2")){
                        span.setText("Ca 2: 9:30 - 11:30");
                    }else if (pitchClassList.get(0).getSpan().equals("3")){
                        span.setText("Ca 3: 13:30 - 15:30");
                    }else if (pitchClassList.get(0).getSpan().equals("4")){
                        span.setText("Ca 4: 16:00 - 18:00");
                    }else if (pitchClassList.get(0).getSpan().equals("5")){
                        span.setText("Ca 5: 19:00 - 21:00");
                    }

                    userName.setText("Người đặt: "+pitchClassList.get(0).getUserName());
                    Glide.with(context)
                            .load("https://datn-2021.herokuapp.com"+pitchClassList.get(0).getImage())
                            .into(img);
                    progressDialog.cancel();
                    builder.create().show();
                }
            }
            @Override
            public void onFailure(Call<ResponeGetDetailBusy> call, Throwable t) {

            }
        });
    }
    public static String numberMoney(String number){
        DecimalFormat decimalFormat = new DecimalFormat("###,###,##0");
        return decimalFormat.format(Double.parseDouble(number));
    }
}
