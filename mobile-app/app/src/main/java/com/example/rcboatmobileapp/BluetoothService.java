package com.example.rcboatmobileapp;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

//TODO: Currently simulating bluetooth beacon
public class BluetoothService implements Runnable {
    private LatLng center = new LatLng(42.129543, 24.706563);
    private LatLng point = new LatLng(42.129543, 24.706563);
    private Double angle = 0.0;
    private GoogleMap googleMap;
    private Marker marker = null;

    private AppCompatActivity context;

    public BluetoothService(AppCompatActivity context) {
        this.context = context;
    }

    @Override
    public void run() {
        TextView telemetry = context.findViewById(R.id.telemetry);
        PolylineOptions polylineOptions = new PolylineOptions()
                .clickable(true);
        for(int i = 0; i < 360; ++i){
            polylineOptions.add(new LatLng(center.latitude + computeX(i) * 0.0002,
                    center.longitude + computeY(i) * 0.0002));
        }

        new Handler(Looper.getMainLooper()).post(() -> {
            googleMap.clear();
            marker = googleMap.addMarker(new MarkerOptions().position(point));
            googleMap.addPolyline(polylineOptions);
        });
        while (marker == null) ;
        while (true) {


            point = new LatLng(center.latitude + computeX(angle) * 0.0002,
                    center.longitude + computeY(angle) * 0.0002);

            float heading = (float) calculateHeading(angle);

            new Handler(Looper.getMainLooper()).post(() -> {
                telemetry.setText(angle + "\n" + calculateHeading(angle));
                marker.setPosition(point);
                CameraPosition position = new CameraPosition(point, 18.0f, 45.0f, heading);
                googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(position));
            });

            angle += 0.5;
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private double computeX(double deg) {
        deg %= 360;
        return Math.cos(Math.toRadians(deg));
    }

    private double computeY(double deg) {
        deg %= 360;
        double x = computeX(deg);
        if(deg == 180)
            return 0;
        return ((180 - deg) * (x * Math.sqrt(1 - x * x))) / Math.abs(180 - deg);
    }

    private double calculateHeading(double deg) {
        deg %= 360;
        double heading = Math.toDegrees(
                Math.atan(
                        (computeY(angle + 0.001) - computeY(angle - 0.001)) /
                                (computeX(angle + 0.001) - computeX(angle - 0.001))
                )
        );
        if(deg < 180)
            heading += 180;
        return heading;
    }

    public LatLng getPoint() {
        return point;
    }

    public void setMap(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }
}
