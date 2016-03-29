package com.shopons.domain;

public class User {

    String id;
    String name;
    String auth_key;
    String mobile_number;
    String email;
    String photo_url;
    String fb_token;
    long fb_id;
    String google_token;
    boolean is_logged_in;
    boolean is_info_pushed;

    public User(){}

    public User(final String authKey, final String fullName, final String emailAddress, final String id) {
        this.name = fullName;
        this.email = emailAddress;
        this.id = id;
        this.auth_key = authKey;
    }
    public boolean getIs_info_pushed(){return is_info_pushed;}

    public void setIs_info_pushed(boolean infopushed){this.is_info_pushed=infopushed;}

    public boolean get_is_logged_in(){return is_logged_in;}

    public void set_is_logged_in(boolean loggedin){ this.is_logged_in = loggedin;}

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

    public String getAuth_key() {
        return auth_key;
    }

    public void setAuth_key(String auth_key) {
        this.auth_key = auth_key;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public String getFb_token() {
        return fb_token;
    }

    public void setFb_token(String fb_token) {
        this.fb_token = fb_token;
    }

    public long getFb_id() {
        return fb_id;
    }

    public void setFb_id(long fb_id) {
        this.fb_id = fb_id;
    }

    public String getGoogle_token() {
        return google_token;
    }

    public void setGoogle_token(String google_token) {
        this.google_token = google_token;
    }
}
