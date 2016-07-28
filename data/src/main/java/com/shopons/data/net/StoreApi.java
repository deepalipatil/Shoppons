package com.shopons.data.net;

import com.shopons.data.entities.CouponEntity;
import com.shopons.data.entities.SearchResultEntity;
import com.shopons.data.entities.StoreDetailsEntity;
import com.shopons.data.entities.StoreEntity;
import com.shopons.data.entities.StoreInfo;

import java.util.List;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

public interface StoreApi {

    @Headers({
            "Accept : application/json",
            "Content-Type : application/json"
    })


    @GET("stores")
    public Observable<StoreEntity> getStoreListing(@Query("long") double longitude,
                                                   @Query("lat") double latitude,@Query("page_id") int pageNo);
    @GET("store/{store_id}")
    public Observable<StoreDetailsEntity> getStoreDetails(@Path("store_id") String storeId);

    @GET("stores/search/{query}")
    public Observable<SearchResultEntity> searchResults(@Path("query") String query,@Query("page_id") int page_id);

    @GET("stores/search/{query}")
    public Observable<SearchResultEntity> searchResults(@Path("query") String query,@Query("long") double longitude,
                                                        @Query("lat") double latitude,@Query("page_id") int page_id);

    @Headers({
            "Accept : application/json",
            "Content-Type : application/json"
    })


    @PUT("coupon/{deal_id}")
    public Observable<CouponEntity> generateCoupon(@Header("Auth-Key") String authKey,@Path("deal_id") String dealId);



}
