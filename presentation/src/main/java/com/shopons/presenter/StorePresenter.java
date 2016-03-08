package com.shopons.presenter;

import com.shopons.domain.Store;
import com.shopons.domain.interactors.GetStoreListingByLocation;
import com.shopons.domain.repositories.StoreRepository;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by komal on 1/3/16.
 */
public class StorePresenter extends Presenter {
    StoreRepository mStoreRepository=new com.shopons.data.repository.StoreRepository();

    public void getStore(double latitude, double longitude, Subscriber<List<Store>> subscriber)
    {
     mSubscriptions.add(new GetStoreListingByLocation(latitude,longitude,mStoreRepository,
             mThreadExecutor,mPostExecutionThread).execute(subscriber));

    }

    @Override
    void resume() {
        cancelRunningUseCases();
    }

    @Override
    void pause() {
        cancelRunningUseCases();
    }

    @Override
    void destroy() {
        cancelRunningUseCases();
    }
}
