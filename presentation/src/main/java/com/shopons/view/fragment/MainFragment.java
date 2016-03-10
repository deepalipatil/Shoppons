package com.shopons.view.fragment;


import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.shopons.R;
import com.shopons.domain.Location;
import com.shopons.presenter.LocationPresenter;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import rx.Subscriber;

/**
 * A simple {@link Fragment} subclass.
 */


public class MainFragment extends Fragment {
    public static final String TAG="####MainFragment";
    Button btnLoc;
    BroadcastReceiver mLocationReceiver;
    LocationPresenter mLocationPresenter;
    private boolean mFirstCalled=false;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment getInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        btnLoc=(Button)getActivity().findViewById(R.id.btn_location);
        View view=inflater.inflate(R.layout.fragment_main,container,false);

        mLocationReceiver= new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                checkGpsStatus();
            }
        };
        mLocationPresenter=new LocationPresenter(getActivity());

       return view;

    }


    @Override
    public void onResume() {
        super.onResume();

        if(GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity())!=ConnectionResult.SUCCESS)
        {
            Log.d(TAG,"Error connecting to service");
            return;
        }
        if(!mFirstCalled)
        {
            mFirstCalled=true;
            checkGpsStatus();
        }
        getActivity().registerReceiver(mLocationReceiver, new IntentFilter("android.location.PROVIDERS_CHANGED"));
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(mLocationReceiver);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mLocationPresenter!=null)
            mLocationPresenter.destroy();
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

    void checkGpsStatus()
    {
        if(isGpsOn(getActivity())==true)
        {
            mLocationPresenter.getUserLocation(new Subscriber<Location>() {
                @Override
                public void onCompleted() {
                    Log.d(TAG,"OnCompleted");

                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();

                }

                @Override
                public void onNext(Location location) {
                    Log.d(TAG,"OnNext");
                    Geocoder geocoder= new Geocoder(getActivity(), Locale.getDefault());
                    try {
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),
                                location.getLongitude(), 1);
                        String city = addresses.get(0).getLocality();
                        btnLoc.setText(city);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        else
        {
            showNoGpsDialog();
        }
    }
    void showNoGpsDialog()
    {
        MaterialDialog.Builder materialDialog = new MaterialDialog.Builder(getActivity())
                .title("Location Service Disabled").content("To detect your location  automatically, you need to enable Location services")
                .positiveText("Settings").negativeText("Dismiss")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                        materialDialog.dismiss();
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                }).onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                        materialDialog.dismiss();

                    }
                });
        materialDialog.show();
    }


}
