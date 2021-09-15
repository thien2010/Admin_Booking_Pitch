package com.example.booking_pitch.Admin.admin_home;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.booking_pitch.InsertNews;
import com.example.booking_pitch.InsertPitch;
import com.example.booking_pitch.R;
import com.example.booking_pitch.ViewPager.Photo;
import com.example.booking_pitch.ViewPager.PhotoAdapter;
import com.example.booking_pitch.data.model.AdapterRecyclerNews;
import com.example.booking_pitch.data.model.AdapterRecyclerView;
import com.example.booking_pitch.data.model.AdapterSpanBusy;
import com.example.booking_pitch.data.model.News;
import com.example.booking_pitch.data.model.PitchClass;
import com.example.booking_pitch.data.repository.ReponeAllSan;
import com.example.booking_pitch.data.repository.RequestAPI;
import com.example.booking_pitch.data.model.SpanBusyClass;
import com.example.booking_pitch.data.repository.ResponeNews;
import com.example.booking_pitch.data.repository.ResponeSpanBusy;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AdminFragment_home extends Fragment {
    private Button date_picker;
    private LinearLayout linearLayout;
    private ViewPager viewPager;
    private PhotoAdapter photoAdapter;
    TextView add_pitch,add_new,today;
    private List<News> newsList;
    private AdapterRecyclerNews adapterRecyclerNews;
    private AdapterRecyclerView adapterRecyclerView;
    private AdapterSpanBusy adapterSpanBusy;
    private RecyclerView rcv_pitch, rcv_news,rcv_spanbusy;
    private List<PitchClass> pitchClassList;
    private List<SpanBusyClass> allBusyList;
    private CalendarView calendarView;
    String to_day;
    String date_piker;
    String date_1 = "";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_home, container, false);
//        viewPager = view.findViewById(R.id.banner);
//        calendarView = view.findViewById(R.id.calendarView);
        rcv_pitch = view.findViewById(R.id.rcv_get_all_pitch);
        rcv_news = view.findViewById(R.id.rcv_get_all_new);
//        photoAdapter = new PhotoAdapter(getContext(),getListPhoto());
        add_pitch = view.findViewById(R.id.addpitch);
        add_new = view.findViewById(R.id.addnews);
        today = view.findViewById(R.id.tv_today);
        rcv_spanbusy = view.findViewById(R.id.rcv_spanbusy1);
        date_picker = view.findViewById(R.id.datePickerButton);
        today.setText("Lịch Hôm Nay");
        getToday();
        Log.e("day",getTodaysDate());
        date_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyyyy");
                        date_piker = simpleDateFormat.format(calendar.getTime());
                        if (date_piker.equals(getTodaysDate())){
                            today.setText("Lịch hôm nay");
                        }else {
                            String day1 = date_piker.substring(0,2);
                            String month1 = date_piker.substring(2,4);
                            String year1 = date_piker.substring(4,8);
                            today.setText(day1+"-"+month1+"-"+year1);
                        }
                        getWithDay(date_piker);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        add_pitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), InsertPitch.class);
                startActivity(intent);
            }
        });
        add_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), InsertNews.class);
                startActivity(intent);
            }
        });
//        viewPager.setAdapter(photoAdapter);
        getPitch();
        getNews();
        return view;
    }
    private void getPitch(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://datn-2021.herokuapp.com/api/pitch/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestAPI requestAPI = retrofit.create(RequestAPI.class);
        Call<ReponeAllSan> call = requestAPI.getAllU();
        call.enqueue(new Callback<ReponeAllSan>() {
            @Override
            public void onResponse(Call<ReponeAllSan> call, Response<ReponeAllSan> response) {
                ReponeAllSan pitchClasses = response.body();
                pitchClassList = new ArrayList<>(Arrays.asList(pitchClasses.getData()));
                adapterRecyclerView = new AdapterRecyclerView(getContext(),pitchClassList);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
                rcv_pitch.setLayoutManager(linearLayoutManager);
                rcv_pitch.setAdapter(adapterRecyclerView);
            }
            @Override
            public void onFailure(Call<ReponeAllSan> call, Throwable t) {

            }
        });
    }
    private void getNews(){
        Retrofit retrofit1 = new Retrofit.Builder()
                .baseUrl("http://datn-2021.herokuapp.com/api/news/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestAPI requestAPI1 = retrofit1.create(RequestAPI.class);
        Call<ResponeNews> call1 = requestAPI1.getAllNew();
        call1.enqueue(new Callback<ResponeNews>() {
            @Override
            public void onResponse(Call<ResponeNews> call, Response<ResponeNews> response) {
                ResponeNews pitchClasses = response.body();
                newsList = new ArrayList<>(Arrays.asList(pitchClasses.getData()));
                adapterRecyclerNews = new AdapterRecyclerNews(getContext(),newsList);
                LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
                rcv_news.setLayoutManager(linearLayoutManager1);
                rcv_news.setAdapter(adapterRecyclerNews);
            }
            @Override
            public void onFailure(Call<ResponeNews> call, Throwable t) {

            }
        });
    }
    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        if (month <10){
            int day = cal.get(Calendar.DAY_OF_MONTH);
            return makeDateString1(day, "0"+month, year);
        }else {
            int day = cal.get(Calendar.DAY_OF_MONTH);
            return makeDateString(day, month, year);
        }

    }
    public void getToday(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://datn-2021.herokuapp.com/api/pitch/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestAPI requestAPI = retrofit.create(RequestAPI.class);
        Call<ResponeSpanBusy> call = requestAPI.getAllBusy(getTodaysDate());
        call.enqueue(new Callback<ResponeSpanBusy>() {
            @Override
            public void onResponse(Call<ResponeSpanBusy> call, Response<ResponeSpanBusy> response) {
                ResponeSpanBusy responeAllBusy = response.body();
                allBusyList = new ArrayList<>(Arrays.asList(responeAllBusy.getData()));
                if (responeAllBusy.isSuccess()==true){
                    for (int i =0 ; i<=allBusyList.size(); i++){
                        adapterSpanBusy = new AdapterSpanBusy(getContext(), allBusyList);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
                        rcv_spanbusy.setLayoutManager(linearLayoutManager);
                        rcv_spanbusy.setAdapter(adapterSpanBusy);
                    }
                    Log.e("EEE", allBusyList.get(0).getSpanBusy()+"-"+getTodaysDate());
                }
            }
            @Override
            public void onFailure(Call<ResponeSpanBusy> call, Throwable t) {

            }
        });
    }
    public void getWithDay(String day){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://datn-2021.herokuapp.com/api/pitch/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestAPI requestAPI = retrofit.create(RequestAPI.class);
        Call<ResponeSpanBusy> call = requestAPI.getAllBusy(day);
        call.enqueue(new Callback<ResponeSpanBusy>() {
            @Override
            public void onResponse(Call<ResponeSpanBusy> call, Response<ResponeSpanBusy> response) {
                ResponeSpanBusy responeAllBusy = response.body();
                allBusyList = new ArrayList<>(Arrays.asList(responeAllBusy.getData()));
                if (responeAllBusy.isSuccess()==true){
                    for (int i =0 ; i<=allBusyList.size(); i++){
                        adapterSpanBusy = new AdapterSpanBusy(getContext(), allBusyList);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
                        rcv_spanbusy.setLayoutManager(linearLayoutManager);
                        rcv_spanbusy.setAdapter(adapterSpanBusy);
                    }
                    Log.e("EEE", allBusyList.get(0).getSpanBusy()+"-"+getTodaysDate());
                }
            }
            @Override
            public void onFailure(Call<ResponeSpanBusy> call, Throwable t) {

            }
        });
    }
    private String makeDateString1(int day, String month, int year)
    {
        return  day+ "" + month+ year;
    }
    private String makeDateString(int day, int month, int year)
    {
        return  day+ "" + month+ year;
    }
}