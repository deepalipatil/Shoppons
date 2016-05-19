package com.shopons.data.repository;


import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shopons.data.deserializer.SearchResultDeserializer;
import com.shopons.data.deserializer.StoreDeserializer;
import com.shopons.data.deserializer.StoreDetailsDeserializer;
import com.shopons.data.deserializer.StoreEntityDeserializer;
import com.shopons.data.entities.AppVersionEntity;
import com.shopons.data.entities.CouponEntity;
import com.shopons.data.entities.SearchResultEntity;
import com.shopons.data.entities.StoreDetailsEntity;
import com.shopons.data.entities.StoreEntity;
import com.shopons.data.entities.StoreInfo;
import com.shopons.data.mappers.AppVersionMapper;
import com.shopons.data.mappers.CouponMapper;
import com.shopons.data.mappers.StoreDetailsMapper;
import com.shopons.data.mappers.StoreMapper;
import com.shopons.data.net.StoreApi;
import com.shopons.data.utils.Urls;
import com.shopons.domain.AppVersion;
import com.shopons.domain.Coupon;
import com.shopons.domain.Location;
import com.shopons.domain.Store;
import com.shopons.domain.StoreDetails;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by komal on 24/2/16.
 */
public class StoreRepository implements com.shopons.domain.repositories.StoreRepository {

   StoreApi mStoreApi;


    public StoreRepository(){

        GsonBuilder gsonBuilder=new GsonBuilder()
                .registerTypeAdapter(StoreEntity.class, new StoreEntityDeserializer())
                .registerTypeAdapter(StoreInfo.class, new StoreDeserializer())
                .registerTypeAdapter(StoreDetailsEntity.class, new StoreDetailsDeserializer())
                .registerTypeAdapter(SearchResultEntity.class, new SearchResultDeserializer());
        Gson gson=gsonBuilder.create();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
// set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        final OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(60, TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(60, TimeUnit.SECONDS);
// add your other interceptors …
// add logging as last interceptor
        okHttpClient.interceptors().add(logging);

        Retrofit retrofit=new Retrofit.Builder().baseUrl(Urls.shopons_base)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson)).build();
        mStoreApi=retrofit.create(StoreApi.class);
    }

    public Observable<List<Store>> getStoreListing(double longitude,double  latitude, int pageNo) {

        final List<Store> stores = new ArrayList<Store>();
        return mStoreApi.getStoreListing(longitude, latitude,pageNo).map(new Func1<List<StoreInfo>, List<Store>>() {
            @Override
            public List<Store> call(List<StoreInfo> list) {
                if(list!=null) {
                    for (StoreInfo element : list) {
                        stores.add(StoreMapper.transform(element));
                    }
                    return stores;
                }
                return null;
            }


        });

    }
    public Observable<StoreDetails> getStoreDetails(String storeId)
    {
        Log.d("TESTINGG","GJGJGGHJGGJGGJHGGHGHGHG");
        mStoreApi.generateCoupon(" 25efc6bf-4cde-4868-986b-faed2b7b8bf5","5727193e5132624542dab6cc");
        Log.d("TESTINGG","############################################################################");
        return  mStoreApi.getStoreDetails(storeId).map(new Func1<StoreDetailsEntity, StoreDetails>() {
            @Override
            public StoreDetails call(StoreDetailsEntity storeDetailsEntity) {
               return StoreDetailsMapper.transform(storeDetailsEntity);
            }
        });
    }

    public Observable<List<StoreDetails>> searchResults(String query,int page_no)
    {
        Log.d("##StoreRepository","Inside search Results");
        final List<StoreDetails> searchResult=new ArrayList<>();
        return mStoreApi.searchResults(query,page_no).map(new Func1<List<StoreDetailsEntity>, List<StoreDetails>>() {

            @Override
            public List<StoreDetails> call(List<StoreDetailsEntity> list) {

                if(list!=null) {
                    for (StoreDetailsEntity element : list) {
                        searchResult.add(StoreDetailsMapper.transform(element));
                    }
                    Log.d("#####StoreRepository","No of results"+searchResult.size());
                    return searchResult;
                }

                return null;
            }
        });
    }

    public Observable<List<StoreDetails>> searchResults(String query,Location userLoc,int page_no)
    {
        Log.d("##StoreRepository","Inside search Results");
        final List<StoreDetails> searchResult=new ArrayList<>();
        return mStoreApi.searchResults(query,userLoc.getLongitude(),userLoc.getLatitude(),page_no).map(new Func1<List<StoreDetailsEntity>, List<StoreDetails>>() {

            @Override
            public List<StoreDetails> call(List<StoreDetailsEntity> list) {

                if(list!=null) {
                    for (StoreDetailsEntity element : list) {
                        searchResult.add(StoreDetailsMapper.transform(element));
                    }
                    Log.d("#####StoreRepository","No of results"+searchResult.size());
                    return searchResult;
                }

                return null;
            }
        });
    }

    @Override
    public Observable<Coupon> getCouponCode(String authKey, String DealId) {

        Log.d("###Coupon Repository","Inside method of getCouponCode");
        mStoreApi.getStoreDetails("ffdfdfdfdgf");
        //Log.d("###Coupon Repository","NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN");
       /* return mStoreApi.generateCoupon(authKey,DealId).map(new Func1<CouponEntity, Coupon>() {
            @Override
            public Coupon call(CouponEntity couponEntity) {
                Log.d("###Coupon Repository","Mapping Info");
                return CouponMapper.transform(couponEntity);
            }
        });*/


        return null;
    }

}
