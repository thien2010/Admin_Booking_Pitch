package com.example.booking_pitch.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.booking_pitch.R;
import com.example.booking_pitch.data.model.AdapterRecyclerView;
import com.example.booking_pitch.data.model.PitchClass;
import com.example.booking_pitch.data.repository.ReponeAllSan;
import com.example.booking_pitch.data.repository.RequestAPI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AllPitchActivity extends AppCompatActivity {
    private AdapterRecyclerView adapterRecyclerView;
    private RecyclerView rcv_pitch;
    private List<PitchClass> pitchClassList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_pitch);
        rcv_pitch= findViewById(R.id.rcv_all_pitch);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        rcv_pitch.setLayoutManager(linearLayoutManager);
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
                adapterRecyclerView = new AdapterRecyclerView(AllPitchActivity.this,pitchClassList);
                rcv_pitch.setAdapter(adapterRecyclerView);
            }
            @Override
            public void onFailure(Call<ReponeAllSan> call, Throwable t) {

            }
        });
    }
}