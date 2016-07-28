package com.shopons.data.net;

import com.shopons.data.entities.AppVersionEntity;
import com.shopons.data.entities.CouponEntity;

import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.PUT;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by komal on 17/5/16.
 */
public interface CouponApi {

    @GET("coupon/{deal_id}")
    public Observable<CouponEntity> generateCoupon(@Header("Auth-Key") String authKey,@Path("deal_id") String dealId);

    @Headers({
            "Accept : application/json",
            "Content-Type : application/json"
    })

    @GET("app_version/user/android/{version}")
    public Observable<AppVersionEntity> getAppVersion(@Path("version") int version);
}
