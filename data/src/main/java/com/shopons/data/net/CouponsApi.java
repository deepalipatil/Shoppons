package com.shopons.data.net;

import com.shopons.data.entities.CouponEntity;
import com.shopons.data.entities.StoreEntity;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

public interface CouponsApi {

    @PUT("coupon/{deal_id}")
    public Observable<CouponEntity>generateCoupon(@Header("Auth-Key") String authKey,@Path("deal_id") String dealId);

    @GET("coupons")
    public Observable<List<CouponEntity>>getCouponList(@Header("Auth-Key") String authKey);


    @Headers({
            "Accept : application/json",
            "Content-Type : application/json"
    })


    @GET("stores")
    public Observable<StoreEntity> getStoreListing(@Query("long") double longitude,
                                                   @Query("lat") double latitude,@Query("page_id") int pageNo);

}
