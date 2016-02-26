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

    public LocationPresenter(Context context)
    {
        mLocationRepository=new LocationRepository(context);
    }

    public void getUserLocation(Subscriber<Location> subscriber)
    {
        mSubscriptions.add(new GetUserLocation(mLocationRepository,
                mThreadExecutor,mPostExecutionThread).execute(subscriber));
    }
    @Override
    public void resume() {
        cancelRunningUseCases();

    }

    @Override
    public void pause() {
        cancelRunningUseCases();
    }

    @Override
    public void destroy() {
        cancelRunningUseCases();
    }
}
