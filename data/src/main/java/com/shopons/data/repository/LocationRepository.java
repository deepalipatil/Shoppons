package com.shopons.data.repository;

import android.content.Context;
import android.util.Log;

import com.shopons.data.interfaces.UserLocationInterface;
import com.shopons.data.utils.LocationManager;
import com.shopons.domain.Location;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by komal on 19/2/16.
 */
public class LocationRepository implements com.shopons.domain.repositories.LocationRepository{

    Context mContext;
    public LocationRepository(Context context)
    {
        mContext=context;
    }
    @Override
    public Observable<Location> getUserLocation() {


        return Observable.create(new Observable.OnSubscribe<Location>() {
            @Override
            public void call(final Subscriber<? super Location> subscriber) {
                Log.d("####LocationRepostory", "Inside get Location");
                final LocationManager locationManager = new LocationManager(mContext);
                //locationManager.connect();
                locationManager.setUserLocationInterface(new UserLocationInterface() {
                    @Override
                    public void onUserLocation(Location location) {
                        subscriber.onNext(location);
                        subscriber.onCompleted();
                    }
                });

            }

    });

    }
}
