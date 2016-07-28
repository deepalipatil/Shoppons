package com.shopons.data.repository;

import android.util.Log;
import com.shopons.data.entities.CouponEntity;
import com.shopons.data.mappers.CouponMapper;
import com.shopons.data.net.CouponsApi;
import com.shopons.data.utils.Urls;
import com.shopons.domain.Coupon;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;
import java.util.ArrayList;
import java.util.List;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.functions.Func1;

public class Temp implements com.shopons.domain.repositories.CouponRepository {

     CouponsApi couponsApi;
     public Temp()
     {
         HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
// set your desired log level
         logging.setLevel(HttpLoggingInterceptor.Level.BODY);
         OkHttpClient httpClient = new OkHttpClient();
// add your other interceptors …
// add logging as last interceptor
         httpClient.interceptors().add(logging);

         Retrofit retrofit=new Retrofit.Builder().baseUrl(Urls.baseUrl)
                 .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                 .addConverterFactory(GsonConverterFactory.create())
                 .client(httpClient).build();
         couponsApi=retrofit.create(CouponsApi.class);
         Log.d("#####CouponRepository","Inside constructor!!!");
     }

    @Override
    public Observable<Coupon> getCouponCode(String authKey,String dealId) {
        Log.d("#####CouponRepository","HERE!!!!");
        couponsApi.getStoreListing(10,10,0);
        Log.d("#####CouponRepository","NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN");
        return couponsApi.generateCoupon(authKey,dealId).map(new Func1<CouponEntity, Coupon>() {
            @Override
            public Coupon call(CouponEntity couponsEntity) {
                Log.d("#####CouponRepository","INNNN!!");
                return CouponMapper.transform(couponsEntity);
            }
        });
    }

    @Override
    public Observable<List<Coupon>> getCouponList(String authKey) {
        return couponsApi.getCouponList(authKey).map(new Func1<List<CouponEntity>, List<Coupon>>() {
            @Override
            public List<Coupon> call(List<CouponEntity> couponEntities) {
                List<Coupon> coupons=new ArrayList<Coupon>();
                for(CouponEntity element:couponEntities)
                {
                    coupons.add(CouponMapper.transform(element));
                }
                return coupons;
            }
        });
    }


}
