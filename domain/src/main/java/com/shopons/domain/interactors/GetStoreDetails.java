package com.shopons.domain.interactors;

import com.shopons.domain.StoreDetails;
import com.shopons.domain.executors.PostExecutionThread;
import com.shopons.domain.executors.ThreadExecutor;
import com.shopons.domain.repositories.StoreRepository;

import rx.Observable;

public class GetStoreDetails extends UseCase<StoreDetails> {
    StoreRepository mStoreRepository;
    String id;
    public GetStoreDetails(String id,StoreRepository storeRepository,
                           final ThreadExecutor threadExecutor,
                           final PostExecutionThread postExecutionThread)

    {
        super(threadExecutor,postExecutionThread);
        this.id=id;
        mStoreRepository=storeRepository;
    }
    @Override
    protected Observable<StoreDetails> buildUseCaseObservable() {
        return mStoreRepository.getStoreDetails(id);
    }
}
