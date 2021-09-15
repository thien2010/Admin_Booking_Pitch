package com.example.booking_pitch.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.booking_pitch.InsertPitch;
import com.example.booking_pitch.R;
import com.example.booking_pitch.data.RealPathUtil;
import com.example.booking_pitch.data.model.AddPitch;
import com.example.booking_pitch.data.model.News;
import com.example.booking_pitch.data.repository.RequestAPI;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PitchActivity extends AppCompatActivity {
    ImageView img_pitch,edit_image,btn_update,btn_delete;
    TextView name_pitch, price_pitch, info_pitch, price_water,tv_image;
    TextInputLayout layout_edit_name,layout_edit_gia,layout_edit_gianc,layout_edit_detail;
    Button btn_back;
    String id;
    String parth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pitch);
        img_pitch = findViewById(R.id.img_pitch);
        name_pitch = findViewById(R.id.name_pitch);
        price_pitch = findViewById(R.id.price_pitch);
        info_pitch = findViewById(R.id.info_pitch);
        price_water = findViewById(R.id.price_water);
        btn_update = findViewById(R.id.btn_update);
        btn_delete = findViewById(R.id.btn_delete);



        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("_id");
        Log.e("_id",id);
        String img = bundle.getString("img");
        String idpitch = bundle.getString("id");
        String name = bundle.getString("name");
        String price = bundle.getString("price");
        String info = bundle.getString("info");
        String price_water1 = bundle.getString("priceWater");
        Glide.with(this)
                .load("http://datn-2021.herokuapp.com"+img)
                .into(img_pitch);
        name_pitch.setText(name);
        price_water.setText("Giá nước: "+numberMoney(price_water1)+" VND");
        price_pitch.setText("Giá Sân: "+numberMoney(price)+" VND");
        info_pitch.setText(info);

        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PitchActivity.this,AdminActivity.class);
                startActivity(intent);
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PitchActivity.this);
                View view = LayoutInflater.from(PitchActivity.this).inflate(R.layout.edit_pitch, null);
                builder.setView(view);

                TextInputEditText edt_name = view.findViewById(R.id.edit_pitchName);
                TextInputEditText edit_price = view.findViewById(R.id.edit_price);
                TextInputEditText edit_priceWater = view.findViewById(R.id.edit_priceWater);
                TextInputEditText edit_detail = view.findViewById(R.id.edit_detail);
                layout_edit_name = view.findViewById(R.id.layout_edit_name);
                layout_edit_gia = view.findViewById(R.id.layout_edit_price);
                layout_edit_gianc = view.findViewById(R.id.layout_edit_priceWater);
                layout_edit_detail = view.findViewById(R.id.layout_edit_detail);
                edit_image = view.findViewById(R.id.edit_image);
                tv_image = view.findViewById(R.id.tv_image);
                Button btn_select = view.findViewById(R.id.btn_select);
                Button btn_editPitch = view.findViewById(R.id.btn_editPitch);
                btn_select.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ContextCompat.checkSelfPermission(PitchActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED){
                            ActivityCompat.requestPermissions(PitchActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);
                        }else {
                            selectImage();
                        }
                    }
                });
                btn_editPitch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!validatePassword1() | !validatePassword2() | !validatePassword3() | !validatePassword4() | !validatePassword6()) {
                            return;
                        } else {
                            progressDialog = new ProgressDialog(PitchActivity.this);
                            progressDialog.setMessage("Đang sửa sân...!");
                            progressDialog.show();
                            String name = edt_name.getText().toString();
                            String price = edit_price.getText().toString();
                            String priceWater = edit_priceWater.getText().toString();
                            String detail = edit_detail.getText().toString();
                            String createBy = "admin";
                            File file = new File(parth);
                            RequestBody id_body = RequestBody.create(MediaType.parse("multipart/form-data"),id);
                            RequestBody id_pitch = RequestBody.create(MediaType.parse("multipart/form-data"),idpitch);
                            RequestBody admin = RequestBody.create(MediaType.parse("multipart/form-data"),createBy);
                            RequestBody name_body = RequestBody.create(MediaType.parse("multipart/form-data"),name);
                            RequestBody price_body = RequestBody.create(MediaType.parse("multipart/form-data"),price);
                            RequestBody priceWater_body = RequestBody.create(MediaType.parse("multipart/form-data"),priceWater);
                            RequestBody detail_body = RequestBody.create(MediaType.parse("multipart/form-data"),detail);
                            RequestBody image_body = RequestBody.create(MediaType.parse("multipart/form-data"),file);
                            MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), image_body);
                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl("https://datn-2021.herokuapp.com/api/pitch/")
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();
                            RequestAPI requestAPI = retrofit.create(RequestAPI.class);
                            Call<AddPitch> call = requestAPI.updateNewPitch(id,id_pitch,name_body,price_body,priceWater_body,body,detail_body,admin);
                            call.enqueue(new Callback<AddPitch>() {
                                @Override
                                public void onResponse(Call<AddPitch> call, Response<AddPitch> response) {
                                    AddPitch addPitch = response.body();
                                    if (addPitch.isSuccess()==true){
                                        Toast.makeText(PitchActivity.this, addPitch.getMessage(), Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(PitchActivity.this, AdminActivity.class);
                                        startActivity(intent);
                                        progressDialog.cancel();
                                    }else {
                                        Toast.makeText(PitchActivity.this, addPitch.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                                @Override
                                public void onFailure(Call<AddPitch> call, Throwable t) {
                                    Toast.makeText(PitchActivity.this, "Thất bại" +id + id_pitch, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
                Log.e("ID",id+idpitch);
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PitchActivity.this);
                builder.setMessage("Bạn có chắc muốn xóa sân này?")
                        .setNegativeButton("Xóa", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                progressDialog = new ProgressDialog(PitchActivity.this);
                                progressDialog.setMessage("Đang xóa sân...!");
                                progressDialog.show();
                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl("https://datn-2021.herokuapp.com/api/pitch/")
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();
                                RequestAPI requestAPI = retrofit.create(RequestAPI.class);
                                Call<AddPitch> call = requestAPI.deletePitch(id);
                                call.enqueue(new Callback<AddPitch>() {
                                    @Override
                                    public void onResponse(Call<AddPitch> call, Response<AddPitch> response) {
                                        AddPitch pitchClass = response.body();
                                        if (pitchClass.isSuccess()==true){
                                            Toast.makeText(PitchActivity.this, pitchClass.getMessage(), Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(PitchActivity.this, AdminActivity.class);
                                            startActivity(intent);
                                            progressDialog.cancel();
                                        }else {
                                            Toast.makeText(PitchActivity.this, pitchClass.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<AddPitch> call, Throwable t) {
                                        Toast.makeText(PitchActivity.this, "Thất bại" + t, Toast.LENGTH_SHORT).show();
                                    }
                                });
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
    }
    private void selectImage() {
        tv_image.setText("");
        edit_image.setImageBitmap(null);
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent,"Select Image"),100);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==100 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            selectImage();
        }else {
            Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode==RESULT_OK && data!=null){
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
//                byte[] bytes = stream.toByteArray();
//                sImage = Base64.encodeToString(bytes,Base64.DEFAULT);
//                tv_image.setText(sImage);
                parth = RealPathUtil.getRealPath(this,uri);
                tv_image.setText(parth);
                Log.e("BBB",parth);
                edit_image.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private boolean validatePassword1(){
        String passwordOld = layout_edit_name.getEditText().getText().toString().trim();
        if (passwordOld.isEmpty()) {
            layout_edit_name.setError("Không được để trống");
            return false;
        } else {
            layout_edit_name.setError(null);
            return true;
        }
    }
    private boolean validatePassword2(){
        String passwordOld = layout_edit_gia.getEditText().getText().toString().trim();
        if (passwordOld.isEmpty()) {
            layout_edit_gia.setError("Không được để trống");
            return false;
        } else {
            layout_edit_gia.setError(null);
            return true;
        }
    }
    private boolean validatePassword3(){
        String passwordOld = layout_edit_gianc.getEditText().getText().toString().trim();
        if (passwordOld.isEmpty()) {
            layout_edit_gianc.setError("Không được để trống");
            return false;
        } else {
            layout_edit_gianc.setError(null);
            return true;
        }
    }
    private boolean validatePassword4(){
        String passwordOld = layout_edit_detail.getEditText().getText().toString().trim();
        if (passwordOld.isEmpty()) {
            layout_edit_detail.setError("Không được để trống");
            return false;
        } else {
            layout_edit_detail.setError(null);
            return true;
        }
    }
    private boolean validatePassword6(){
        String passwordOld = tv_image.getText().toString().trim();
        if (passwordOld.isEmpty()) {
            tv_image.setText("Bạn chưa chọn ảnh");
            return false;
        } else {
            return true;
        }
    }

    public static String numberMoney(String number){
        DecimalFormat decimalFormat = new DecimalFormat("###,###,##0");
        return decimalFormat.format(Double.parseDouble(number));
    }
}