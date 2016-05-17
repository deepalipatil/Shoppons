package com.shopons.data.entities;

import com.google.gson.annotations.SerializedName;
import com.shopons.domain.Location;

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
    @SerializedName("sub_locality")
    private String locality;
    @SerializedName("city")
    private String city;
    @SerializedName("contact")
    private String contact;
    @SerializedName("comp_rating")
    private double rating;
    @SerializedName("thumbnail")
    private String thumbnail;
    @SerializedName("brand_info")
    private List<BrandInfo> brandInfoList;
    @SerializedName("total_reviews")
    private int reviews;
    private LocationEntity location;
    private PhoneNumberEntity phone_numbers;
    private List<DealsInfo> dealsInfoList;




    public PhoneNumberEntity getPhone_numbers() {
        return phone_numbers;
    }

    public void setPhone_numbers(PhoneNumberEntity phone_numbers) {
        this.phone_numbers = phone_numbers;
    }

    public int getReviews() {
        return reviews;
    }

    public void setReviews(int reviews) {
        this.reviews = reviews;
    }

    public LocationEntity getLocation() {
        return location;
    }

    public void setLocation(LocationEntity location) {

        this.location = new LocationEntity(location.getLat(),location.getLng());
    }

    public List<BrandInfo> getBrandInfoList() {
        return brandInfoList;
    }

    public void setBrandInfoList(List<BrandInfo> brandInfoList) {
        this.brandInfoList = brandInfoList;
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

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
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

    public List<DealsInfo> getDealsInfoList() {
        return dealsInfoList;
    }

    public void setDealsInfoList(List<DealsInfo> dealsInfoList) {
        this.dealsInfoList = dealsInfoList;
    }
}

