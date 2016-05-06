package com.shopons.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shopons.utils.ExceptionTypes;
import com.shopons.utils.FontUtils;

import javax.security.auth.callback.CallbackHandler;

/**
 * Base fragment
 *
 * @author : Kaustubh Deshmukh
 * @date : 01/10/15 : 8:54 PM
 * @email : akshay@betacraft.co
 */
public abstract class  BaseFragment extends DialogFragment {

    private static final String TAG = "##BaseFragment";
    protected CallbackHandler mHandler;

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
    public void onDetach() {
        super.onDetach();
    }



    public static void setBoldFont(final TextView textView) {
        textView.setTypeface(FontUtils.getFonts(textView.getContext(),"Sansation_Bold.ttf"));
    }

    public static void setLightFont(final TextView textView) {
        textView.setTypeface(FontUtils.getFonts(textView.getContext(),"Sansation_Light.ttf"));
    }

    public static void setRegularFont(final TextView textView) {
        textView.setTypeface(FontUtils.getFonts(textView.getContext(),"Sansation_Regular.ttf"));
    }

    public static void setRegularFont(final TextInputLayout textInputLayout) {
        textInputLayout.setTypeface(FontUtils.getFonts(textInputLayout.getContext(),"Arcon-Regular.otf"));
    }

    public static void setRegularFont(final EditText editText) {
        editText.setTypeface(FontUtils.getFonts(editText.getContext(), "Arcon-Regular.otf"));
    }

    protected View tryInflate(String name, Context context, AttributeSet attrs) {
        LayoutInflater li = LayoutInflater.from(context);
        View v = null;
        try {
            v = li.createView(name, null, attrs);
        } catch (Exception e) {
            try {
                v = li.createView("android.widget." + name, null, attrs);
            } catch (Exception e1) {
            }
        }
        return v;
    }

    public static void setTypeFace(TextView tv) {
        tv.setTypeface(FontUtils.getFonts(tv.getContext(), "Arcon-Regular.otf"));
    }


    boolean isGpsOn(Context context)
    {
        String allowedLocationProviders =
                Settings.System.getString(context.getContentResolver(),
                        Settings.System.LOCATION_PROVIDERS_ALLOWED);
        if (allowedLocationProviders == null) {
            allowedLocationProviders = "";
        }
        return allowedLocationProviders.contains(LocationManager.GPS_PROVIDER);
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
