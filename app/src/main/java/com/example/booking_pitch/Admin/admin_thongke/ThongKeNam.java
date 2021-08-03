package com.example.booking_pitch.Admin.admin_thongke;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.booking_pitch.R;
import com.example.booking_pitch.data.model.TKNgayThang;
import com.example.booking_pitch.data.model.TkThang;
import com.example.booking_pitch.data.repository.RequestAPI;
import com.example.booking_pitch.data.repository.ResponeGetDay;
import com.example.booking_pitch.data.repository.ResponeGetNam;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ThongKeNam extends Fragment {

    EditText edt_year;
    Button btn_tk_year;
    TextView tong_nam;
    List<TKNgayThang> ngayThangList;
    List<TkThang> tkThangList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thong_ke_nam, container, false);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://datn-2021.herokuapp.com/api/pitch/tk/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestAPI requestAPI = retrofit.create(RequestAPI.class);
        tong_nam = view.findViewById(R.id.tong_year);
        edt_year = view.findViewById(R.id.edt_year);
        btn_tk_year = view.findViewById(R.id.btn_tk_year);
        btn_tk_year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<ResponeGetNam> call = requestAPI.getYear(edt_year.getText().toString());
                call.enqueue(new Callback<ResponeGetNam>() {
                    @Override
                    public void onResponse(Call<ResponeGetNam> call, Response<ResponeGetNam> response) {
                        ResponeGetNam responeGetNam = response.body();
                        ngayThangList = new ArrayList<>(Arrays.asList(responeGetNam.getArrPitch()));
                        tkThangList = new ArrayList<>(Arrays.asList(responeGetNam.getArrMonth()));
                        tong_nam.setText("Tổng: "+responeGetNam.getTotalMoney());
                        BarData barData;
                        BarDataSet barDataSet;
                        ArrayList chart;
                        BarChart barChart = view.findViewById(R.id.barchart_nam1);
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
                        Description description = new Description();
                        description.setText("Năm");
                        barChart.setDescription(description);
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

                        float barSpace = 0.05f;
                        float groupSpace = 1.3f;
//                        barData.setBarWidth(0.8f);
//                        barChart.getXAxis().setAxisMinimum(0f);
//                        barChart.getXAxis().setAxisMaximum(0+barChart.getBarData().getGroupWidth(groupSpace,barSpace)*ngayThangList.size());
//                        barChart.getAxisLeft().setAxisMinimum(0);
                        barChart.invalidate();


                        BarData barData1;
                        BarDataSet barDataSet1;
                        ArrayList chart1;
                        BarChart barChart1 = view.findViewById(R.id.barchart_nam2);
                        chart1 = new ArrayList<>();
                        String[] day1 = new String[tkThangList.size()];
                        for (int i = 0; i<tkThangList.size();i++){
                            BarEntry barEntry = new BarEntry(i+1,tkThangList.get(i).getMoneyOfMonth());
                            chart1.add(barEntry);
                            String name1 = tkThangList.get(i).getMonth();
                            day1[i] = name1;
                        }
                        barDataSet1 = new BarDataSet(chart1,"VND");
                        barDataSet1.setColors(ColorTemplate.MATERIAL_COLORS);
                        barDataSet1.setValueTextSize(15f);
                        barData1 = new BarData(barDataSet1);
                        barChart1.setData(barData1);
                        barChart1.setDescription(description);
                        XAxis xAxis1 = barChart1.getXAxis();
                        xAxis1.setValueFormatter(new IndexAxisValueFormatter(day1));
                        xAxis1.setCenterAxisLabels(true);
                        xAxis1.setPosition(XAxis.XAxisPosition.BOTTOM);
                        xAxis1.setGranularity(1);
                        xAxis1.setGranularityEnabled(true);

                        barChart1.setDragEnabled(true);
                        barChart1.setVisibleXRangeMaximum(3);

                        barData1.setBarWidth(0.8f);
                        barChart1.getXAxis().setAxisMinimum(0);
                        barChart1.getXAxis().setAxisMaximum(0+barChart1.getBarData().getGroupWidth(groupSpace,barSpace)*tkThangList.size());
                        barChart1.getAxisLeft().setAxisMinimum(0);
                        barChart1.invalidate();

                    }
                    @Override
                    public void onFailure(Call<ResponeGetNam> call, Throwable t) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage("Bạn cần chọn năm trước")
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