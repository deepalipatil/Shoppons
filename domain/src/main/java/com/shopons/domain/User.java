package com.shopons.domain;

/**
 * User model
 *
 * @author : Kaustubh Deshmukh
 * @date : 01/10/15 : 3:42 PM
 * @email : akshay@betacraft.co
 */
public class User {
    private String mUserName;
    private String mFirstName;
    private String mLastName;
    private String mFullName;
    private String mEmailAddress;
    private String mProfilePicUrl;
    private String mPhoneNumber;
    private String mStatus;
    private String mUserType;


    private String mAuthKey;
    private String mFacebookToken;
    private String mGooglePlusToken;
    private String mId;
    private boolean mIsFacebook;
    private boolean mIsGplus;
    private long mFacebookId;
    private boolean mIsEmailVerified;
    private boolean mIsPhoneVerified;
    private String mPassword;
    private boolean mIsLoggedIn;
    private String mTemporaryPassword;

    public User() {}

    public User(final String email, final String password) {
        mEmailAddress = email;
        mPassword = password;
    }


    public User(final String authkey,final String fullName, final String email, final String phoneNumber,
                final String password,final String id){
        mAuthKey = authkey;
        mFullName = fullName;
        mEmailAddress = email;
        mPhoneNumber = phoneNumber;
        mPassword = password;
        mId = id;
    }

    public String getFullName(){
        return mFullName;
    }

    public String getUserName(){
        return mUserName;
    }

    public String getFirstName(){
        return mFirstName;
    }

    public String getLastName(){
        return mLastName;
    }

    public String getAuthKey(){
        return mAuthKey;
    }

    public String getEmailAddress(){
        return mEmailAddress;
    }

    public String getFacebookToken(){
        return mFacebookToken;
    }

    public String getId() {
        return mId;
    }


    public void setFullName(final String fullName){
        mFullName = fullName;
    }


    public void setUserName(String userName) {
        mUserName = userName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public void setEmailAddress(String emailAddress) {
        mEmailAddress = emailAddress;
    }

    public void setAuthKey(String authKey) {
        mAuthKey = authKey;
    }

    public void setFacebookToken(String facebookToken) {
        mFacebookToken = facebookToken;
    }

    public void setId(String id) {
        mId = id;
    }

    public long getFacebookId() {
        return mFacebookId;
    }

    public void setFacebookId(long facebookId) {
        mFacebookId = facebookId;
    }

    public boolean isFacebook() {
        return mIsFacebook;
    }

    public void setIsFacebook(boolean isFacebook) {
        mIsFacebook = isFacebook;
    }

    public boolean isGplus() {
        return mIsGplus;
    }

    public void setIsGplus(boolean isGplus) {
        mIsGplus = isGplus;
    }

    public String getGooglePlusToken() {
        return mGooglePlusToken;
    }

    public void setGooglePlusToken(String googlePlusToken) {
        mGooglePlusToken = googlePlusToken;
    }

    public String getProfilePicUrl() {
        return mProfilePicUrl;
    }

    public boolean isLoggedIn() {
        return mIsLoggedIn;
    }

    public void setIsLoggedIn(boolean isLoggedIn) {
        mIsLoggedIn = isLoggedIn;
    }

    public void setTemporaryPassword(String mTemporaryPassword) {
        this.mTemporaryPassword = mTemporaryPassword;
    }

    public void setUserType(String mUserType) {
        this.mUserType = mUserType;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String mStatus) {
        this.mStatus = mStatus;
    }

    public void setIsEmailVerified(boolean isEmailVerified) {
        mIsEmailVerified = isEmailVerified;
    }

    public void setIsPhoneVerified(boolean isPhoneVerified) {
        mIsPhoneVerified = isPhoneVerified;
    }

    public void setPassword(final String password){
        mPassword = password;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        mProfilePicUrl = profilePicUrl;
    }

    public String getUserType() {
        return mUserType;
    }

    public String getTemporaryPassword() {
        return mTemporaryPassword;
    }

    public String getPassword(){
        return mPassword;
    }

    public boolean isEmailVerified(){
        return mIsEmailVerified;
    }

    public boolean isPhoneVerified(){
        return mIsPhoneVerified;
    }

    public String getPhoneNumber(){
        return mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

}
