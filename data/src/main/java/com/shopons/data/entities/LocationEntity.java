package com.shopons.data.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by komal on 11/3/16.
 */
public class LocationEntity {

    @SerializedName("long")
    private double lng;
    private double lat;

    public LocationEntity(double latitude,double longitude)
    {
        this.lat=latitude;
        this.lng=longitude;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
