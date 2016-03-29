package com.shopons.data.repository;

import android.util.Log;

import com.shopons.data.entities.UserEntity;
import com.shopons.data.mappers.UserMapper;
import com.shopons.data.net.UserApis;
import com.shopons.data.realmdb.UserInfo;
import com.shopons.data.utils.Urls;
import com.shopons.domain.User;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.util.concurrent.TimeUnit;

import io.realm.Realm;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * User repository implementation
 *
 * @author : Kaustubh Deshmukh
 * @date : 01/10/15 : 8:47 PM
 * @email : akshay@betacraft.co
 */
public final class UserRepository implements com.shopons.domain.repositories.UserRepository {

    private UserApis mUserApis;

    public UserRepository() {

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
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        mUserApis=retrofit.create(UserApis.class);
    }



    @Override
    public Observable<User> loginWithFacebook(User user) {

        return mUserApis.loginWithFacebook(user).map(new Func1<UserEntity, User>() {
            @Override
            public User call(UserEntity userEntity) {
                if(userEntity==null)
                    Log.d("###UserRepository","UserEntity is null");
                return UserMapper.transform(userEntity);
            }
        });
    }

    @Override
    public Observable<Void> logout() {
        return null;
    }



    @Override
    public Observable<User> registerWithGooglePlus(final String email, final String userName, final String googlePlusToken) {
        return mUserApis.signUpWithGooglePlus(email,userName,googlePlusToken);
    }

    @Override
    public Observable<User> loginWithGooglePlus(User user) {
        return mUserApis.loginWithGooglePlus(user).map(new Func1<UserEntity, User>() {
            @Override
            public User call(UserEntity userEntity) {
                if (userEntity == null)
                    Log.d("###UserRepository", "UserEntity is null");
                return UserMapper.transform(userEntity);
            }
        });



    }

    @Override
    public Observable<Void> saveUserInfo(final User user) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                UserInfo.SaveUserInfo(user);
                subscriber.onNext(null);
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<Void> deleteUserInfo() {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                UserInfo.clearUserInfo();
                subscriber.onNext(null);
                subscriber.onCompleted();
            }
        });
    }


    @Override
    public Observable<User> getUserInfo() {
        return Observable.create(new Observable.OnSubscribe<User>() {
            @Override
            public void call(Subscriber<? super User> subscriber) {
                final Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                /**
                 * For now we are returning just the first and only saved user
                 */
                final UserEntity userEntity = realm.where(UserEntity.class).findFirst();
                if (userEntity == null) {
                    realm.commitTransaction();
                    realm.close();
                    subscriber.onNext(null);
                    subscriber.onCompleted();
                    return;
                }
                final User user = UserMapper.transform(userEntity);
                realm.commitTransaction();
                realm.close();
                subscriber.onNext(user);
                subscriber.onCompleted();
            }
        });
    }





}
