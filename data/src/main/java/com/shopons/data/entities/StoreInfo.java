package com.shopons.data.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by komal on 29/2/16.
 */
public class StoreInfo {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("address")
    private String address;
    @SerializedName("city")
    private String city;
    @SerializedName("contact")
    private String contact;
    @SerializedName("comp_rating")
    private double rating;
    @SerializedName("thumbnail")
    private String thumbnail;
    @SerializedName("brand_info")
    private List<BrandInfo> brand_info;

    public List<BrandInfo> getBrand_info() {
        return brand_info;
    }

    public void setBrand_info(List<BrandInfo> brand_info) {
        this.brand_info = brand_info;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {

        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public double getRating() {
        return rating;
    }
    public void setRating(double rating) {
        this.rating = rating;
    }


}

