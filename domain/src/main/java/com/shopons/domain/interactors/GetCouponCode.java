package com.shopons.domain.interactors;

import com.shopons.domain.Coupon;
import com.shopons.domain.StoreDetails;
import com.shopons.domain.executors.PostExecutionThread;
import com.shopons.domain.executors.ThreadExecutor;
import com.shopons.domain.repositories.StoreRepository;

import rx.Observable;

public class GetCouponCode extends UseCase<Coupon> {
    StoreRepository mStoreRepository;
    String authKey;
    String dealId;
    public GetCouponCode(String authKey,String dealId,StoreRepository storeRepository,
                           final ThreadExecutor threadExecutor,
                           final PostExecutionThread postExecutionThread)

    {
        super(threadExecutor,postExecutionThread);
        this.authKey=authKey;
        this.dealId=dealId;
        mStoreRepository=storeRepository;
    }
    @Override
    protected Observable<Coupon> buildUseCaseObservable() {
        return mStoreRepository.getCouponCode(authKey,dealId);
    }
}
