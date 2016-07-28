package com.shopons.data.mappers;

import android.util.Log;

import com.shopons.data.entities.CouponEntity;
import com.shopons.domain.Coupon;

public class CouponMapper {

    public static Coupon transform(CouponEntity couponEntity)
    {
        Log.d("####CouponMapper","Started mapping");
        Coupon coupon=new Coupon();
        coupon.setCoupon_code(couponEntity.getCoupon_code());
        Log.d("####CouponMapper","Done mapping");
        return coupon;
    }
}
