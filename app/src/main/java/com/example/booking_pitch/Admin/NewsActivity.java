package com.example.booking_pitch.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
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

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsActivity extends AppCompatActivity {
    ImageView img_news;
    ImageView edit_image;
    TextView title, content,tv_image;
    TextInputLayout layout_edit_title,layout_edit_content;
    Button btn_back;
    ImageView edit_news,delete;
    String parth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        img_news = findViewById(R.id.img_news1);
        title = findViewById(R.id.title_news);
        content = findViewById(R.id.content_news);
        btn_back = findViewById(R.id.btn_back);
        edit_news = findViewById(R.id.edit_news);
        delete = findViewById(R.id.delete_news);

        Bundle bundle = getIntent().getExtras();
        String _id = bundle.getString("_id");
        String img = bundle.getString("imgnews");
        String title1 = bundle.getString("title");
        String content1 = bundle.getString("content");
        Glide.with(this)
                .load("http://datn-2021.herokuapp.com"+img)
                .into(img_news);
        title.setText(title1);
        content.setText(content1);
        edit_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(NewsActivity.this);
                View view = LayoutInflater.from(NewsActivity.this).inflate(R.layout.edit_news, null);
                builder.setView(view);
                TextInputEditText edt_title = view.findViewById(R.id.edit_title);
                TextInputEditText edit_content = view.findViewById(R.id.edit_content);
                layout_edit_title = view.findViewById(R.id.layout_edit_title);
                layout_edit_content = view.findViewById(R.id.layout_edit_content);
                edit_image = view.findViewById(R.id.edit_image);
                tv_image = view.findViewById(R.id.tv_image);
                Button btn_select = view.findViewById(R.id.btn_select);
                Button btn_editNews = view.findViewById(R.id.btn_editNews);
                btn_select.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ContextCompat.checkSelfPermission(NewsActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED){
                            ActivityCompat.requestPermissions(NewsActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);
                        }else {
                            selectImage();
                        }
                    }
                });
                btn_editNews.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!validatePassword1() | !validatePassword2() ) {
                            return;
                        } else{
                            String title = edt_title.getText().toString();
                            String content = edit_content.getText().toString();
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
                            Call<News> call = requestAPI.updateNews(_id,title_body,content_body,body);
                            call.enqueue(new Callback<News>() {
                                @Override
                                public void onResponse(Call<News> call, Response<News> response) {
                                    News news = response.body();
                                    if (news.isSuccess()==true){
                                        Toast.makeText(NewsActivity.this, news.getMessage(), Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(NewsActivity.this, news.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                                @Override
                                public void onFailure(Call<News> call, Throwable t) {
                                    Toast.makeText(NewsActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(NewsActivity.this);
                builder.setMessage("Bạn có chắc muốn xóa tin tức này?")
                        .setNegativeButton("Xóa", new DialogInterface.OnClickListener() {
                                @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl("https://datn-2021.herokuapp.com/api/news/")
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();
                                RequestAPI requestAPI = retrofit.create(RequestAPI.class);
                                Call<News> call = requestAPI.deleteNews(_id);
                                call.enqueue(new Callback<News>() {
                                    @Override
                                    public void onResponse(Call<News> call, Response<News> response) {
                                        News news = response.body();
                                        if (news.isSuccess()==true){
                                            Toast.makeText(NewsActivity.this, news.getMessage(), Toast.LENGTH_SHORT).show();
                                        }else {
                                            Toast.makeText(NewsActivity.this, news.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<News> call, Throwable t) {
                                        Toast.makeText(NewsActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();
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
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewsActivity.this,AdminActivity.class);
                startActivity(intent);
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
                Log.e("BBB",parth);
                edit_image.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private boolean validatePassword1(){
        String passwordOld = layout_edit_title.getEditText().getText().toString().trim();
        if (passwordOld.isEmpty()) {
            layout_edit_title.setError("Không được để trống");
            return false;
        } else {
            layout_edit_title.setError(null);
            return true;
        }
    }
    private boolean validatePassword2(){
        String passwordOld = layout_edit_content.getEditText().getText().toString().trim();
        if (passwordOld.isEmpty()) {
            layout_edit_content.setError("Không được để trống");
            return false;
        } else {
            layout_edit_content.setError(null);
            return true;
        }
    }
}