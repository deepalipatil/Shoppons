package com.shopons.utils;

import android.content.Intent;
import android.net.Uri;

/**
 * Created by deepali on 25/2/16.
 */
public class Intents {
    private static String INTENT_PREFIX;

    private static final Uri CONNECT = Uri.parse(INTENT_PREFIX + "/connect/");
    private static final Uri SOCIAL_LOGIN = Uri.parse(INTENT_PREFIX + "/social_login/");
    private static final Uri HOME = Uri.parse(INTENT_PREFIX + "/home/");
    private static final Uri SIGN_UP = Uri.parse(INTENT_PREFIX + "/sign_up/");
    private static final Uri LOGIN = Uri.parse(INTENT_PREFIX + "/login/");
    private static final Uri SEARCH_LOCATION = Uri.parse(INTENT_PREFIX + "/search_location/");
    private static final Uri BOOKING = Uri.parse(INTENT_PREFIX + "/booking/");
    private static final Uri ACCEPT_DOC = Uri.parse(INTENT_PREFIX + "/accept_doc/");
    private static final Uri PAYMENT = Uri.parse(INTENT_PREFIX + "/payment/");
    private static final Uri BOOKING_SUCCESS = Uri.parse(INTENT_PREFIX + "/booking_success/");
    private static final Uri EDIT_PROFILE = Uri.parse(INTENT_PREFIX + "/edit_profile/");
    private static final Uri SHOW_PROFILE = Uri.parse(INTENT_PREFIX + "/show_profile/");
    private static final Uri CHANGE_PASSWORD = Uri.parse(INTENT_PREFIX + "/change_password/");
    private static final Uri REVIEW = Uri.parse(INTENT_PREFIX + "/review/");
    private static final Uri OTP = Uri.parse(INTENT_PREFIX + "/otp/");
    private static final Uri INVOICE = Uri.parse(INTENT_PREFIX + "/invoice/");
    private static final Uri CHECKLIST = Uri.parse(INTENT_PREFIX + "/checklist/");
    private static final Uri CALL_US = Uri.parse(INTENT_PREFIX + "/call_us/");
    private static final Uri IN_RIDE = Uri.parse(INTENT_PREFIX + "/in_ride/");
    private static final Uri TRACK = Uri.parse(INTENT_PREFIX + "/track/");
    private static final Uri MAKE_PAYMENT = Uri.parse(INTENT_PREFIX + "/make_payment/");
    private static final Uri SHOW_LICENSE = Uri.parse(INTENT_PREFIX + "/show_license/");

    public static Intent getConnectIntent() {
        return new Intent(Intent.ACTION_VIEW).setData(CONNECT);
    }

    public static Intent getSocialIntent() {
        return new Intent(Intent.ACTION_VIEW).setData(SOCIAL_LOGIN);
    }

    public static Intent getHomeIntent() {
        return new Intent(Intent.ACTION_VIEW).setData(HOME);
    }

    public static Intent getShowProfileIntent() {
        return new Intent(Intent.ACTION_VIEW).setData(SHOW_PROFILE);
    }
}
