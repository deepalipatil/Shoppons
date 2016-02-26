package com.shopons.domain;

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

    public Store(String id,String name, String address,String city, String contact, double rating)
    {
        this.id=id;
        this.name=name;
        this.address=address;
        this.city=city;
        this.contact=contact;
        this.rating=rating;
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
