package com.shopons.data.repository;


import com.shopons.data.entities.AppVersionEntity;
import com.shopons.data.mappers.AppVersionMapper;
import com.shopons.data.net.GeneralApi;
import com.shopons.data.utils.Urls;
import com.shopons.domain.AppVersion;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.functions.Func1;


public class GeneralRepository implements com.shopons.domain.repositories.GeneralRepository {

    GeneralApi mGeneralApi;
    public GeneralRepository(){

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
         mGeneralApi=retrofit.create(GeneralApi.class);

    }


    @Override
    public Observable<AppVersion> getAppVersion(final int versionNumber) {
        return mGeneralApi.getAppVersion(versionNumber).map(new Func1<AppVersionEntity, AppVersion>() {
            @Override
            public AppVersion call(final AppVersionEntity appVersionEntity) {
                return AppVersionMapper.transform(appVersionEntity);
            }
        });
    }
}