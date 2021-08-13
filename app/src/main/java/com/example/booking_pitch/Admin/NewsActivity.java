package com.example.booking_pitch.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.booking_pitch.R;

public class NewsActivity extends AppCompatActivity {
    ImageView img_news;
    TextView title, content;
    Button btn_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        img_news = findViewById(R.id.img_news1);
        title = findViewById(R.id.title_news);
        content = findViewById(R.id.content_news);
        btn_back = findViewById(R.id.btn_back);
        Bundle bundle = getIntent().getExtras();
        String img = bundle.getString("imgnews");
        String title1 = bundle.getString("title");
        String content1 = bundle.getString("content");
        Glide.with(this)
                .load("http://datn-2021.herokuapp.com"+img)
                .into(img_news);
        title.setText(title1);
        content.setText(content1);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewsActivity.this,AdminActivity.class);
                startActivity(intent);
            }
        });
    }
}