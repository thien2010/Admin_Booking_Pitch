package com.example.booking_pitch.Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.booking_pitch.Admin.AdminActivity;
import com.example.booking_pitch.Admin.admin_home.AcountAdminFragment;
import com.example.booking_pitch.R;
import com.example.booking_pitch.data.model.LoginAdminAccount;
import com.example.booking_pitch.data.repository.RequestAPI;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginActivity extends AppCompatActivity {
    Button btn_login;
    String username, password;
    TextInputEditText userAdmin;
    TextInputEditText passwordAdmin;
    CheckBox remember;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;
    ProgressDialog progressDialog;
    RequestAPI requestAPI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_login = findViewById(R.id.btn_login_admin);
        userAdmin = findViewById(R.id.user_admin);
        passwordAdmin = findViewById(R.id.password_admin);
        remember = findViewById(R.id.remember);
        boolean remem = remember.isChecked();
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        progressDialog = new ProgressDialog(LoginActivity.this);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://datn-2021.herokuapp.com/api/user/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        requestAPI = retrofit.create(RequestAPI.class);
        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            userAdmin.setText(loginPreferences.getString("username", ""));
            passwordAdmin.setText(loginPreferences.getString("password", ""));
            remember.setChecked(true);
        }
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userAdmin.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivity.this, "SĐT không được để trống ", Toast.LENGTH_SHORT).show();
                }if (passwordAdmin.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Mật khẩu không được để trống ", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.setTitle("Đăng nhập");
                    progressDialog.setMessage("Đang đăng nhập...");
                    progressDialog.show();
                    Login();
//                    rememberLogin(userAdmin.getText().toString(), passwordAdmin.getText().toString(), remem);
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(userAdmin.getWindowToken(), 0);
                    username = userAdmin.getText().toString();
                    password = passwordAdmin.getText().toString();
                    if (remember.isChecked()) {
                        loginPrefsEditor.putBoolean("saveLogin", true);
                        loginPrefsEditor.putString("username", username);
                        loginPrefsEditor.putString("password", password);
                        loginPrefsEditor.commit();
                    } else {
                        loginPrefsEditor.clear();
                        loginPrefsEditor.commit();
                    }
                }
            }
        });
    }
    public void Login(){
        LoginAdminAccount loginAdminAccount = new LoginAdminAccount(userAdmin.getText().toString(),passwordAdmin.getText().toString());
        Log.e("acount", loginAdminAccount.getUsername() +loginAdminAccount.getPassword());
        Call<LoginAdminAccount> call = requestAPI.loginAdmin(loginAdminAccount);
        call.enqueue(new Callback<LoginAdminAccount>() {
            @Override
            public void onResponse(Call<LoginAdminAccount> call, Response<LoginAdminAccount> response) {
                LoginAdminAccount loginAdminAccount1 = response.body();
                if (loginAdminAccount1!=null){
                    if (loginAdminAccount1.isSuccess() == true){
                        Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                        startActivity(intent);
                        progressDialog.cancel();
                        finish();
                    }else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setMessage("Tài khoản hoặc mật khẩu không chính xác!")
                                .setNegativeButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        progressDialog.cancel();
                                    }
                                });
                        builder.create().show();
                    }
                }else {
                    Toast.makeText(LoginActivity.this, "Tài khoản chưa có trên hệ thống", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<LoginAdminAccount> call, Throwable t) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setMessage("Đăng nhập thất bại")
                        .setNegativeButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                progressDialog.cancel();
                            }
                        });
                builder.create().show();
            }
        });
    }
}