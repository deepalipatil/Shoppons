package com.shopons.data.repository;

import android.util.Log;

import com.shopons.data.entities.CouponEntity;
import com.shopons.data.mappers.CouponMapper;
import com.shopons.data.net.CouponApi;
import com.shopons.data.utils.Urls;
import com.shopons.domain.Coupon;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.util.List;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.functions.Func1;


public class CouponRepository implements com.shopons.domain.repositories.CouponRepository {

    CouponApi mCouponApi;

    public CouponRepository()
    {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
// set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient();
// add your other interceptors …
// add logging as last interceptor
        httpClient.interceptors().add(logging);

        Retrofit retrofit=new Retrofit.Builder().baseUrl(Urls.shopons_base)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient).build();
        mCouponApi=retrofit.create(CouponApi.class);
    }


    @Override
    public Observable<Coupon> getCouponCode(String authKey, String DealId) {

        Log.d("###Coupon Repository","Inside method of getCouponCode");
        mCouponApi.generateCoupon(authKey,DealId);
        Log.d("###Coupon Repository","NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN");
        return mCouponApi.generateCoupon(authKey,DealId).map(new Func1<CouponEntity, Coupon>() {
            @Override
            public Coupon call(CouponEntity couponEntity) {
                Log.d("###Coupon Repository","Mapping Info");
                return CouponMapper.transform(couponEntity);
            }
        });


        //return null;
    }

    @Override
    public Observable<List<Coupon>> getCouponList(String authKey) {
        return null;
    }
}
