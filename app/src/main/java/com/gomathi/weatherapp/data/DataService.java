package com.gomathi.weatherapp.data;

import java.util.ArrayList;


public interface DataService {
    void saveWeatherCities(ArrayList<String> weatherCities);

    ArrayList<String> getWeatherCities();
}
