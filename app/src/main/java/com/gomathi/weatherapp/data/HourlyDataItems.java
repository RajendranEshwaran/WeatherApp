package com.gomathi.weatherapp.data;

public class HourlyDataItems {

    int hourlyWeatherImage;
    String hourlWeatherData;


    public HourlyDataItems(int img, String hrdata) {
        this.hourlWeatherData = hrdata;
        this.hourlyWeatherImage = img;
    }
}
