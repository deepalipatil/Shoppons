package com.shopons.presenter;

import com.shopons.domain.Location;
import com.shopons.domain.Store;
import com.shopons.domain.StoreDetails;
import com.shopons.domain.interactors.GetStoreDetails;
import com.shopons.domain.interactors.GetStoreListingByLocation;
import com.shopons.domain.interactors.SearchByShopName;
import com.shopons.domain.repositories.StoreRepository;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by komal on 1/3/16.
 */
public class StorePresenter extends Presenter {
    StoreRepository mStoreRepository=new com.shopons.data.repository.StoreRepository();

    public void getStore(double latitude, double longitude, int pageNo,Subscriber<List<Store>> subscriber)
    {
     mSubscriptions.add(new GetStoreListingByLocation(latitude,longitude,pageNo,mStoreRepository,
             mThreadExecutor,mPostExecutionThread).execute(subscriber));

    }

    public void getStoreDetails(String id, Subscriber<StoreDetails> subscriber)
    {
        mSubscriptions.add(new GetStoreDetails(id,mStoreRepository,mThreadExecutor,
                mPostExecutionThread).execute(subscriber));
    }

    public void searchResults(String query,int page_no,Subscriber<List<StoreDetails>> subscriber)
    {
        mSubscriptions.add(new SearchByShopName(query,page_no,mStoreRepository,
                mThreadExecutor,mPostExecutionThread).execute(subscriber));
    }

    public void searchResults(String query,Location userLoc,int page_no,Subscriber<List<StoreDetails>> subscriber)
    {
        mSubscriptions.add(new SearchByShopName(query,userLoc,page_no,mStoreRepository,
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
