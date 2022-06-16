package com.example.alarmclock;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.alarmclock.alarm.alarm;
import com.example.alarmclock.news.news;
import com.example.alarmclock.stopwatch.stopwatch;
import com.example.alarmclock.study.study;
import com.example.alarmclock.weather.weather;


public class PageAdapter extends FragmentPagerAdapter {

    int tabcount;

    public PageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tabcount=behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0: return new alarm();
            case 1: return new news();
            case 2: return new weather();
            case 3: return new stopwatch();
            case 4: return new study();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return tabcount;
    }
}