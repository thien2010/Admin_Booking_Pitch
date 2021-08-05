package com.example.booking_pitch.Admin.admin_home;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.booking_pitch.Admin.AdminActivity;
import com.example.booking_pitch.Login.LoginActivity;
import com.example.booking_pitch.R;
import com.example.booking_pitch.data.model.AcountAdmin;
import com.example.booking_pitch.data.model.LoginAdminAccount;
import com.example.booking_pitch.data.repository.RequestAPI;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AcountAdminFragment extends Fragment {
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    LinearLayout doimk;
    LinearLayout logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_acount_admin, container, false);
        doimk = view.findViewById(R.id.doimk);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://datn-2021.herokuapp.com/api/user/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestAPI requestAPI = retrofit.create(RequestAPI.class);

        doimk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View view = LayoutInflater.from(getContext()).inflate(R.layout.change_password, null);
                builder.setView(view);
                TextInputEditText user_name = view.findViewById(R.id.user_admin_change);
                TextInputEditText old_password = view.findViewById(R.id.password_old);
                TextInputEditText new_password = view.findViewById(R.id.password_new);
                TextInputEditText confim_passs = view.findViewById(R.id.password_confim);
                Button change_password = view.findViewById(R.id.btn_change_admin);
                change_password.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (user_name.getText().toString().isEmpty() || old_password.getText().toString().isEmpty() || new_password.getText().toString().isEmpty()){
                            Toast.makeText(getContext(), "Còn thông tin chưa được điền", Toast.LENGTH_SHORT).show();
                        }else if(new_password.getText().toString() != confim_passs.getText().toString()){
                            Toast.makeText(getContext(), "Mật khẩu mới không trùng nhau", Toast.LENGTH_SHORT).show();
                        } else if (new_password.getText().toString().equalsIgnoreCase(confim_passs.getText().toString())){
                            Call<AcountAdmin> call1 = requestAPI.change_password(user_name.getText().toString(),old_password.getText().toString(),new_password.getText().toString());
                            call1.enqueue(new Callback<AcountAdmin>() {
                                @Override
                                public void onResponse(Call<AcountAdmin> call, Response<AcountAdmin> response) {
                                    AcountAdmin acountAdmin = response.body();
                                    if(acountAdmin.isSuccess() == true){
                                        Intent intent = new Intent(getContext(), LoginActivity.class);
                                        startActivity(intent);
                                        Toast.makeText(getContext(), acountAdmin.getMessage(), Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(getContext(), "Tài khoản, Mật Khẩu đúng", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                @Override
                                public void onFailure(Call<AcountAdmin> call, Throwable t) {
                                }
                            });
                        }
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        logout = view.findViewById(R.id.log_out);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
                builder.setMessage("Bạn có chắc muốn đăng xuất không!")
                        .setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            }
                        })
                        .setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                builder.create().show();

            }
        });
        return view;
    }
}