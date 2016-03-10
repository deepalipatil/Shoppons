package com.shopons.data.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by deepali on 8/3/16.
 */
public class ErrorMsgEntity {

    @SerializedName("message")
    private String mMessage;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }
}
