package com.shopons.domain.interactors;

import com.shopons.domain.Store;
import com.shopons.domain.executors.PostExecutionThread;
import com.shopons.domain.executors.ThreadExecutor;
import com.shopons.domain.repositories.GeneralRepository;
import com.shopons.domain.repositories.StoreRepository;

import java.util.List;

import rx.Observable;

/**
 * Created by komal on 24/2/16.
 */
public class GetStoreListingByLocation extends UseCase<List<Store>> {
    StoreRepository mStoreRepository;
    double latitude, longitude;
    int pageNo;

    public GetStoreListingByLocation(final double latitude, final double longitude,int pageNo,
                              final StoreRepository mStoreRepository,
                              final ThreadExecutor threadExecutor,
                              final PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.latitude=latitude;
        this.longitude=longitude;
        this.pageNo=pageNo;
        this.mStoreRepository=mStoreRepository;

    }

    @Override
    protected Observable<List<Store>> buildUseCaseObservable() {
        return mStoreRepository.getStoreListing(longitude,latitude,pageNo);
    }
}
