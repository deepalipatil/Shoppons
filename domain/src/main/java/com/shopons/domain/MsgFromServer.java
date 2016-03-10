package com.shopons.domain;

/**
 * Created by deepali on 8/3/16.
 */
public class MsgFromServer {

    private String mMessage;
    private boolean mSuccess;

    public MsgFromServer(final String message, final boolean success){
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
