package com.shopons.data.utils;

import android.text.TextUtils;
import android.util.Patterns;

import com.shopons.domain.User;

/**
 * Created by deepali on 8/3/16.
 */
public class Validation {

    private static final int MIN_NAME_LENGTH = 8;
    private static final int MAX_NAME_LENGTH = 30;
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int PHONE_NUMBER_LENGTH = 10;
    private static final int OTP_LENGTH = 10;
    private static final int MIN_PASSWORD_LENGTH_DELIVERY = 8;
    private static final int MAX_PASSWORD_LENGTH_DELIVERY = 20;


    public static boolean isValidEmailAddress(final String emailAddress) {
        return Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches();
    }

    public static void validateName(final String name) throws Exception {
        if (name == null) {
            throw new Exception("Name can not be blank");
        }
        if (!name.matches("[a-zA-Z ]+")) {
            throw new Exception("Only spaces and letters are allowed in name");
        }
        if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
            throw new Exception("Name must be 8 - 30 characters long!");
        }
    }

    /**
     * Helper method which will return us the error related to email
     *
     * @param email
     */
    public static void validateEmailAddress(final String email) throws Exception {
        if (TextUtils.isEmpty(email)) {
            throw new Exception("Email must not be blank");
        }
        if (!isValidEmailAddress(email)) {
            throw new Exception("Invalid email address");
        }
    }


    public static void validatePassword(final String password) throws Exception {
        if (TextUtils.isEmpty(password)) {
            throw new Exception("Password must not be blank");
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new Exception("Password must be of at least 8 characters");
        }
        if (!password.matches("[ #$%*)(!_A-Za-z0-9]+")) {
            throw new Exception("Password should contain only numbers, characters & space!");
        }
    }



    /*public static void validateUser(final User user) throws Exception {
        final StringBuilder errorStringBuilder = new StringBuilder();
        if (!user.isPhoneVerified()) {
            errorStringBuilder.append("You have not verified your phone yet");
            throw new Exception(errorStringBuilder.toString());
        }
        if (!errorStringBuilder.toString().isEmpty())
            throw new Exception(errorStringBuilder.toString());
    }*/



    public static void validatePhoneNumber(final String phoneNumber) throws Exception {
        if (TextUtils.isEmpty(phoneNumber.trim())) {
            throw new Exception("Phone number must be present!");
        }
        if (phoneNumber.trim().length() != PHONE_NUMBER_LENGTH) {
            throw new Exception("Phone number must be of 10 digits");
        }
        if (!Patterns.PHONE.matcher(phoneNumber).matches()) {
            throw new Exception("Invalid phone number entered!");
        }
    }


    public static void validateOtp(final String otp) throws Exception {
        if (TextUtils.isEmpty(otp)) {
            throw new Exception("OTP must be present!");
        }
        if (otp.trim().length() > OTP_LENGTH) {
            throw new Exception("OTP must be of " + OTP_LENGTH + " digits");
        }
    }


    public static void validatePasswordDelivery(final String password) throws Exception {
        if (password == null) {
            throw new Exception("Password must not be blank");
        }
        if (password.length() < MIN_PASSWORD_LENGTH_DELIVERY) {
            throw new Exception("Password must be of at least 8 characters");
        }
        if(password.length() > MAX_PASSWORD_LENGTH_DELIVERY){
            throw new Exception("Password must be less than 20 characters");
        }
       /* if (!password.matches("[A-Za-z0-9]+")) {
            throw new InvalidPasswordException("Password should contain only numbers and characters!");
        }*/
    }

}

