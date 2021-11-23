package com.example.viewpager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.google.android.material.bottomappbar.BottomAppBar;

import java.util.ArrayList;

public class MyPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> mDate;


    public MyPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);

        mDate = new ArrayList<>();
        mDate.add(new ColorFragment());
        mDate.add(new Color2Fragment());
        mDate.add(new Color3Fragment());

    }

    @Override
    public int getCount() {
        return mDate.size();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mDate.get(position);
    }
}
