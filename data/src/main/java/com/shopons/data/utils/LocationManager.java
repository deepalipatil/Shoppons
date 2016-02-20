package com.shopons.data.utils;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
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



   public LocationManager(Context context){
        mGoogleApiClient=new GoogleApiClient.Builder(context)
               .addApi(LocationServices.API).addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();
        mlocationRequest= LocationRequest.create().
               setInterval(5000).setFastestInterval(3000);
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
                    if(userLocationInterface!=null)
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


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public void connect()
    {
        if(mGoogleApiClient.isConnected()==false)
        mGoogleApiClient.connect();
    }

}




