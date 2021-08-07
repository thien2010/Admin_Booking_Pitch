package com.example.booking_pitch.Admin.quanLyLich;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.booking_pitch.R;
import com.example.booking_pitch.data.model.AdapterPitch;
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


public class DaDat_admin extends Fragment {
    ListView lv_confim;
    AdapterPitchConfim adapterPitch;
    List<PitchClass> pitchClassList;
    ProgressDialog progressDialog;
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_da_dat_admin, container, false);
        swipeRefreshLayout = view.findViewById(R.id.refreshLayout_2);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading..., please wait!");
        progressDialog.show();
        lv_confim = view.findViewById(R.id.lv_confim);
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
        Call<List<PitchClass>> callAllUser = requestAPI.getAllA1();
        callAllUser.enqueue(new Callback<List<PitchClass>>() {
            @Override
            public void onResponse(Call<List<PitchClass>> call, Response<List<PitchClass>> response) {
                List<PitchClass> pitchClass = response.body();
                pitchClassList = new ArrayList<>(pitchClass);
                adapterPitch = new AdapterPitchConfim(getContext(),pitchClassList);
                lv_confim.setAdapter(adapterPitch);
                progressDialog.cancel();

            }
            @Override
            public void onFailure(Call<List<PitchClass>> call, Throwable t) {

            }
        });
    }
}