package com.shopons.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * User model parcelable to trade in-between the activities
 *
 * @author : Kaustubh Deshmukh
 * @date : 01/10/15 : 8:54 PM
 * @email : akshay@betacraft.co
 */
public final class UserModel implements Parcelable {

    private String mId;
    private String mEmailAddress;
    private String mName;
    private String mFullName;
    private String mPhoneNumber;
    private String mPassword;
    private String mAuthKey;
    private boolean mIsLoggedIn;

    public UserModel(){}

    public UserModel(final String authKey, final String fullName, final String emailAddress, final String phoneNumber,
                     final String password, final String id) {
        this.mFullName = fullName;
        mEmailAddress = emailAddress;
        mPhoneNumber = phoneNumber;
        mPassword = password;
        mId = id;
        mAuthKey = authKey;
    }

    protected UserModel(Parcel in) {
        mId = in.readString();
        mFullName = in.readString();
        mEmailAddress = in.readString();
        mPhoneNumber = in.readString();
        mPassword = in.readString();
        mAuthKey = in.readString();
        mIsLoggedIn = in.readByte() != 0;
    }


    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel in) {
            return new UserModel(in);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(mId);
        parcel.writeString(mName);
        parcel.writeString(mFullName);
        parcel.writeString(mEmailAddress);
        parcel.writeString(mPhoneNumber);
        parcel.writeString(mPassword);
        parcel.writeString(mAuthKey);
        parcel.writeByte((byte) (mIsLoggedIn ? 1 : 0));
    }

    public void setId(final String id){
        mId = id;
    }

    public String getId() {
        return mId;
    }

    public void setAuthKey(String authKey) {
        mAuthKey = authKey;
    }

    public String getFullName() {
        return mFullName;
    }

    public String getEmailAddress() {
        return mEmailAddress;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public String getPassword() {
        return mPassword;
    }

    public String getAuthKey() {
        return mAuthKey;
    }

}
