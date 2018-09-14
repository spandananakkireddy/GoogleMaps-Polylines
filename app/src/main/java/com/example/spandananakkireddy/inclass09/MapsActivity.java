package com.example.spandananakkireddy.inclass09;

import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

import static java.lang.Double.valueOf;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLoadedCallback{

    private GoogleMap mMap;
    Marker marker1, marker2;
    ArrayList<Trip.Points> points = new ArrayList<>();
  


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        InputStream raw = getResources().openRawResource(R.raw.trip);
        Reader rd = new BufferedReader(new InputStreamReader(raw));
        Gson gson = new Gson();
        Trip trip = gson.fromJson(rd, Trip.class);
        points = trip.getPoints();
        Log.d("demo", trip + "");
        Log.d("demo1", points + "");

    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        PolylineOptions poly = new PolylineOptions();
        if (points.size() > 0) {

            for (int i = 0; i < points.size(); i++) {
                LatLng ltLn = new LatLng(Double.parseDouble(points.get(i).getLatitude()), Double.parseDouble(points.get(i).getLongitude()));
                poly.add(ltLn);
                Log.d("demomap", points.get(i).getLatitude()+"");
                if (i == 0) {
                    if (marker1 != null) {
                        marker1.remove();
                    }
                    marker1 = mMap.addMarker((new MarkerOptions()).position(ltLn).title("Start Location"));
                }

                if (i == points.size() - 1) {
                    if (marker2 != null) {
                        marker2.remove();
                    }
                    marker2 = mMap.addMarker((new MarkerOptions()).position(ltLn).title("End Location"));
                }
            }
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(points.get(0).getLatitude()), Double.parseDouble(points.get(0).getLongitude())), 12.0f));
            poly.width(10).color(Color.BLACK).geodesic(true);
            mMap.addPolyline(poly);
           mMap.setOnMapLoadedCallback(this);
        }

    }


    @Override
    public void onMapLoaded() {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for(int j=0;j<points.size();j++){
            builder.include(new LatLng(Double.parseDouble(points.get(j).getLatitude()),Double.parseDouble(points.get(j).getLongitude())));
        }
        LatLngBounds bounds = builder.build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds,12);
        mMap.animateCamera(cameraUpdate);
    }
}

