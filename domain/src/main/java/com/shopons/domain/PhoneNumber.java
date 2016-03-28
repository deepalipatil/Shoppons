package com.shopons.domain;

/**
 * Created by komal on 25/3/16.
 */
public class PhoneNumber {
    private String telephone;
    private String mobile;
    private String landline;

    public PhoneNumber(String telephone, String mobile,String landline)
    {
        this.telephone=telephone;
        this.mobile=mobile;
        this.landline=landline;
    }

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
