package com.example.booking_pitch.Admin.quanLyLich;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.booking_pitch.R;
import com.example.booking_pitch.data.model.AdapterPitch;
import com.example.booking_pitch.data.model.PitchClass;
import com.example.booking_pitch.data.repository.RequestAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChoXacNhan extends Fragment {
    ListView lv_choXacNhan;
    AdapterPitch adapterPitch;
    List<PitchClass> pitchClassList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cho_xac_nhan, container, false);

        lv_choXacNhan = view.findViewById(R.id.lv_choXacNhan);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://datn-2021.herokuapp.com/api/pitch/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestAPI requestAPI = retrofit.create(RequestAPI.class);
        Call<List<PitchClass>> callAllUser = requestAPI.getAllA0();
        callAllUser.enqueue(new Callback<List<PitchClass>>() {
            @Override
            public void onResponse(Call<List<PitchClass>> call, Response<List<PitchClass>> response) {
                List<PitchClass> pitchClass = response.body();
                pitchClassList = new ArrayList<>(pitchClass);
                if (pitchClassList.size() == 0){
                   TextView koco_mang = view.findViewById(R.id.koco_mang);
                   koco_mang.setText("Chưa có sân được đặt");
                }else {
                    adapterPitch = new AdapterPitch(getContext(),pitchClassList);
                    lv_choXacNhan.setAdapter(adapterPitch);
                }

            }
            @Override
            public void onFailure(Call<List<PitchClass>> call, Throwable t) {

            }
        });
        return view;
    }
}