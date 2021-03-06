package com.example.booking_pitch.Admin.admin_home;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.booking_pitch.Admin.AdminActivity;
import com.example.booking_pitch.Login.LoginActivity;
import com.example.booking_pitch.R;
import com.example.booking_pitch.data.model.AcountAdmin;
import com.example.booking_pitch.data.model.AdapterAllUser;
import com.example.booking_pitch.data.model.LoginAdminAccount;
import com.example.booking_pitch.data.model.Users;
import com.example.booking_pitch.data.repository.RequestAPI;
import com.example.booking_pitch.data.repository.ResponeAllUser;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AcountAdminFragment extends Fragment {
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    //"(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{8,}" +               //at least 6 characters
                    "$");
    TextInputLayout tv_laout1,tv_laout2,tv_laout3;
    LinearLayout doimk;
    LinearLayout logout;
    LinearLayout info;
    LinearLayout get_all_user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_acount_admin, container, false);
        doimk = view.findViewById(R.id.doimk);
        info = view.findViewById(R.id.info_admin);
        get_all_user = view.findViewById(R.id.get_all_user);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://datn-2021.herokuapp.com/api/user/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestAPI requestAPI = retrofit.create(RequestAPI.class);
        get_all_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UserActivity.class);
                startActivity(intent);
            }
        });
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View view = LayoutInflater.from(getContext()).inflate(R.layout.info_app, null);
                builder.setView(view);
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
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
                tv_laout1 = view.findViewById(R.id.txt_layout1);
                tv_laout2 = view.findViewById(R.id.txt_layout2);
                tv_laout3 = view.findViewById(R.id.txt_layout3);
                Button change_password = view.findViewById(R.id.btn_change_admin);
                change_password.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!validatePassword() | !validatePassword1() | !validatePassword3()){
                            return;
                        }else if (user_name.getText().toString().isEmpty()){
                            Toast.makeText(getContext(), "C??n th??ng tin ch??a ???????c ??i???n", Toast.LENGTH_SHORT).show();
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
                                        tv_laout1.setError("M???t kh???u sai");
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
                builder.setMessage("B???n c?? ch???c mu???n ????ng xu???t kh??ng!")
                        .setNegativeButton("Tho??t", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            }
                        })
                        .setPositiveButton("H???y", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                builder.create().show();

            }
        });
        return view;
    }
    private boolean validatePassword() {

        String passwordInput = tv_laout2.getEditText().getText().toString().trim();

        if (passwordInput.isEmpty()) {
            tv_laout2.setError("Kh??ng ???????c ????? tr???ng");
            return false;
        }  else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            tv_laout2.setError("M???t kh???u: 8 k?? t??? tr??? l??n,A-Z,a-z,0-9,[@#$%^&+=]");
            return false;
        } else {
            tv_laout2.setError(null);
            return true;
        }
    }
    private boolean validatePassword1(){
        String passwordOld = tv_laout1.getEditText().getText().toString().trim();
        if (passwordOld.isEmpty()) {
            tv_laout1.setError("Kh??ng ???????c ????? tr???ng");
            return false;
        } else {
            tv_laout1.setError(null);
            return true;
        }
    }
    private boolean validatePassword3(){
        String passwordFim = tv_laout3.getEditText().getText().toString().trim();
        String passwordInput = tv_laout2.getEditText().getText().toString().trim();
        if (passwordFim.isEmpty()) {
            tv_laout3.setError("Kh??ng ???????c ????? tr???ng");
            return false;
        }else if (!passwordFim.equals(passwordInput)){
            tv_laout3.setError("M???t kh???u x??c nh???n sai");
            return false;
        }else {
            tv_laout3.setError(null);
            return true;
        }
    }
}