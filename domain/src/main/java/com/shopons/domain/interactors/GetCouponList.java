package com.shopons.domain.interactors;

import com.shopons.domain.Coupon;
import com.shopons.domain.executors.PostExecutionThread;
import com.shopons.domain.executors.ThreadExecutor;
import com.shopons.domain.repositories.CouponRepository;

import java.util.List;

import rx.Observable;

public class GetCouponList extends UseCase<List<Coupon>> {

    String authkey;
    CouponRepository mCouponRespository;
    public GetCouponList(String authKey,CouponRepository mCouponRespository,
                         final ThreadExecutor threadExecutor, final PostExecutionThread postExecutionThread)
    {
        super(threadExecutor,postExecutionThread);
        this.authkey=authKey;
        this.mCouponRespository=mCouponRespository;
    }
    @Override
    protected Observable<List<Coupon>> buildUseCaseObservable() {
        return mCouponRespository.getCouponList(authkey);
    }
}
