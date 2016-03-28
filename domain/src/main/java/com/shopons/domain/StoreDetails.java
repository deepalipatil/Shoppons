package com.shopons.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by komal on 20/3/16.
 */
public class StoreDetails {
    private String id;
    private String name;
    private String address;
    private String city;
    private String contact;
    private double rating;
    private String thumbnail;
    private List<BrandInfo>brandInfoList;
    private boolean tag_men=false,tag_women=false,tag_kids=false;
    private Location location;
    private int reviews;
    private PhoneNumber phone_numbers;

    public StoreDetails(String id,String name, String address,String city, String contact, double rating,
                 String thumbnail,List<BrandInfo> brand_info,Location location,int reviews,PhoneNumber phone_numbers)
    {
        tag_men=false;
        tag_kids=false;
        tag_women=false;
        this.id=id;
        this.name=name;
        this.address=address;
        this.city=city;
        this.contact=contact;
        this.rating=rating;
        this.thumbnail=thumbnail;
        this.brandInfoList=new ArrayList<>();
        if(location!=null)
            this.location=new Location(location.getLatitude(),location.getLongitude());
        this.reviews=reviews;
        this.phone_numbers=phone_numbers;


        for(BrandInfo brandInfo:brand_info) {
            this.brandInfoList.add(brandInfo);
            if(brandInfo.getPerson_type().equals("Men") && tag_men==false)
                tag_men=true;
            if(brandInfo.getPerson_type().equals("Women") && tag_women==false)
                tag_women=true;
            if(brandInfo.getPerson_type().equals("Kids") && tag_kids==false)
                tag_kids=true;
        }
    }

    public int getReviews() {
        return reviews;
    }

    public void setReviews(int reviews) {
        this.reviews = reviews;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public boolean getTag_men()
    {
        return tag_men;
    }

    public void setTag_men(boolean tag_men) {
        this.tag_men = tag_men;
    }

    public boolean getTag_kids(){
        return tag_kids;
    }

    public void setTag_kids(boolean tag_kids) {
        this.tag_kids = tag_kids;
    }

    public boolean getTag_women()
    {
        return  tag_women;
    }

    public void setTag_women(boolean tag_women) {
        this.tag_women = tag_women;
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
