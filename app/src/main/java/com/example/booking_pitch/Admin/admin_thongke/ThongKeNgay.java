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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class ThongKeNgay extends Fragment {

    TextView edt_day;
    ImageView img_date;
    Button btn_tk_day;
    TextView tong_ngay;
    List<TKNgayThang> ngayThangList;
    String date_1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thong_ke_ngay, container, false);
        //http://192.168.0.100/api/pitch/tk/date/21062021
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://datn-2021.herokuapp.com/api/pitch/tk/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestAPI requestAPI = retrofit.create(RequestAPI.class);

        edt_day = view.findViewById(R.id.edt_day);
        img_date = view.findViewById(R.id.img_date);
        btn_tk_day = view.findViewById(R.id.btn_tk_ngay);
        tong_ngay = view.findViewById(R.id.tong_ngay);
        img_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyyyy");
                        date_1 = simpleDateFormat.format(calendar.getTime());
                        String day1 = date_1.substring(0,2);
                        String month1 = date_1.substring(2,4);
                        String year1 = date_1.substring(4,8);
                        edt_day.setText(day1+"-"+month1+"-"+year1);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        btn_tk_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<ResponeGetDay> call = requestAPI.getDay(date_1);
                call.enqueue(new Callback<ResponeGetDay>() {
                    @Override
                    public void onResponse(Call<ResponeGetDay> call, Response<ResponeGetDay> response) {
                        ResponeGetDay responeGetDay = response.body();
                        ngayThangList = new ArrayList<>(Arrays.asList(responeGetDay.getArrPitch()));
                        tong_ngay.setText("Tổng: "+responeGetDay.getTotalMoney());
                        BarData barData;
                        BarDataSet barDataSet;
                        ArrayList chart;
                        BarChart barChart = view.findViewById(R.id.barchart);
                        chart = new ArrayList<>();
                        String[] day = new String[ngayThangList.size()];
                        for (int i = 0; i<ngayThangList.size(); i++){
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
                        xAxis.setLabelCount(day.length);
                        xAxis.setDrawGridLines(false);
                        xAxis.setDrawAxisLine(false);
                        xAxis.setCenterAxisLabels(true);
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        xAxis.setGranularity(1f);
                        xAxis.setGranularityEnabled(true);

                        barChart.setDragEnabled(true);
                        barChart.setVisibleXRangeMaximum(3);

//                        float barSpace = 0.05f;
//                        float groupSpace = 1.2f;
                        barData.setBarWidth(0.8f);
//                        barChart.getXAxis().setAxisMinimum(0);
//                        barChart.getXAxis().setAxisMaximum(0+barChart.getBarData().getGroupWidth(groupSpace,barSpace)*ngayThangList.size());
//                        barChart.getAxisLeft().setAxisMinimum(0);
                        barChart.invalidate();
                    }
                    @Override
                    public void onFailure(Call<ResponeGetDay> call, Throwable t) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage("Bạn cần chọn ngày trước")
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