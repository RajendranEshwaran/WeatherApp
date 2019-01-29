package com.gomathi.weatherapp.data;

import android.content.Context;
import android.support.annotation.NonNull;

import com.gomathi.weatherapp.Constants;

import java.util.ArrayList;


public class DataServiceImpl implements Constants, DataService {
    private static DataServiceImpl INSTANCE = null;
    private TinyDB mTinyDB = null;

    private DataServiceImpl(@NonNull Context context) {
        mTinyDB = new TinyDB(context);
    }

    public static DataServiceImpl getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new DataServiceImpl(context);
        }
        return INSTANCE;
    }

    public void saveWeatherCities(ArrayList<String> weatherCities) {
        mTinyDB.putListString(KEY_WEATHER_ARRAY_LIST, weatherCities);
    }

    public ArrayList<String> getWeatherCities() {
        return mTinyDB.getListString(KEY_WEATHER_ARRAY_LIST);
    }
}
