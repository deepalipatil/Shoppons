package com.shopons.data.repository;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shopons.data.deserializer.StoreDeserializer;
import com.shopons.data.deserializer.StoreEntityDeserializer;
import com.shopons.data.entities.AppVersionEntity;
import com.shopons.data.entities.StoreEntity;
import com.shopons.data.entities.StoreInfo;
import com.shopons.data.mappers.AppVersionMapper;
import com.shopons.data.mappers.StoreMapper;
import com.shopons.data.net.StoreApi;
import com.shopons.data.utils.Urls;
import com.shopons.domain.AppVersion;
import com.shopons.domain.Store;

import java.util.ArrayList;
import java.util.List;

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

    GsonBuilder gsonBuilder=new GsonBuilder().registerTypeAdapter(StoreEntity.class, new StoreEntityDeserializer())
            .registerTypeAdapter(StoreInfo.class, new StoreDeserializer());
    Gson gson=gsonBuilder.create();


    public StoreRepository(){
        Retrofit retrofit=new Retrofit.Builder().baseUrl(Urls.shopons_base)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson)).build();
        mStoreApi=retrofit.create(StoreApi.class);
    }

    public Observable<List<Store>> getStoreListing(double longitude,double  latitude) {

        final List<Store> stores = new ArrayList<Store>();
        return mStoreApi.getStoreListing(longitude, latitude).map(new Func1<List<StoreInfo>, List<Store>>() {
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
}
