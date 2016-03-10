package com.shopons.data.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by deepali on 4/3/16.
 */
public class UserEntity {

    private String userName;
    private String firstName;
    private String lastName;
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("auth_key")
    private String authKey;
    @SerializedName("phone_number")
    private String phoneNumber;
    private String userType;
    @SerializedName("facebook_access_token")
    private String facebookToken;
    @SerializedName("google_plus_id_token")
    private String googlePlusToken;
    @SerializedName("facebook_user_id")
    private long facebookUserId;
    @SerializedName("id")
    private String id;
    private boolean isGplus;
    private boolean isFacebook;

    @SerializedName("photo_url")
    private String profilePicUrl;
    @SerializedName("status")
    private String status;

    @SerializedName("email_verified")
    private boolean isEmailVerified;
    @SerializedName("phone_number_verified")
    private boolean isPhoneVerified;
    @SerializedName("password")
    private String password;
    private boolean isLoggedIn;
    @SerializedName("temporary_password")
    private String temporaryPassword;
    private String unverifiedNumber = "";
    @SerializedName("otp")
    private String otp;

    public UserEntity(){}

    public UserEntity(final String email, final String password){
        this.email = email;
        this.password = password;
    }

    public String getName(){
        return name;
    }

    public String getUserName(){
        return userName;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public String getAuthKey(){
        return authKey;
    }

    public String getEmail(){
        return email;
    }

    public String getFacebookToken(){ return facebookToken;}

    public String getPhoneNumber(){
        return phoneNumber;
    }

    public boolean isEmailVerified(){
        return isEmailVerified;
    }

    public boolean isPhoneVerified(){
        return isPhoneVerified;
    }

    public String getPassword(){
        return password;
    }

    public String getTemporaryPassword() {
        return temporaryPassword;
    }

    public String getUserType() {
        return userType;
    }

    public String getStatus() {
        return status;
    }


    public void setName(final String name){ this.name = name;}

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    public void setFacebookToken(String facebookToken) {
        this.facebookToken = facebookToken;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIsFacebook(boolean isFacebook) {
        this.isFacebook = isFacebook;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isFacebook() {
        return isFacebook;
    }

    public void setPhoneNumber(String phone_number) {
        this.phoneNumber = phone_number;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

    public void setIsEmailVerified(boolean isEmailVerified) {
        this.isEmailVerified = isEmailVerified;
    }

    public void setIsPhoneVerified(boolean isPhoneVerified) {
        this.isPhoneVerified = isPhoneVerified;
    }

    public void setPassword(final String password){
        this.password = password;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setIsLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    public void setTemporaryPassword(String temporaryPassword) {
        this.temporaryPassword = temporaryPassword;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUnverifiedNumber(String unverifiedNumber) {
        this.unverifiedNumber = unverifiedNumber;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getId() {
        return id;
    }

    public long getFacebookUserId() {
        return facebookUserId;
    }

    public void setFacebookUserId(long facebookUserId) {
        this.facebookUserId = facebookUserId;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public boolean isGplus() {
        return isGplus;
    }

    public void setIsGplus(boolean isGplus) {
        this.isGplus = isGplus;
    }

    public String getGooglePlusToken() {
        return googlePlusToken;
    }

    public void setGooglePlusToken(String googlePlusToken) {
        this.googlePlusToken = googlePlusToken;
    }

}
