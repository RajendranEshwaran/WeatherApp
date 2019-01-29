package com.gomathi.weatherapp;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.gomathi.weatherapp.Adapter.DaysWeatherAdapter;
import com.gomathi.weatherapp.NetworkHelper.RemoteFetchDays;

import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class DailyFragment extends Fragment {

    Handler handler;

    String[] hourlyWeather = new String[10];
    String[] timeWeather = new String[10];
    String[] descWeather = new String[10];

    String[] errorSoon = new String[100];

    int[] images = {R.drawable.weather1, R.drawable.weather2, R.drawable.weather3, R.drawable.weather4, R.drawable.weather5, R.drawable.weather6, R.drawable.weather7};

    // List<MyParameters> myParameters = new ArrayList<>();

    DaysWeatherAdapter daysWeatherAdapter;

    public DailyFragment() {
        // Required empty public constructor
        handler = new Handler();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        errorSoon = new String[]{""};

        hourlyWeather = new String[]{"One", "Two", "Three", "Four", "Five", "six", "seven"};
        timeWeather = new String[]{"0", "0", "0", "0", "0", "0", "0"};
        descWeather = new String[]{"Temp", "Temp", "Temp", "Temp", "Temp", "Temp", "Temp"};


        View view = inflater.inflate(R.layout.fragment_daily, container, false);

        ListView listView = (ListView) view.findViewById(R.id.days_listView);

        daysWeatherAdapter = new DaysWeatherAdapter(getActivity(), hourlyWeather, timeWeather, descWeather, images);
        listView.setAdapter(daysWeatherAdapter);

        updateWeatherData("Charlotte");
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // updateWeatherData(new CityPreference(getActivity()).getCity());

    }


    private void updateWeatherData(final String city) {
        new Thread() {
            public void run() {
                final JSONObject json = RemoteFetchDays.getJSON(getActivity(), city);
                if (json == null) {
                    handler.post(new Runnable() {
                        public void run() {

                            Toast.makeText(getActivity(),
                                    getActivity().getString(R.string.place_not_found),
                                    Toast.LENGTH_LONG).show();

                        }
                    });
                } else {

                    handler.post(new Runnable() {
                        public void run() {
                            renderWeather(json);
                        }
                    });
                }
            }
        }.start();
    }


    private void renderWeather(JSONObject json) {
        try {

            //  cityField.setText(json.getString("name").toUpperCase(Locale.US) + ", " + json.getJSONObject("sys").getString("country"));

            // String cityName = json.getString("name").toUpperCase(Locale.US) + "," + json.getJSONObject("sys").getString("country");

            // System.out.println("RajayJson : "+ json);

            for (int i = 0; i < 7; i++) {
                JSONObject listDetails = json.getJSONArray("list").getJSONObject(i);

                DateFormat df = DateFormat.getDateTimeInstance();

                String updatedOn = df.format(new Date(listDetails.getLong("dt") * 1000));

                JSONObject mainDetails = listDetails.getJSONObject("temp");

                String temp = String.format("%.2f", mainDetails.getDouble("day")) + " ℃";

                JSONObject datas = listDetails.getJSONArray("weather").getJSONObject(0);

                String desc = datas.getString("description").toLowerCase(Locale.US);

                System.out.println("RajayJson: " + desc + "Time:" + updatedOn + "Tempr:" + temp);

                // myParameters.add(new MyParameters(updatedOn ,temp , desc));

//                setWeatherIcon(datas.getString("icon"));
//
//                setWeatherIcon(details.getInt("id"),
//                        json.getJSONObject("sys").getLong("sunrise") * 1000,
//                        json.getJSONObject("sys").getLong("sunset") * 1000);

                hourlyWeather[i] = temp;
                descWeather[i] = desc;
                timeWeather[i] = updatedOn;
                daysWeatherAdapter.notifyDataSetChanged();

            }


        } catch (Exception e) {
            Log.e("SimpleWeather", "One or more fields not found in the JSON data");
        }
    }


    private void setWeatherIcon(int actualId, long sunrise, long sunset) {
        int id = actualId / 100;
        String icon = "";
        if (actualId == 800) {
            long currentTime = new Date().getTime();
            if (currentTime >= sunrise && currentTime < sunset) {
                icon = getActivity().getString(R.string.weather_sunny);
            } else {
                icon = getActivity().getString(R.string.weather_clear_night);
            }
        } else {
            switch (id) {
                case 2:
                    icon = getActivity().getString(R.string.weather_thunder);
                    break;
                case 3:
                    icon = getActivity().getString(R.string.weather_drizzle);
                    break;
                case 7:
                    icon = getActivity().getString(R.string.weather_foggy);
                    break;
                case 8:
                    icon = getActivity().getString(R.string.weather_cloudy);
                    break;
                case 6:
                    icon = getActivity().getString(R.string.weather_snowy);
                    break;
                case 5:
                    icon = getActivity().getString(R.string.weather_rainy);
                    break;
            }
        }
        // weatherIcon.setText(icon);
    }

    public void changeCity(String city) {

        updateWeatherData(city);

    }
}

