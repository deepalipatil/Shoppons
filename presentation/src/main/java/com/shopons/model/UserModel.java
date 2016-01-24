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

    private long mId;
    private String mEmailAddress;
    private String mName;
    private String mProfilePicUrl;

    public UserModel(long id, String emailAddress, String name, String profilePicUrl) {
        mId = id;
        mEmailAddress = emailAddress;
        mName = name;
        mProfilePicUrl = profilePicUrl;
    }

    protected UserModel(Parcel in) {
        mId = in.readLong();
        mEmailAddress = in.readString();
        mName = in.readString();
        mProfilePicUrl = in.readString();
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
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mId);
        dest.writeString(mEmailAddress);
        dest.writeString(mName);
        dest.writeString(mProfilePicUrl);
    }
}
