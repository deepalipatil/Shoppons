package com.shopons.model;

import android.os.Parcel;
import android.os.Parcelable;

public final class UserModel implements Parcelable {

    private String mId;
    private String mEmailAddress;
    private String mName;
    private String mFullName;
    private String mAuthKey;
    private boolean mIsLoggedIn;

    public UserModel(){}

    public UserModel(final String authKey, final String fullName, final String emailAddress, final String id) {
        this.mFullName = fullName;
        mEmailAddress = emailAddress;
        mId = id;
        mAuthKey = authKey;
    }

    protected UserModel(Parcel in) {
        mId = in.readString();
        mFullName = in.readString();
        mEmailAddress = in.readString();
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

    public String getAuthKey() {
        return mAuthKey;
    }

}
