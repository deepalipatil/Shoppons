package com.shopons.data.utils;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.shopons.data.interfaces.UserLocationInterface;

/**
 * Created by komal on 20/2/16.
 */
public class LocationManager implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener {
    GoogleApiClient mGoogleApiClient;
    Location mlocation;
    UserLocationInterface userLocationInterface;
    LocationRequest mlocationRequest;
    Context mContext;



   public LocationManager(Context context){
       mGoogleApiClient = new GoogleApiClient.Builder(context)
               .addApi(LocationServices.API)
               .addOnConnectionFailedListener(this)
               .addConnectionCallbacks(this)
               .build();
       mContext = context;
       mlocationRequest = LocationRequest.create()
               .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
               .setInterval(500)
               .setFastestInterval(300);
       mGoogleApiClient.connect();
   }

    public void setUserLocationInterface(UserLocationInterface userLocationInterface) {
        this.userLocationInterface = userLocationInterface;
    }

    @Override
    public void onConnected(Bundle bundle) {
        mlocation= LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if(mlocation==null)
        {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mlocationRequest, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    mlocation = location;
                    if(userLocationInterface!=null && mlocation!=null)
                    {
                        com.shopons.domain.Location userLocation=new com.shopons.domain.Location(location.getLatitude(),
                                                            location.getLongitude());
                        userLocationInterface.onUserLocation(userLocation);
                        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,this);
                        if(mGoogleApiClient!=null && mGoogleApiClient.isConnected())
                            mGoogleApiClient.disconnect();
                    }

                }
            });

        }
        else
        {
            com.shopons.domain.Location userLocation=new com.shopons.domain.Location(mlocation.getLatitude(),
                    mlocation.getLongitude());
            userLocationInterface.onUserLocation(userLocation);
            if(mGoogleApiClient!=null && mGoogleApiClient.isConnected())
                mGoogleApiClient.disconnect();
        }


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d("####LocationManager", " OnConnectionFailed! " + connectionResult.toString());
        if (!connectionResult.hasResolution()) {
            // show the localized error dialog.
            GooglePlayServicesUtil.getErrorDialog(connectionResult.getErrorCode(),
                    ((Activity) mContext), 0).show();
        }
    }

    public void Myconnect()
    {
        if(mGoogleApiClient.isConnected()==false)
        mGoogleApiClient.connect();
    }

}




