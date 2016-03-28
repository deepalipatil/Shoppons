package com.shopons.data.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by komal on 25/3/16.
 */
public class PhoneNumberEntity {
    private String telephone;
    private String mobile;
    @SerializedName("land_line")
    private String landline;

    public String getLandline() {
        return landline;
    }

    public void setLandline(String landline) {
        this.landline = landline;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
