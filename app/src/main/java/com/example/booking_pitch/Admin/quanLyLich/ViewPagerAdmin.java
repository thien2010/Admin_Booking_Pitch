package com.example.booking_pitch.Admin.quanLyLich;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import org.jetbrains.annotations.NotNull;

public class ViewPagerAdmin extends FragmentStatePagerAdapter {
    String listTab[] = {"Chờ xác nhận", "Đã đặt"};
    private ChoXacNhan choXacNhan;
    private DaDat_admin daDat_admin;

    public ViewPagerAdmin(@NonNull @NotNull FragmentManager fm) {
        super(fm);
        choXacNhan = new ChoXacNhan();
        daDat_admin = new DaDat_admin();
    }

    @NonNull
    @NotNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return choXacNhan;
        }else if (position == 1){
            return daDat_admin;
        }else {
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return listTab[position];
    }
}