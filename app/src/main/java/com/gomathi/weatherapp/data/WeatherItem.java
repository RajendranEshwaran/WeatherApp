package com.gomathi.weatherapp.data;

import android.support.annotation.NonNull;


public class WeatherItem {
    private final String mCity;
    private final String mImg;
    private final String mDegreeCelsius;
    private final int mHumidity;
    private final float mRainLevel;
    private final float mWind;

    public WeatherItem(@NonNull String city, @NonNull String img, @NonNull String degreeCelsius, @NonNull int humidity, @NonNull float rainLevel, @NonNull float wind) {
        mCity = city;
        mImg = img;
        mDegreeCelsius = degreeCelsius;
        mHumidity = humidity;
        mRainLevel = rainLevel;
        mWind = wind;
    }

    public WeatherItem(@NonNull String city, @NonNull String img, @NonNull float kelvin, @NonNull int humidity, @NonNull float rainLevel, @NonNull float wind) {
        mCity = city;
        mImg = img;
        mDegreeCelsius = "0.0f";
        mHumidity = humidity;
        mRainLevel = rainLevel;
        mWind = wind;
    }

    public String getCity() {
        return mCity;
    }

    public String getImg() {
        return mImg;
    }

    public String getDegreeCelsius() {
        return mDegreeCelsius;
    }

    public float getHumidity() {
        return mHumidity;
    }

    public float getRainLevel() {
        return mRainLevel;
    }

    public float getWind() {
        return mWind;
    }
}
