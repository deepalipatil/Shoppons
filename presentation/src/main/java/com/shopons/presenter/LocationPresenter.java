package com.shopons.presenter;

import android.content.Context;

import com.shopons.data.repository.LocationRepository;
import com.shopons.domain.Location;
import com.shopons.domain.interactors.GetUserLocation;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by komal on 20/2/16.
 */
public class LocationPresenter extends Presenter {

    LocationRepository mLocationRepository;

    LocationPresenter(Context context)
    {
        mLocationRepository=new LocationRepository(context);
    }

    void getUserLocation(Subscriber<Location> subscriber)
    {
        mSubscriptions.add(new GetUserLocation(mLocationRepository,
                mThreadExecutor,mPostExecutionThread).execute(subscriber));
    }
    @Override
    void resume() {

    }

    @Override
    void pause() {

    }

    @Override
    void destroy() {

    }
}
