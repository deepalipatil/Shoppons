package com.shopons.domain.interactors;

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
        return mStoreRepository.searchResults(query,page_no);
    }
}
