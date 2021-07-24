package com.example.booking_pitch.Admin.quanLyLich;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.booking_pitch.R;
import com.google.android.material.tabs.TabLayout;

public class AdminFragment_QLL extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin__q_l_l, container, false);
        viewPager = view.findViewById(R.id.viewpager_tab_admin);
        tabLayout = view.findViewById(R.id.tabs_admin);
        viewPager.setAdapter(new ViewPagerAdmin(getFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }
}