package com.shopons.data.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by komal on 20/3/16.
 */
public class StoreDetailsEntity {
    private String id;
    private List<BrandInfo> brand_info;
    private String name;
    private String address;
    @SerializedName("comp_rating")
    private double rating;
    @SerializedName("total_reviews")
    private int reviews;
    private String city;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<BrandInfo> getBrand_info() {
        return brand_info;
    }

    public void setBrand_info(List<BrandInfo> brand_info) {
        this.brand_info = brand_info;
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

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getReviews() {
        return reviews;
    }

    public void setReviews(int reviews) {
        this.reviews = reviews;
    }
}
