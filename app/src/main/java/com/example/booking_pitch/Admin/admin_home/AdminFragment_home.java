package com.example.booking_pitch.Admin.admin_home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.booking_pitch.R;
import com.example.booking_pitch.ViewPager.Photo;
import com.example.booking_pitch.ViewPager.PhotoAdapter;
import com.example.booking_pitch.data.model.AdapterRecyclerNews;
import com.example.booking_pitch.data.model.AdapterRecyclerView;
import com.example.booking_pitch.data.model.News;
import com.example.booking_pitch.data.model.PitchClass;
import com.example.booking_pitch.data.repository.ReponeAllSan;
import com.example.booking_pitch.data.repository.RequestAPI;
import com.example.booking_pitch.data.repository.ResponeNews;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AdminFragment_home extends Fragment {

    private LinearLayout linearLayout;
    private ViewPager viewPager;
    private PhotoAdapter photoAdapter;
    private List<News> newsList;
    private AdapterRecyclerNews adapterRecyclerNews;
    private AdapterRecyclerView adapterRecyclerView;
    private RecyclerView rcv_pitch, rcv_news;
    private List<PitchClass> pitchClassList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_home, container, false);
        viewPager = view.findViewById(R.id.banner);
        rcv_pitch = view.findViewById(R.id.rcv_get_all_pitch);
        rcv_news = view.findViewById(R.id.rcv_get_all_new);
        photoAdapter = new PhotoAdapter(getContext(),getListPhoto());
        viewPager.setAdapter(photoAdapter);
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
    private List<Photo> getListPhoto(){
        List<Photo> list = new ArrayList<>();
        list.add(new Photo(R.drawable.banner2));
        list.add(new Photo(R.drawable.banner3));
        list.add(new Photo(R.drawable.banner1));
        return list;
    }
}