package com.shopons.domain.repositories;

import com.shopons.domain.Coupon;

import java.util.List;

import rx.Observable;

/**
 * Created by komal on 12/5/16.
 */
public interface CouponRepository {

    Observable<Coupon>getCouponCode(String authKey,String DealId);
    Observable<List<Coupon>>getCouponList(String authKey);
}
