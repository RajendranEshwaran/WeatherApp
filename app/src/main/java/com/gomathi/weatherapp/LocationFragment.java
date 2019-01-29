package com.gomathi.weatherapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.gomathi.weatherapp.Adapter.WeatherAdapter;
import com.gomathi.weatherapp.NetworkHelper.RemoteFetchNow;
import com.gomathi.weatherapp.data.DataServiceImpl;
import com.gomathi.weatherapp.data.WeatherItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class LocationFragment extends Fragment {
    private FloatingActionButton mFabAddWeatherItem;
    private WeatherAdapter mAdapter;
    //private final WeatherAPI mWeatherAPI;
    private DataServiceImpl mDataService;
    private ArrayList<WeatherItem> weatherItemList = new ArrayList<>();
    private boolean isDoingRequest = false;
    private boolean isShownError = false;

    //private AddWeatherDialogFragment mAddWeatherDialogFragment;
    //@BindView(R.id.empty_view)
    View emptyView;
    //@BindView(R.id.weather_recycler_view)
    RecyclerView mRecyclerView;
    Handler handler;

    public LocationFragment() {
        // Requires empty public constructor
        handler = new Handler();

    }

   /* @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }*/

    public static LocationFragment newInstance() {
        return new LocationFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataService = DataServiceImpl.getInstance(getActivity());

        mAdapter = new WeatherAdapter(getActivity(), new ArrayList<WeatherItem>(), new WeatherListener() {
            @Override
            public void onWeatherTrashTouched(int id) {
                trashWeatherTouched(id);
            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        weatherItemList = new ArrayList<WeatherItem>();
        mFabAddWeatherItem = (FloatingActionButton) view.findViewById(R.id.fab_add_weather_item);
        mFabAddWeatherItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputDialog();
            }
        });
        mRecyclerView = view.findViewById(R.id.weather_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //mRecyclerView.setAdapter(mAdapter);
        //mRecyclerView.setHasFixedSize(true);
        //loadWeatherItems();
        loadWeatherItems();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //loadWeatherItems();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    public void loadWeatherItems() {
        if (isActive() && !isDoingRequest) {
            ArrayList<String> weatherCities = mDataService.getWeatherCities();
            if (weatherCities != null && weatherCities.size() == 0) {
                showWeatherItems(weatherItemList);
                return;
            }
            isDoingRequest = true;
            isShownError = false;

            for (String weatherCity : weatherCities) {
                updateWeatherData(weatherCity, false);
            }
        }

    }


    private void showInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Location");
        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("Go", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateWeatherData(input.getText().toString(), true);
            }
        });
        builder.show();
    }


    private void updateWeatherData(final String city, final boolean isAdd) {
        new Thread() {
            public void run() {
                final JSONObject json = RemoteFetchNow.getJSON(getActivity(), city);
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
                            renderWeather(json, isAdd);
                        }
                    });
                }
            }
        }.start();
    }

    private void renderWeather(JSONObject json, boolean isAdd) {
        try {


            //(@NonNull String city, @NonNull String img, @NonNull float degreeCelsius, @NonNull int humidity, @NonNull float rainLevel, @NonNull float wind) {

            /* cityField.setText(json.getString("name").toUpperCase(Locale.US) + ", " + json.getJSONObject("sys").getString("country"));
             */
            JSONObject details = json.getJSONArray("weather").getJSONObject(0);
            JSONObject main = json.getJSONObject("main");
            JSONArray weather = json.getJSONArray("weather");

            String degreeCelsius = String.format("%.2f", main.getDouble("temp")) + " ℃";


            int humidity = main.getInt("humidity");
            //float rainLevel = (float)main.getDouble("rainLevel");
            JSONObject windObj = json.getJSONObject("wind");
            float wind = (float) windObj.getDouble("speed");
            String city = json.getString("name");
            //.toUpperCase(Locale.US);

            String img = "";
            if (details.length() > 0)
                img = details.getString("icon");

            String iconStr = details.getString("description");

            WeatherItem weatherItem = new WeatherItem(city, img, degreeCelsius, humidity, 0.0f, wind);
            if (isAdd) {
                ArrayList<String> weatherCities = mDataService.getWeatherCities();

                if (weatherCities != null && weatherCities.size() >= 0) {
                    weatherCities.add(city);
                    mDataService.saveWeatherCities(weatherCities);
                }
                isAdd = false;

            }
            weatherItemList.add(weatherItem);
            mAdapter = new WeatherAdapter(getActivity(), weatherItemList, new WeatherListener() {
                @Override
                public void onWeatherTrashTouched(int id) {
                    trashWeatherTouched(id);
                }
            });


            mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();

            isDoingRequest = false;
        } catch (Exception e) {
            Log.e("SimpleWeather", "One or more fields not found in the JSON data");
        }
    }


    public boolean isActive() {
        return isAdded();
    }


    public void showWeatherItems(ArrayList<WeatherItem> weatherItemsList) {
        mAdapter.swap(weatherItemsList);
    }

    public interface WeatherListener {
        void onWeatherTrashTouched(int id);
    }

    public void displayLoadingIndicator(Boolean isVisible) {
        mRecyclerView.setVisibility(isVisible ? View.GONE : View.VISIBLE);
    }

    public void displayNoNetworkMessage() {
        if (getView() != null) {
            Snackbar snackbarNoInternetConnection = Snackbar
                    .make(getView(), getString(R.string.error_msg_internet), Snackbar.LENGTH_INDEFINITE)
                    .setAction(getString(R.string.again), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            loadWeatherItems();
                        }
                    });
            snackbarNoInternetConnection.show();
        }


    }

    public void trashWeatherTouched(int position) {
        if (weatherItemList != null && weatherItemList.size() >= 0)
            weatherItemList.remove(position);

        ArrayList<String> weatherCities = mDataService.getWeatherCities();
        weatherCities.remove(position);
        mDataService.saveWeatherCities(weatherCities);
        showWeatherItems(weatherItemList);
    }


}
