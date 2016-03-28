package com.shopons.data.net;

import com.shopons.data.entities.StoreDetailsEntity;
import com.shopons.data.entities.StoreEntity;
import com.shopons.data.entities.StoreInfo;

import java.util.List;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by komal on 24/2/16.
 */
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



}
