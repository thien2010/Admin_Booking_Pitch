package com.example.booking_pitch.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
import com.example.booking_pitch.R;
import com.example.booking_pitch.data.model.LoginAdminAccount;
import com.example.booking_pitch.data.repository.RequestAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginActivity extends AppCompatActivity {
    Button btn_login;
    String username, password;
    EditText userAdmin;
    EditText passwordAdmin;
    CheckBox remember;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;
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
        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            userAdmin.setText(loginPreferences.getString("username", ""));
            passwordAdmin.setText(loginPreferences.getString("password", ""));
            remember.setChecked(true);
        }

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userAdmin.getText().toString().isEmpty() || passwordAdmin.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Không được để trống ", Toast.LENGTH_SHORT).show();
                }else {
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
                    Login();
                }
            }
        });
    }
    public void Login(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://datn-2021.herokuapp.com/api/user/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestAPI requestAPI = retrofit.create(RequestAPI.class);
        LoginAdminAccount loginAdminAccount = new LoginAdminAccount(userAdmin.getText().toString(),passwordAdmin.getText().toString());
        Log.e("acount", loginAdminAccount.getUsername() +loginAdminAccount.getPassword());
        Call<LoginAdminAccount> call = requestAPI.loginAdmin(loginAdminAccount);
        call.enqueue(new Callback<LoginAdminAccount>() {
            @Override
            public void onResponse(Call<LoginAdminAccount> call, Response<LoginAdminAccount> response) {
                Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                startActivity(intent);
            }
            @Override
            public void onFailure(Call<LoginAdminAccount> call, Throwable t) {
                Log.e("ok","Loi");
            }
        });
    }
}