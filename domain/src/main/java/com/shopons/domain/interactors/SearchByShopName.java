package com.shopons.domain.interactors;

import com.shopons.domain.Location;
import com.shopons.domain.StoreDetails;
import com.shopons.domain.executors.PostExecutionThread;
import com.shopons.domain.executors.ThreadExecutor;
import com.shopons.domain.repositories.StoreRepository;

import java.util.List;

import rx.Observable;

/**
 * Created by komal on 29/3/16.
 */
public class SearchByShopName extends UseCase<List<StoreDetails>> {
    StoreRepository mStoreRepository;
    String query;
    int page_no;
    Location userLoc;

    public SearchByShopName(String query,Location userLoc,int page_no,StoreRepository storeRepository,
                            final ThreadExecutor threadExecutor,
                            final PostExecutionThread postExecutionThread)

    {
        super(threadExecutor,postExecutionThread);
        this.query=query;
        this.page_no=page_no;
        mStoreRepository=storeRepository;
        this.userLoc=userLoc;
    }

    public SearchByShopName(String query,int page_no,StoreRepository storeRepository,
                           final ThreadExecutor threadExecutor,
                           final PostExecutionThread postExecutionThread)

    {
        super(threadExecutor,postExecutionThread);
        this.query=query;
        this.page_no=page_no;
        mStoreRepository=storeRepository;
    }
    @Override
    protected Observable<List<StoreDetails>> buildUseCaseObservable() {
       // if(userLoc==null)
            return mStoreRepository.searchResults(query,page_no);
       // else
         //   return mStoreRepository.searchResults(query,userLoc,page_no);
    }
}
