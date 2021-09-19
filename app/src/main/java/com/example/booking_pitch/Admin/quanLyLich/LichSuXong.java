package com.example.booking_pitch.Admin.quanLyLich;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.booking_pitch.R;
import com.example.booking_pitch.data.model.AdapterPitch;
import com.example.booking_pitch.data.model.AdapterPitch2;
import com.example.booking_pitch.data.model.AdapterPitchConfim;
import com.example.booking_pitch.data.model.PitchClass;
import com.example.booking_pitch.data.repository.RequestAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LichSuXong extends Fragment {
    AdapterPitch2 adapterPitch2;
    ListView listView;
    List<PitchClass> pitchClassList;
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lich_su_xong, container, false);
        listView = view.findViewById(R.id.lv_pitch2);
        swipeRefreshLayout = view.findViewById(R.id.refreshLayout_3);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadData2();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        LoadData2();
        return view;
    }
    public void LoadData2(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://datn-2021.herokuapp.com/api/pitch/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestAPI requestAPI = retrofit.create(RequestAPI.class);
        Call<List<PitchClass>> callAllUser = requestAPI.getAllA2();
        callAllUser.enqueue(new Callback<List<PitchClass>>() {
            @Override
            public void onResponse(Call<List<PitchClass>> call, Response<List<PitchClass>> response) {
                List<PitchClass> pitchClass = response.body();
                if (pitchClass!=null){
                    pitchClassList = new ArrayList<>(pitchClass);
                    adapterPitch2 = new AdapterPitch2(getContext(),pitchClassList);
                    listView.setAdapter(adapterPitch2);
                }else {
                    Toast.makeText(getContext(), "Lịch sử trống", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<PitchClass>> call, Throwable t) {

            }
        });
    }
}