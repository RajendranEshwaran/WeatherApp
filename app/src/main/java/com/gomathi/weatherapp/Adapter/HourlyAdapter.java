package com.gomathi.weatherapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gomathi.weatherapp.R;
import com.gomathi.weatherapp.data.HourlyDataItems;

import java.util.List;

public class HourlyAdapter extends ArrayAdapter<String> {

    Context context;
    int resoureId;
    String weatherData[];
    String weatherTimeData[];
    String weatherDescData[];
    int[] images;

    List<HourlyDataItems> dataItems = null;


    public HourlyAdapter(Context context, String[] wData, String[] tData, String[] dData, int[] img) {
        super(context, R.layout.itemrow, wData);

        this.weatherDescData = dData;
        this.weatherTimeData = tData;
        this.weatherData = wData;
        this.context = context;
        this.images = img;
    }

    static class dataHolder {
        ImageView imgview;
        TextView dataTxt;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        dataHolder holder = null;
        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            //LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            // convertView = inf.inflate(resoureId,parent);
            convertView = inf.inflate(R.layout.itemrow, null);
//        holder = new dataHolder();
//        holder.imgview = (ImageView)convertView.findViewById(R.id.hourly_imageview);
//        holder.dataTxt = (TextView)convertView.findViewById(R.id.hourly_textview);
//        convertView.setTag(holder);
        }

//        imgview = (ImageView)convertView.findViewById(R.id.hourly_imageview);
//     holder.dataTxt = (TextView)convertView.findViewById(R.id.hourly_textview);

        TextView dataTimeTxt = (TextView) convertView.findViewById(R.id.timeHourly_txtview);
        TextView dataDescTxt = (TextView) convertView.findViewById(R.id.descHourly_txtview);
        TextView dataWeatherTxt = (TextView) convertView.findViewById(R.id.weatherHourly_txtview);
        ImageView imgView = (ImageView) convertView.findViewById(R.id.hourly_imageview);
        dataTimeTxt.setText(weatherTimeData[position]);
        dataDescTxt.setText(weatherDescData[position]);
        dataWeatherTxt.setText(weatherData[position]);
        imgView.setImageResource(images[position]);

//    else{
////        holder = (dataHolder)convertView.getTag();
////        HourlyDataItems dataItemsTemp = dataItems.get(position);
////        holder.imgview.setImageResource(dataItemsTemp.hourlyWeatherImage);
////        holder.dataTxt.setText(dataItemsTemp.hourlWeatherData);
//
//    }
        return convertView;
    }
}
