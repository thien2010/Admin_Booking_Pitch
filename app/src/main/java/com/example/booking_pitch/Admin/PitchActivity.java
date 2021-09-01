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

import java.text.DecimalFormat;

public class PitchActivity extends AppCompatActivity {
    ImageView img_pitch;
    TextView name_pitch, price_pitch, info_pitch;
    Button btn_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pitch);
        img_pitch = findViewById(R.id.img_pitch);
        name_pitch = findViewById(R.id.name_pitch);
        price_pitch = findViewById(R.id.price_pitch);
        info_pitch = findViewById(R.id.info_pitch);
        Bundle bundle = getIntent().getExtras();
        String img = bundle.getString("img");
        String name = bundle.getString("name");
        String price = bundle.getString("price");
        String info = bundle.getString("info");
        Glide.with(this)
                .load("http://datn-2021.herokuapp.com"+img)
                .into(img_pitch);
        name_pitch.setText(name);
        price_pitch.setText(numberMoney(price));
        info_pitch.setText(info);

        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PitchActivity.this,AdminActivity.class);
                startActivity(intent);
            }
        });
    }
    public static String numberMoney(String number){
        DecimalFormat decimalFormat = new DecimalFormat("###,###,##0");
        return decimalFormat.format(Double.parseDouble(number));
    }
}