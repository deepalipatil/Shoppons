package com.shopons.data.net;



import com.shopons.data.entities.AppVersionEntity;

import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by komal on 11/2/16.
 */
public interface GeneralApi {

    @Headers({
            "Accept : application/json",
            "Content-Type : application/json"
    })

    @GET("app_version/user/android/{version}")
    public Observable<AppVersionEntity> getAppVersion(@Path("version") int version);

}
