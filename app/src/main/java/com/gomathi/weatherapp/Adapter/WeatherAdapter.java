package com.gomathi.weatherapp.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gomathi.weatherapp.LocationFragment;
import com.gomathi.weatherapp.R;
import com.gomathi.weatherapp.Util.Util;
import com.gomathi.weatherapp.Util.WeatherUtil;
import com.gomathi.weatherapp.data.WeatherItem;

import java.util.ArrayList;


public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    private ArrayList<WeatherItem> mWeatherItems;
    private Context mContext;
    private Resources mResources;
    private String mPackageName;
    private LocationFragment.WeatherListener mWeatherListener;

    public WeatherAdapter(@NonNull Context context, @NonNull ArrayList<WeatherItem> weatherItems, LocationFragment.WeatherListener weatherListener) {
        mWeatherItems = weatherItems;
        mContext = context;
        mResources = mContext.getResources();
        mPackageName = mContext.getPackageName();
        mWeatherListener = weatherListener;
    }

    public void swap(ArrayList<WeatherItem> weatherItems) {
        mWeatherItems = weatherItems;
        notifyDataSetChanged();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView mIVWeatherIcon;
        public TextView mTVDegreeCelsius;
        public TextView mTVHumidity;
        public TextView mTVWind;
        public TextView mTVRainLevel;
        public TextView mTVCity;
        public ImageView mIVTrash;

        public ViewHolder(View v) {
            super(v);
            mIVWeatherIcon = (ImageView) v.findViewById(R.id.iv_weather_icon);
            mTVDegreeCelsius = (TextView) v.findViewById(R.id.tv_degree);
            mTVHumidity = (TextView) v.findViewById(R.id.tv_humidity);
            mTVWind = (TextView) v.findViewById(R.id.tv_wind);
            mTVRainLevel = (TextView) v.findViewById(R.id.tv_rain_level);
            mTVCity = (TextView) v.findViewById(R.id.tv_city_name);
            mIVTrash = (ImageView) v.findViewById(R.id.iv_trash);
        }
    }


    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_weather, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTVWind.setText(getItem(position).getWind() + "m/s");
        holder.mTVDegreeCelsius.setText(String.valueOf(getItem(position).getDegreeCelsius()));
        holder.mTVHumidity.setText(getItem(position).getHumidity() + "%");
        holder.mTVRainLevel.setText(String.valueOf(getItem(position).getRainLevel()));
        holder.mIVWeatherIcon.setImageDrawable(Util.getDrawable(WeatherUtil.getWeatherIcon(getItem(position).getImg()), mResources, mPackageName));
        holder.mTVCity.setText(String.valueOf(getItem(position).getCity()));
        holder.mIVTrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mWeatherListener.onWeatherTrashTouched(holder.getAdapterPosition());
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {

        return mWeatherItems.size();
    }

    public WeatherItem getItem(int position) {

        return mWeatherItems.get(position);
    }

}
