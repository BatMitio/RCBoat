package com.example.rcboatmobileapp;

import com.google.android.gms.maps.model.LatLng;

public class TelemetryPacket {
    private LatLng latLng;
    private Double heading;
    private Double batteryPercentage;
    private Boolean cageOpen;

    public TelemetryPacket() {
        this.latLng = new LatLng(42.12963, 24.73437);
        this.heading = 45.0;
        this.batteryPercentage = 75.0;
        this.cageOpen = false;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public Double getHeading() {
        return heading;
    }

    public void setHeading(Double heading) {
        this.heading = heading;
    }

    public Double getBatteryPercentage() {
        return batteryPercentage;
    }

    public void setBatteryPercentage(Double batteryPercentage) {
        this.batteryPercentage = batteryPercentage;
    }

    public Boolean getCageOpen() {
        return cageOpen;
    }

    public void setCageOpen(Boolean cageOpen) {
        this.cageOpen = cageOpen;
    }
}
