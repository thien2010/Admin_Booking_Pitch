package com.example.booking_pitch.Admin.quanLyLich;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import org.jetbrains.annotations.NotNull;

public class ViewPagerAdmin extends FragmentStatePagerAdapter {
    String listTab[] = {"Chờ xác nhận", "Đã đặt", "Lịch sử đã đá"};
    private ChoXacNhan choXacNhan;
    private DaDat_admin daDat_admin;
    private LichSuXong lichSuXong;

    public ViewPagerAdmin(@NonNull @NotNull FragmentManager fm) {
        super(fm);
        choXacNhan = new ChoXacNhan();
        daDat_admin = new DaDat_admin();
        lichSuXong = new LichSuXong();
    }

    @NonNull
    @NotNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return choXacNhan;
        }else if (position == 1){
            return daDat_admin;
        }else if (position == 2){
            return lichSuXong;
        } else {
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
        return listTab[position];
    }
}