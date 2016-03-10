package com.shopons.data.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by deepali on 8/3/16.
 */
public class MsgEntity {

    @SerializedName("message")
    private String mMessage;

    @SerializedName("success")
    private boolean mSuccess;

    public MsgEntity(final String message, final boolean success){
        mMessage = message;
        mSuccess = success;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public boolean isSuccess() {
        return mSuccess;
    }

    public void setSuccess(boolean success) {
        mSuccess = success;
    }
}
