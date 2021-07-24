package com.example.booking_pitch.Admin.admin_thongke;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.booking_pitch.Admin.quanLyLich.ViewPagerAdmin;
import com.example.booking_pitch.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


public class AdminFragment_TK extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin__t_k, container, false);
        viewPager = view.findViewById(R.id.viewpager_tab_thong_ke);
        tabLayout = view.findViewById(R.id.tab_thongke);
        viewPager.setAdapter(new ViewPagerThongKe(getFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }
}