package com.example.booking_pitch.Admin.admin_thongke;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.booking_pitch.Admin.quanLyLich.ChoXacNhan;
import com.example.booking_pitch.Admin.quanLyLich.DaDat_admin;

import org.jetbrains.annotations.NotNull;

public class ViewPagerThongKe extends FragmentStatePagerAdapter {
    String listTab1[] = {"Ngày", "Tháng", "Năm"};
    private ThongKeNgay thongKeNgay ;
    private ThongKeThang thongKeThang ;
    private ThongKeNam thongKeNam;

    public ViewPagerThongKe(@NonNull @NotNull FragmentManager fm) {
        super(fm);
        thongKeNgay = new ThongKeNgay();
        thongKeThang = new ThongKeThang();
        thongKeNam = new ThongKeNam();
    }

    @NonNull
    @NotNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return thongKeNgay;
        }else if (position == 1){
            return thongKeThang;
        }else if(position == 2){
            return thongKeNam;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return listTab1[position];
    }
}
