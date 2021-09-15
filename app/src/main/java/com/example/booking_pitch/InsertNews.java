package com.example.booking_pitch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.booking_pitch.Admin.AdminActivity;
import com.example.booking_pitch.Admin.NewsActivity;
import com.example.booking_pitch.data.RealPathUtil;
import com.example.booking_pitch.data.model.AddPitch;
import com.example.booking_pitch.data.model.News;
import com.example.booking_pitch.data.model.PitchClass;
import com.example.booking_pitch.data.repository.RequestAPI;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InsertNews extends AppCompatActivity {
    Button  btn_select,decode;
    TextView tv_image;
    ImageView load_img;
    TextInputEditText edt_title, edt_content;
    TextInputLayout layout_title,layout_content;
    ProgressDialog progressDialog;

    String sImage;
    String parth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_news);
        btn_select = findViewById(R.id.btn_encode);
        decode = findViewById(R.id.add_news);
        tv_image = findViewById(R.id.tv_image);
        load_img = findViewById(R.id.load_image);
        edt_content = findViewById(R.id.edt_content);
        edt_title = findViewById(R.id.edt_title);
        layout_title = findViewById(R.id.layouy_title);
        layout_content = findViewById(R.id.layout_content);
        
        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(InsertNews.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(InsertNews.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);
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
                if (!validatePassword1() | !validatePassword2() | validatePassword6()) {
                    return;
                } else{
                    progressDialog = new ProgressDialog(InsertNews.this);
                    progressDialog.setMessage("Xin đợi...!");
                    progressDialog.show();
                    String title = edt_title.getText().toString();
                    String content = edt_content.getText().toString();
                    File file = new File(parth);
                    RequestBody title_body = RequestBody.create(MediaType.parse("multipart/form-data"),title);
                    RequestBody content_body = RequestBody.create(MediaType.parse("multipart/form-data"),content);
                    RequestBody image_body = RequestBody.create(MediaType.parse("multipart/form-data"),file);
                    MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), image_body);
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://datn-2021.herokuapp.com/api/news/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    RequestAPI requestAPI = retrofit.create(RequestAPI.class);
                    Call<News> newsCall = requestAPI.createNews(title_body,content_body,body);
                    newsCall.enqueue(new Callback<News>() {
                        @Override
                        public void onResponse(Call<News> call, Response<News> response) {
                            News news = response.body();
                            if (news.isSuccess()==true){
                                Toast.makeText(InsertNews.this, news.getMessage(), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(InsertNews.this, AdminActivity.class);
                                startActivity(intent);
                                progressDialog.cancel();
                            }else {
                                Toast.makeText(InsertNews.this, news.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<News> call, Throwable t) {

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
        if (requestCode==100 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
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
//                Log.e("BBB",parth);
                load_img.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private boolean validatePassword1(){
        String passwordOld = layout_title.getEditText().getText().toString().trim();
        if (passwordOld.isEmpty()) {
            layout_title.setError("Không được để trống");
            return false;
        } else {
            layout_title.setError(null);
            return true;
        }
    }
    private boolean validatePassword2(){
        String passwordOld = layout_content.getEditText().getText().toString().trim();
        if (passwordOld.isEmpty()) {
            layout_content.setError("Không được để trống");
            return false;
        } else {
            layout_content.setError(null);
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
}