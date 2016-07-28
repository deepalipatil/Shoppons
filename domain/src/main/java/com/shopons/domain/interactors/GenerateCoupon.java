package com.shopons.domain.interactors;

import com.shopons.domain.Coupon;
import com.shopons.domain.executors.PostExecutionThread;
import com.shopons.domain.executors.ThreadExecutor;
import com.shopons.domain.repositories.CouponRepository;
import com.shopons.domain.repositories.StoreRepository;

import rx.Observable;

/**
 * Created by komal on 13/5/16.
 */
public class GenerateCoupon extends UseCase<Coupon> {
    String authKey,dealId;
    StoreRepository mCouponRepository;

    public GenerateCoupon(String authKey,String dealId, StoreRepository mCouponRepository,
                          final ThreadExecutor threadExecutor, final PostExecutionThread postExecutionThread)
    {
        super(threadExecutor,postExecutionThread);
        this.authKey=authKey;
        this.dealId=dealId;
        this.mCouponRepository=mCouponRepository;
    }
    @Override
    protected Observable<Coupon> buildUseCaseObservable() {
        return mCouponRepository.getCouponCode(authKey,dealId);
    }
}
