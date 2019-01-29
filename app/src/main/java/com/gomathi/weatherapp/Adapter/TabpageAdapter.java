package com.gomathi.weatherapp.Adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.gomathi.weatherapp.DailyFragment;
import com.gomathi.weatherapp.HourlyFragment;
import com.gomathi.weatherapp.LocationFragment;
import com.gomathi.weatherapp.MapFragment;
import com.gomathi.weatherapp.NowFragment;

public class TabpageAdapter extends FragmentPagerAdapter {

    String[] tabArray = new String[]{"Now", "Hourly", "Days", "Map", "Location"};
    Integer tabscount = 5;

    public TabpageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabArray[position];
    }

    @Override
    public Fragment getItem(int i) {

        switch (i) {
            case 0:
                NowFragment nowFragment = new NowFragment();
                return nowFragment;
            case 1:
                HourlyFragment hourlyFragmentFragment = new HourlyFragment();
                return hourlyFragmentFragment;
            case 2:
                DailyFragment dailyFragmentFragment = new DailyFragment();
                return dailyFragmentFragment;
            case 3:
                MapFragment mapFragmentFragment = new MapFragment();
                return mapFragmentFragment;
            case 4:
                LocationFragment LocationFragment = new LocationFragment();
                return LocationFragment;

        }
        return null;
    }

    @Override
    public int getCount() {
        return tabscount;
    }
}
