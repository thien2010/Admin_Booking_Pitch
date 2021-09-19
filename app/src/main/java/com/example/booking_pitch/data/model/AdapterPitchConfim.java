package com.example.booking_pitch.data.model;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
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
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.booking_pitch.R;
import com.example.booking_pitch.data.repository.RequestAPI;
import com.example.booking_pitch.data.repository.ResponeCancel;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdapterPitchConfim extends BaseAdapter {
    public Context context;
    public List<PitchClass> pitchClassList;
    ProgressDialog progressDialog;
    String date_Candel = "";
    List<String> date_cancel = new ArrayList<>();
    List<String> date_cancel2 = new ArrayList<>();
    String date_1 = "";
    String date_2 = "";
    String date3="";
    String date_Done="";
    String date6;
    String userID, codeSpecial, dateDone,dateCancel;
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
                    .load("https://datn-2021.herokuapp.com"+pro.getImage())
                    .into(viewHolder.img);
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
            String state = pro.getState();
            String _id = pro.get_id();
            String date = pro.getDate();
            Log.e("TTT",date);
              if (pro.getCodeSpecial().isEmpty()){
                    String day = date.substring(0,2);
                    String month = date.substring(2,4);
                    String year = date.substring(4,8);
                    viewHolder.date.setText("Ngày: "+day +"-"+month+"-"+year);
                }else {

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
                viewHolder.totalPrice.setText("Giá: "+numberMoney(pro.getTotalPrice())+" VND");
            }
            userID = pro.getUserID();
            codeSpecial = pro.getCodeSpecial();
            viewHolder.userName.setText("Người đặt: "+pro.getUserName());
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Xác nhận lịch đặt: " +pro.getPitchName()+" - "+ viewHolder.span.getText().toString())
                            .setNegativeButton("Đồng ý", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    progressDialog = new ProgressDialog(context);
                                    progressDialog.setMessage("Đang xác nhận...!");
                                    progressDialog.show();
                                    if (pro.getDayOfWeek().isEmpty()){
                                        Call<PitchClass> call = requestAPI.updatePitch(_id,"2","one","admin");
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
                                                Toast.makeText(context, "Sân đã đá xong", Toast.LENGTH_SHORT).show();
                                            }
                                            @Override
                                            public void onFailure(Call<PitchClass> call, Throwable t) {
                                                Toast.makeText(context, "Xác nhận thất bại", Toast.LENGTH_SHORT).show();
                                                Log.e("loi", "adad");
                                                progressDialog.cancel();
                                            }
                                        });
                                    }else {
                                        Call<PitchClass> call = requestAPI.updatePitch(pro.getCodeSpecial(),"2","many","admin");
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
                                                Toast.makeText(context, "Sân đã đá xong", Toast.LENGTH_SHORT).show();
                                            }
                                            @Override
                                            public void onFailure(Call<PitchClass> call, Throwable t) {
                                                Toast.makeText(context, "Xác nhận thất bại", Toast.LENGTH_SHORT).show();
                                                Log.e("loi", "adad");
                                                progressDialog.cancel();
                                            }
                                        });
                                    }
                                }
                            })
                            .setPositiveButton("Trở về", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    builder.create().show();
                }
            });
            viewHolder.btn_huy_dd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Hủy lịch đặt: " +pro.getPitchName()+" - "+ viewHolder.span.getText().toString())
                            .setNegativeButton("Đồng ý", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    progressDialog = new ProgressDialog(context);
                                    progressDialog.setMessage("Đang hủy lịch...!");
                                    progressDialog.show();
                                    if (pro.getDayOfWeek().isEmpty()){
                                        Call<PitchClass> call = requestAPI.updatePitch(_id,"3","one","admin");
                                        call.enqueue(new Callback<PitchClass>() {
                                            @Override
                                            public void onResponse(Call<PitchClass> call, Response<PitchClass> response) {
                                                for (int i = 0 ; i< pitchClassList.size(); i++){
                                                    if (pitchClassList.get(i).get_id() == _id){
                                                        Log.d("t","ok"+ _id);
                                                        pitchClassList.remove(i);
                                                        setDatachange(pitchClassList);
                                                        progressDialog.cancel();
                                                    }
                                                }
                                                Toast.makeText(context, "Hủy thành công", Toast.LENGTH_SHORT).show();
                                            }
                                            @Override
                                            public void onFailure(Call<PitchClass> call, Throwable t) {
                                                Toast.makeText(context, "Hủy thất bại", Toast.LENGTH_SHORT).show();
                                                Log.e("loi", "adad");
                                                progressDialog.cancel();
                                            }
                                        });
                                    }else {
                                        progressDialog.cancel();
                                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                        View view = LayoutInflater.from(context).inflate(R.layout.dialog_cancel, null);
                                        builder.setView(view);
                                        TextView tv_dateDone = view.findViewById(R.id.tv_dateDone);
                                        TextView tv_dateCancel = view.findViewById(R.id.tv_dateCancel);
                                        TextView all_date = view.findViewById(R.id.all_date);
                                        Button btn_cancel = view.findViewById(R.id.btn_cancel);
                                        Button btn_clear = view.findViewById(R.id.btn_clear);
                                        ImageView datepicker = view.findViewById(R.id.date_picker_cancel);
                                        ImageView datepicker2 = view.findViewById(R.id.date_picker_cancel2);
                                        all_date.setText(date3);

                                        datepicker.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                final Calendar calendar = Calendar.getInstance();
                                                int year = calendar.get(Calendar.YEAR);
                                                int month = calendar.get(Calendar.MONTH);
                                                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                                                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                                                    @Override
                                                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                                        calendar.set(year, month, dayOfMonth);
                                                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyyyy");
                                                        date_1 = simpleDateFormat.format(calendar.getTime());
                                                        date_cancel.add(date_1);
                                                        date6 = "";
                                                        date_Done = "";
                                                        for (int y=0; y<date_cancel.size();y++){
                                                            date_cancel.get(y);
                                                            if ( y == date_cancel.size()-1 ){
                                                                date6 = date_cancel.get(y);
                                                            }else {
                                                                date6 = date_cancel.get(y)+"/";
                                                            }
                                                            date_Done += date6;
                                                        }
                                                        tv_dateDone.setText(date_Done);
                                                        Log.e("date_cancel",date_Done);
                                                    }
                                                }, year, month, day);
                                                datePickerDialog.show();
                                            }
                                        });
                                        datepicker2.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                final Calendar calendar = Calendar.getInstance();
                                                int year = calendar.get(Calendar.YEAR);
                                                int month = calendar.get(Calendar.MONTH);
                                                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                                                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                                                    @Override
                                                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                                        calendar.set(year, month, dayOfMonth);
                                                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyyyy");
                                                        date_2 = simpleDateFormat.format(calendar.getTime());
                                                        date_cancel2.add(date_2);
                                                        date6 = "";
                                                        date_Candel = "";
                                                        for (int y=0; y<date_cancel2.size();y++){
                                                            date_cancel2.get(y);
                                                            if ( y == date_cancel2.size()-1 ){
                                                                date6 = date_cancel2.get(y);
                                                            }else {
                                                                date6 = date_cancel2.get(y)+"/";
                                                            }
                                                            date_Candel += date6;
                                                        }
                                                        tv_dateCancel.setText(date_Candel);
                                                    }
                                                }, year, month, day);
                                                datePickerDialog.show();
                                            }
                                        });
                                        btn_cancel.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                progressDialog = new ProgressDialog(context);
                                                progressDialog.setMessage("Đang hủy lịch...!");
                                                progressDialog.show();
                                                if (tv_dateDone.getText().toString().isEmpty() && tv_dateCancel.getText().toString().isEmpty()){
                                                    Toast.makeText(context, "Vui lòng chọn 1 trong 2 ngày trên", Toast.LENGTH_SHORT).show();
                                                }else {
                                                    dateDone = tv_dateDone.getText().toString().trim();
                                                    dateCancel = tv_dateCancel.getText().toString().trim();
                                                    RequestBody userId = RequestBody.create(MediaType.parse("multipart/form-data"),userID);
                                                    RequestBody codeSpecial1 = RequestBody.create(MediaType.parse("multipart/form-data"),codeSpecial);
                                                    RequestBody dateDone1 = RequestBody.create(MediaType.parse("multipart/form-data"),dateDone);
                                                    RequestBody dateCancel1 = RequestBody.create(MediaType.parse("multipart/form-data"),dateCancel);
                                                    Retrofit retrofit = new Retrofit.Builder()
                                                            .baseUrl("https://datn-2021.herokuapp.com/api/pitch/")
                                                            .addConverterFactory(GsonConverterFactory.create())
                                                            .build();
                                                    RequestAPI requestAPI = retrofit.create(RequestAPI.class);
                                                    Call<ResponeCancel> responeCancelCall = requestAPI.cancel(userId,codeSpecial1,dateDone1,dateCancel1);
                                                    responeCancelCall.enqueue(new Callback<ResponeCancel>() {
                                                        @Override
                                                        public void onResponse(Call<ResponeCancel> call, Response<ResponeCancel> response) {
                                                            ResponeCancel responeCancel = response.body();
                                                            if (responeCancel!=null){
                                                                Toast.makeText(context, responeCancel.getMessage(), Toast.LENGTH_SHORT).show();
                                                                for (int i = 0 ; i< pitchClassList.size(); i++){
                                                                    if (pitchClassList.get(i).get_id() == _id){
                                                                        Log.d("t","ok"+ _id);
                                                                        pitchClassList.remove(i);
                                                                        setDatachange(pitchClassList);
                                                                        progressDialog.cancel();
                                                                    }
                                                                }
                                                                dialog.dismiss();
                                                            }else {
                                                                Toast.makeText(context, responeCancel.getMessage(), Toast.LENGTH_SHORT).show();
                                                                dialog.dismiss();
                                                                progressDialog.cancel();
                                                            }
                                                        }

                                                        @Override
                                                        public void onFailure(Call<ResponeCancel> call, Throwable t) {

                                                        }
                                                    });
                                                }
                                            }
                                        });
                                        btn_clear.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                tv_dateDone.setText("");
                                                tv_dateCancel.setText("");
                                                for (int i = 0; i < date_cancel.size(); i++){
                                                    date_cancel.remove(i);
                                                }
                                                for (int i = 0; i < date_cancel2.size(); i++){
                                                    date_cancel2.remove(i);
                                                }
                                            }
                                        });
                                        builder.create().show();
//                                        Call<PitchClass> call = requestAPI.updatePitch(pro.getCodeSpecial(),"3","many","admin");
//                                        call.enqueue(new Callback<PitchClass>() {
//                                            @Override
//                                            public void onResponse(Call<PitchClass> call, Response<PitchClass> response) {
//                                                for (int i = 0 ; i< pitchClassList.size(); i++){
//                                                    if (pitchClassList.get(i).get_id() == _id){
//                                                        Log.d("t","ok"+ _id);
//                                                        pitchClassList.remove(i);
//                                                        setDatachange(pitchClassList);
//                                                        progressDialog.cancel();
//                                                    }
//                                                }
//                                                Toast.makeText(context, "Hủy thành công", Toast.LENGTH_SHORT).show();
//                                            }
//                                            @Override
//                                            public void onFailure(Call<PitchClass> call, Throwable t) {
//                                                Toast.makeText(context, "Hủy thất bại", Toast.LENGTH_SHORT).show();
//                                                Log.e("loi", "adad");
//                                                progressDialog.cancel();
//                                            }
//                                        });
                                    }
                                }
                            })
                            .setPositiveButton("Trở về", new DialogInterface.OnClickListener() {
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
