package com.shopons.presenter;

import com.shopons.domain.Coupon;
import com.shopons.domain.interactors.GenerateCoupon;
import com.shopons.domain.interactors.GetCouponList;
import com.shopons.domain.repositories.CouponRepository;


import rx.Subscriber;

/**
 * Created by komal on 13/5/16.
 */
public class CouponPresenter extends Presenter {

    CouponRepository mCouponRepository=new com.shopons.data.repository.CouponRepository();

    /*public void generateCoupon(String authKey, String dealId, Subscriber<Coupon> subscriber)
    {
        mSubscriptions.add(new GenerateCoupon(authKey,dealId,mCouponRepository,
                mThreadExecutor,mPostExecutionThread).execute(subscriber));
    }*/

    public void getCouponList(String authKey,Subscriber<Coupon> subscriber)
    {
        mSubscriptions.add(new GetCouponList(authKey,mCouponRepository,
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
