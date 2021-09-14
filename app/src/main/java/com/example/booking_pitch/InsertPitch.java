package com.example.booking_pitch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.booking_pitch.data.RealPathUtil;
import com.example.booking_pitch.data.model.AddPitch;
import com.example.booking_pitch.data.model.PitchClass;
import com.example.booking_pitch.data.repository.RequestAPI;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InsertPitch extends AppCompatActivity {
    Button  decode,btn_select;
    TextView tv_image;
    ImageView load_img;
    TextInputEditText edt_pitchID, edt_pitchName, edt_price, edt_priceWater,edt_detail;
    String sImage;
    TextInputLayout layout_id,layout_name,layout_gia,layout_gianc,layout_detail;
    String parth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_pitch);
        btn_select = findViewById(R.id.btn_encode);
        decode = findViewById(R.id.decode_encode);
        tv_image = findViewById(R.id.tv_image);
        edt_pitchID = findViewById(R.id.edt_pitchId);
        edt_pitchName = findViewById(R.id.edt_pitchName);
        edt_price = findViewById(R.id.edt_price);
        edt_priceWater = findViewById(R.id.edt_priceWater);
        edt_detail = findViewById(R.id.edt_detail);
        load_img = findViewById(R.id.load_image);
        layout_id = findViewById(R.id.layout_id);
        layout_name = findViewById(R.id.layout_name);
        layout_gia = findViewById(R.id.layout_gia);
        layout_gianc = findViewById(R.id.layout_gianc);
        layout_detail = findViewById(R.id.layout_detail);


        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(InsertPitch.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(InsertPitch.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);
                }else {
                    selectImage();
                }
            }
        });
        decode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                byte[] bytes = Base64.decode(sImage,Base64.DEFAULT);
//                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
//                load_img.setImageBitmap(bitmap);
                if (!validatePassword1() | !validatePassword2() | !validatePassword3() | !validatePassword4() | !validatePassword5()) {
                    return;
                } else {
                    String id = edt_pitchID.getText().toString();
                    String name = edt_pitchName.getText().toString();
                    String price = edt_price.getText().toString();
                    String priceWater = edt_priceWater.getText().toString();
                    String detail = edt_detail.getText().toString();
                    String createBy = "admin";
                    File file = new File(parth);
                    RequestBody id_body = RequestBody.create(MediaType.parse("multipart/form-data"), id);
                    RequestBody admin = RequestBody.create(MediaType.parse("multipart/form-data"), createBy);
                    RequestBody name_body = RequestBody.create(MediaType.parse("multipart/form-data"), name);
                    RequestBody price_body = RequestBody.create(MediaType.parse("multipart/form-data"), price);
                    RequestBody priceWater_body = RequestBody.create(MediaType.parse("multipart/form-data"), priceWater);
                    RequestBody detail_body = RequestBody.create(MediaType.parse("multipart/form-data"), detail);
                    RequestBody image_body = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), image_body);

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://datn-2021.herokuapp.com/api/pitch/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    RequestAPI requestAPI = retrofit.create(RequestAPI.class);
                    Call<AddPitch> call = requestAPI.creatNewPitch(id_body, name_body, price_body, priceWater_body, body, detail_body, admin);
                    call.enqueue(new Callback<AddPitch>() {
                        @Override
                        public void onResponse(Call<AddPitch> call, Response<AddPitch> response) {
                            AddPitch pitchClass = response.body();
                            if (pitchClass.isSuccess() == true) {
                                Toast.makeText(InsertPitch.this, pitchClass.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(InsertPitch.this, pitchClass.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<AddPitch> call, Throwable t) {
                            Toast.makeText(InsertPitch.this, "Thất bại" + t, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }

    private void selectImage() {
        tv_image.setText("");
        load_img.setImageBitmap(null);
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
                load_img.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private boolean validatePassword1(){
        String passwordOld = layout_id.getEditText().getText().toString().trim();
        if (passwordOld.isEmpty()) {
            layout_id.setError("Không được để trống");
            return false;
        } else {
            layout_id.setError(null);
            return true;
        }
    }
    private boolean validatePassword2(){
        String passwordOld = layout_name.getEditText().getText().toString().trim();
        if (passwordOld.isEmpty()) {
            layout_name.setError("Không được để trống");
            return false;
        } else {
            layout_name.setError(null);
            return true;
        }
    }
    private boolean validatePassword3(){
        String passwordOld = layout_gia.getEditText().getText().toString().trim();
        if (passwordOld.isEmpty()) {
            layout_gia.setError("Không được để trống");
            return false;
        } else {
            layout_gia.setError(null);
            return true;
        }
    }
    private boolean validatePassword4(){
        String passwordOld = layout_gianc.getEditText().getText().toString().trim();
        if (passwordOld.isEmpty()) {
            layout_gianc.setError("Không được để trống");
            return false;
        } else {
            layout_gianc.setError(null);
            return true;
        }
    }
    private boolean validatePassword5(){
        String passwordOld = layout_detail.getEditText().getText().toString().trim();
        if (passwordOld.isEmpty()) {
            layout_detail.setError("Không được để trống");
            return false;
        } else {
            layout_detail.setError(null);
            return true;
        }
    }
}