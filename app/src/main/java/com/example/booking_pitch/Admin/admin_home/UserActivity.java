package com.example.booking_pitch.Admin.admin_home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.booking_pitch.R;
import com.example.booking_pitch.data.model.AdapterAllUser;
import com.example.booking_pitch.data.model.Users;
import com.example.booking_pitch.data.repository.RequestAPI;
import com.example.booking_pitch.data.repository.ResponeAllUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserActivity extends AppCompatActivity {

    AdapterAllUser adapterAllUser;
    List<Users> usersList;
    RecyclerView recyclerView;
    TextView all_user;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://datn-2021.herokuapp.com/api/user/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    RequestAPI requestAPI = retrofit.create(RequestAPI.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        all_user = findViewById(R.id.all_user);
        recyclerView = findViewById(R.id.rcv_alluser);
        LoadUser();
    }
    public void LoadUser(){
        Call<ResponeAllUser> call = requestAPI.getAllUser();
        call.enqueue(new Callback<ResponeAllUser>() {
            @Override
            public void onResponse(Call<ResponeAllUser> call, Response<ResponeAllUser> response) {
                ResponeAllUser responeAllUser = response.body();
                if(responeAllUser!=null){
                    if (responeAllUser.isSuccess()==true){
                        all_user.setText("Số lượng thành viên: "+responeAllUser.getQuantityUser());
                        usersList = new ArrayList<>(Arrays.asList(responeAllUser.getData()));
                        adapterAllUser = new AdapterAllUser(UserActivity.this,usersList);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(UserActivity.this,RecyclerView.VERTICAL,false);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setAdapter(adapterAllUser);
                    }
                }else {
                    Toast.makeText(UserActivity.this, "Chưa có user nào", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponeAllUser> call, Throwable t) {

            }
        });
    }
}