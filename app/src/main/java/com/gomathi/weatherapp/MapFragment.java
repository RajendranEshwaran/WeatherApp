package com.gomathi.weatherapp;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap googleMap;
    MapView mapView;
    View view;
    float lati = 0f;
    float longt = 0f;
    String city = "";
    String title, desc = "";


    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        lati = 40.489247f;
        longt = -74.044502f;
        city = "New York";
        title = "Liberty Statue";
        desc = "Yes!! we are here";

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_map, container, false);
        return view;

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = (MapView) view.findViewById(R.id.mapView);

        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMapobj) {

        MapsInitializer.initialize(getContext());
        googleMap = googleMapobj;
        googleMapobj.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMapobj.addMarker(new MarkerOptions().position(new LatLng(lati, longt)).title(title).snippet(desc));
        CameraPosition Liberty = CameraPosition.builder().target(new LatLng(lati, longt)).zoom(4).bearing(0).tilt(45).build();
        googleMapobj.moveCamera(CameraUpdateFactory.newCameraPosition(Liberty));
    }
}
