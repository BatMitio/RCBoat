package com.example.rcboatmobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private final BluetoothService bluetoothService;

    public MainActivity() {
        this.bluetoothService = new BluetoothService();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment map =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
        map.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        bluetoothService.setMap(googleMap);
        Thread thread = new Thread(bluetoothService);
        thread.start();
        System.out.println();
    }
}