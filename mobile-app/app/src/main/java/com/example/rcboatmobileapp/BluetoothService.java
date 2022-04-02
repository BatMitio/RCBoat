package com.example.rcboatmobileapp;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

//TODO: Currently simulating bluetooth beacon
public class BluetoothService implements Runnable {
    private LatLng center = new LatLng(42.129543, 24.706563);
    private LatLng point = new LatLng(42.129543, 24.706563);
    private Double angle = 0.0;
    private GoogleMap googleMap;
    private Marker marker = null;

    @Override
    public void run() {
        new Handler(Looper.getMainLooper()).post(() -> {
            googleMap.clear();
            marker = googleMap.addMarker(new MarkerOptions().position(point));
        });
        while (marker == null);
        while (true) {
            while(angle > 360)
                angle -= 360;
            double cos = Math.cos(Math.toRadians(angle));
            double x = cos;
            double y = (180.0 - angle) * (cos * Math.sqrt(1 - cos * cos)) / Math.abs(180.0 - angle);

            point = new LatLng(center.latitude + x * 0.0002,
                    center.longitude + y * 0.0002);

            new Handler(Looper.getMainLooper()).post(() -> {
                marker.setPosition(point);
                CameraPosition position = new CameraPosition(point, 18.0f, 45.0f, 0.0f);
                googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(position));
            });

            angle += 0.5;
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public LatLng getPoint() {
        return point;
    }

    public void setMap(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }
}
