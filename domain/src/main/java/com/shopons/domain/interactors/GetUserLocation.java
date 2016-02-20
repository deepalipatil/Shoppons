package com.shopons.domain.interactors;

import com.shopons.domain.Location;
import com.shopons.domain.executors.PostExecutionThread;
import com.shopons.domain.executors.ThreadExecutor;
import com.shopons.domain.repositories.GeneralRepository;
import com.shopons.domain.repositories.LocationRepository;

import rx.Observable;

/**
 * Created by komal on 20/2/16.
 */
public class GetUserLocation extends UseCase<Location> {
    LocationRepository mLocationRepository;

    public GetUserLocation(final LocationRepository locationRepository,final ThreadExecutor threadExecutor,
                    final PostExecutionThread postExecutionThread)
    {
        super(threadExecutor, postExecutionThread);
        mLocationRepository=locationRepository;
    }
    @Override
    protected Observable<Location> buildUseCaseObservable() {
        return mLocationRepository.getUserLocation();
    }
}
