package com.gomathi.weatherapp.Util;


public class WeatherUtil {
    public static float convertKelvinInCelsius(double kelvin) {
        return (float) (kelvin - 273.15);
    }

    public static float convertKelvinInFar(double kelvin) {
        return (float) (((convertKelvinInCelsius(kelvin) - 32) / 1.8));
    }

    public static String getWeatherIcon(String weatherIconAPI) {
        String weatherIcon;
        switch (weatherIconAPI) {
            //  clear sky
            case "01d":
                weatherIcon = "sw_01";
                break;
            case "01n":
                weatherIcon = "sw_02";
                break;
            // few clouds
            case "02d":
                weatherIcon = "sw_03";
                break;
            case "02n":
                weatherIcon = "sw_07";
                break;
            //  scattered clouds
            case "03d":
                weatherIcon = "sw_04";
                break;
            case "03n":
                weatherIcon = "sw_04";
                break;
            //   broken clouds
            case "04d":
                weatherIcon = "sw_06";
                break;
            case "04n":
                weatherIcon = "sw_06";
                break;
            //    shower rain
            case "09d":
                weatherIcon = "sw_23";
                break;
            case "09n":
                weatherIcon = "sw_23";
                break;
            //    rain
            case "10d":
                weatherIcon = "sw_12";
                break;
            case "10n":
                weatherIcon = "sw_32";
                break;
            //   thunderstorm
            case "11d":
                weatherIcon = "sw_17";
                break;
            case "11n":
                weatherIcon = "sw_37";
                break;
            //   snow
            case "12d":
                weatherIcon = "sw_15";
                break;
            case "12n":
                weatherIcon = "sw_35";
                break;
            //   mist
            case "13d":
                weatherIcon = "sw_10";
                break;
            case "13n":
                weatherIcon = "sw_30";
                break;
            default:
                weatherIcon = "error_icon";
        }
        return weatherIcon;
    }
}
