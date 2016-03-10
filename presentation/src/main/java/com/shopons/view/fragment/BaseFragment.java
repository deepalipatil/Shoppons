package com.shopons.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.shopons.domain.User;
import com.shopons.domain.constants.Constants;
import com.shopons.utils.ExceptionTypes;
import com.shopons.view.activity.SocialLoginActivity;

/**
 * Base fragment
 *
 * @author : Kaustubh Deshmukh
 * @date : 01/10/15 : 8:54 PM
 * @email : akshay@betacraft.co
 */
public class BaseFragment extends DialogFragment {

    private static final String TAG = "##BaseFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected boolean isConnected() {
        final ConnectivityManager cm = (ConnectivityManager) getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }

    public ExceptionTypes getExceptionType(final Throwable cause) {
        if (cause.getMessage() == null ||
                cause.getMessage().toLowerCase().contains("no address") ||
                cause.getMessage().toLowerCase().contains("connection refused") ||
                cause.getMessage().contains("java.net.UnknownHostException") ||
                cause.getMessage().toLowerCase().contains("timed out") ||
                cause.getMessage().toLowerCase().contains("time out")) {
            return ExceptionTypes.NOINTERNET;
        }
        return null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data == null)
            return;
        int response = data.getIntExtra("result", 0);
        if (response == SocialLoginActivity.LOGIN_SUCCESS) {
            final User user = new User();
            final SocialLoginFragment socialLoginFragment = new SocialLoginFragment();
            switch (data.getIntExtra("social_network", 0)){
                case SocialLoginActivity.FACEBOOK:
                    user.setFullName(data.getStringExtra(Constants.NAME));
                    user.setEmailAddress(data.getStringExtra(Constants.EMAIL));
                    user.setIsFacebook(true);
                    user.setIsGplus(false);
                    user.setFacebookToken(data.getStringExtra(Constants.TOKEN));
                    user.setFacebookId(data.getLongExtra(Constants.ID, 0));
                    socialLoginFragment.facebookLogin(user);
                    break;

                case SocialLoginActivity.GOOGLE_PLUS:
                    user.setFullName(data.getStringExtra(Constants.NAME));
                    user.setEmailAddress(data.getStringExtra(Constants.EMAIL));
                    user.setIsFacebook(false);
                    user.setIsGplus(true);
                    user.setId(data.getStringExtra(Constants.ID));
                    user.setGooglePlusToken(data.getStringExtra(Constants.TOKEN));
                    socialLoginFragment.googlePlusLogin(user);
                    break;
            }

        } else {
            //TODO change this
            Toast.makeText(getActivity(), "There was some issue with social login, " +
                    "please try again !!", Toast.LENGTH_LONG).show();
        }
    }

    protected void hideSoftKeyboard() {
        final InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if(getActivity().getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                    0);
        }
    }

    protected void openCameraIntent() {
        final Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, 2);
        } else {
            Toast.makeText(getActivity(), "Could not open Camera!", Toast.LENGTH_SHORT).show();
        }
    }
}
