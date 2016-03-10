package com.shopons.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by komal on 24/2/16.
 */


 public class Store {
    private String id;
    private String name;
    private String address;
    private String city;
    private String contact;
    private double rating;
    private String thumbnail;
    private List<BrandInfo>brand_info;

    public Store(String id,String name, String address,String city, String contact, double rating,
                 String thumbnail,List<BrandInfo> brand_info)
    {
        this.id=id;
        this.name=name;
        this.address=address;
        this.city=city;
        this.contact=contact;
        this.rating=rating;
        this.thumbnail=thumbnail;
        this.brand_info=new ArrayList<>();

        for(BrandInfo brandInfo:brand_info)
            this.brand_info.add(brandInfo);
    }

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
