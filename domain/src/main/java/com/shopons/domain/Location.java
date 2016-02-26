package com.shopons.domain;

/**
 * Created by komal on 19/2/16.
 */
public class Location {
    private double latitude;
    private double longitude;

    public Location(double lat, double lon)
    {
        latitude=lat;
        longitude=lon;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
