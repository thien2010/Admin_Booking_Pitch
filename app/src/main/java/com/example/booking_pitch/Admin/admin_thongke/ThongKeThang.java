package com.example.booking_pitch.Admin.admin_thongke;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.booking_pitch.R;
import com.example.booking_pitch.data.model.TKNgayThang;
import com.example.booking_pitch.data.repository.RequestAPI;
import com.example.booking_pitch.data.repository.ResponeGetDay;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ThongKeThang extends Fragment {
    EditText edt_month,edt_nam;
    Button btn_tk_month;
    Spinner spinner;
    TextView tong_thang;
    List<TKNgayThang> ngayThangList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thong_ke_thang, container, false);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://datn-2021.herokuapp.com/api/pitch/tk/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestAPI requestAPI = retrofit.create(RequestAPI.class);
        tong_thang = view.findViewById(R.id.tong_thang);
        edt_month = view.findViewById(R.id.edt_thang);
        edt_nam = view.findViewById(R.id.edt_nam);
        btn_tk_month = view.findViewById(R.id.btn_tk_thang);
        btn_tk_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = edt_month.getText().toString() + edt_nam.getText().toString();
                Log.e("y",a);
                Call<ResponeGetDay> call = requestAPI.getMonth(a);
                call.enqueue(new Callback<ResponeGetDay>() {
                    @Override
                    public void onResponse(Call<ResponeGetDay> call, Response<ResponeGetDay> response) {
                        ResponeGetDay responeGetDay = response.body();
                        ngayThangList = new ArrayList<>(Arrays.asList(responeGetDay.getArrPitch()));
                        tong_thang.setText("Tổng: "+responeGetDay.getTotalMoney());
                        BarData barData;
                        BarDataSet barDataSet;
                        ArrayList chart;
                        BarChart barChart = view.findViewById(R.id.barchart_thang);
                        chart = new ArrayList<>();
                        String[] day = new String[ngayThangList.size()];
                        for (int i = 0; i<ngayThangList.size();i++){
                            BarEntry barEntry = new BarEntry(i+1,ngayThangList.get(i).getTotalPrice());
                            chart.add(barEntry);
                            String name = ngayThangList.get(i).getPitchName();
                            day[i] = name;
                        }
                        barDataSet = new BarDataSet(chart,"VND");
                        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                        barDataSet.setValueTextSize(15f);
                        barData = new BarData(barDataSet);
                        barChart.setData(barData);

                        XAxis xAxis = barChart.getXAxis();
                        xAxis.setValueFormatter(new IndexAxisValueFormatter(day));
                        xAxis.setCenterAxisLabels(true);
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        xAxis.setGranularity(1);
                        xAxis.setGranularityEnabled(true);

                        barChart.setDragEnabled(true);
                        barChart.setVisibleXRangeMaximum(3);

                        float barSpace = 0.05f;
                        float groupSpace = 0.5f;
                        barData.setBarWidth(0.8f);
                        barChart.getXAxis().setAxisMinimum(0);
                        barChart.getXAxis().setAxisMaximum(0+barChart.getBarData().getGroupWidth(groupSpace,barSpace)*ngayThangList.size());
                        barChart.getAxisLeft().setAxisMinimum(0);
                        barChart.invalidate();
                    }

                    @Override
                    public void onFailure(Call<ResponeGetDay> call, Throwable t) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage("Bạn cần chọn tháng trước")
                                .setNegativeButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                });
                        builder.create().show();
                    }
                });
            }
        });
        return view;
    }
}