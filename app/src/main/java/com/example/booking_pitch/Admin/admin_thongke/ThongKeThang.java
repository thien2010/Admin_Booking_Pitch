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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.booking_pitch.R;
import com.example.booking_pitch.data.model.TKNgayThang;
import com.example.booking_pitch.data.repository.RequestAPI;
import com.example.booking_pitch.data.repository.ResponeGetDay;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DecimalFormat;
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
    LinearLayout layout_thang;
    TextView edt_month;
    Button btn_tk_month;
    ImageView img_month;
    TextView tong_thang,dt_thang, trong_tai_t, dv_vs_dien_t, sl_san_t, sl_nuoc_t, chi_phi_t, so_coc;
    String month2;
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
        layout_thang = view.findViewById(R.id.layout_thang);
        chi_phi_t = view.findViewById(R.id.chi_phi_t);
        trong_tai_t = view.findViewById(R.id.trong_tai_t);
        dv_vs_dien_t = view.findViewById(R.id.dv_vs_dien_t);
        sl_nuoc_t =  view.findViewById(R.id.sl_nuoc_t);
        sl_san_t = view.findViewById(R.id.so_luong_san_t);
        dt_thang = view.findViewById(R.id.dt_thang);
        tong_thang = view.findViewById(R.id.tong_thang);
        edt_month = view.findViewById(R.id.edt_month);
        img_month = view.findViewById(R.id.img_date_month);
        so_coc = view.findViewById(R.id.so_coc_2);
//        edt_nam = view.findViewById(R.id.ed);
        btn_tk_month = view.findViewById(R.id.btn_tk_month);
        layout_thang.setVisibility(View.INVISIBLE);
        img_month.setOnClickListener(new View.OnClickListener() {
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
                        String date_1 = simpleDateFormat.format(calendar.getTime());
                        String month1 = date_1.substring(2,4);
                        String year1 = date_1.substring(4,8);
                        month2 = month1+year1;
                        edt_month.setText(month1+"-"+year1);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        btn_tk_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String a = edt_month.getText().toString() + edt_nam.getText().toString();
                Call<ResponeGetDay> call = requestAPI.getMonth(month2);
                call.enqueue(new Callback<ResponeGetDay>() {
                    @Override
                    public void onResponse(Call<ResponeGetDay> call, Response<ResponeGetDay> response) {
                        ResponeGetDay responeGetDay = response.body();
                        if (responeGetDay!=null){
                            ngayThangList = new ArrayList<>(Arrays.asList(responeGetDay.getArrPitch()));
                            layout_thang.setVisibility(View.VISIBLE);
                            tong_thang.setText(numberMoney(responeGetDay.getTotalMoney())+" VND");
                            so_coc.setText(responeGetDay.getTotalGiveUp());
                            float cp = responeGetDay.getTotalCost();
                            float dt = Float.valueOf(responeGetDay.getTotalMoney());
                            float loi_nhuan = dt - cp;
                            float tong_dv = Float.valueOf(responeGetDay.getQuantitySoccer())*65000;
                            trong_tai_t.setText(numberMoney(String.valueOf(Float.valueOf(responeGetDay.getTotalUmpire())*150000))+" VND");
                            dv_vs_dien_t.setText(numberMoney(String.valueOf(tong_dv))+" VND");
                            sl_san_t.setText(responeGetDay.getQuantitySoccer());
                            sl_nuoc_t.setText(numberMoney(String.valueOf(Float.valueOf(responeGetDay.getTotalWater())*18000))+" VND");
                            chi_phi_t.setText(numberMoney(String.valueOf(responeGetDay.getTotalCost()))+" VND");
                            dt_thang.setText((numberMoney(String.valueOf(loi_nhuan)))+" VND");

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
                            Description description = new Description();
                            description.setText("VND");
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
                            float groupSpace = 1.2f;
//                        barData.setBarWidth(0.8f);
//                        barChart.getXAxis().setAxisMinimum(0);
//                        barChart.getXAxis().setAxisMaximum(0+barChart.getBarData().getGroupWidth(groupSpace,barSpace)*ngayThangList.size());
//                        barChart.getAxisLeft().setAxisMinimum(0);
                            barChart.invalidate();
                        }else {
                            Toast.makeText(getContext(), "Th???ng k?? tr???ng", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponeGetDay> call, Throwable t) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage("B???n c???n ch???n th??ng tr?????c")
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
    public static String numberMoney(String number){
        DecimalFormat decimalFormat = new DecimalFormat("###,###,##0");
        return decimalFormat.format(Double.parseDouble(number));
    }
}