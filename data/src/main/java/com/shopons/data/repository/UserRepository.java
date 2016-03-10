package com.shopons.data.repository;

import com.shopons.data.entities.GenericEntity;
import com.shopons.data.entities.MsgEntity;
import com.shopons.data.entities.UserEntity;
import com.shopons.data.mappers.MsgMapper;
import com.shopons.data.mappers.UserMapper;
import com.shopons.data.net.UserApis;
import com.shopons.data.utils.Urls;
import com.shopons.domain.MsgFromServer;
import com.shopons.domain.User;

import java.util.List;
import java.util.Map;

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

    public UserRepository(final UserApis userApis) {
        Retrofit retrofit=new Retrofit.Builder().baseUrl(Urls.baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).build();
        mUserApis=retrofit.create(UserApis.class);
    }

    public UserRepository(){}

    @Override
    public Observable<User> loginUser(final String email, final String password) {
        return mUserApis.login(email, password);
    }

    @Override
    public Observable<User> getUserById(String userId) {
        return null;
    }

    @Override
    public Observable<List<User>> getFriendsOfUserById(long userId) {
        return null;
    }

    @Override
    public Observable<User> loginWithFacebook(User user) {
        return null;
    }

    @Override
    public Observable<User> registerByEmail(final String email, final String phone, final String password) {
        return mUserApis.registerUserByEmail(email,phone,password);
    }

    @Override
    public Observable<User> updateUser(final User user) {
        final UserEntity userEntity = UserMapper.transform(user);
        return mUserApis.updateUser(userEntity.getAuthKey(), userEntity, userEntity.getId()).map(new Func1<UserEntity, User>() {
            @Override
            public User call(UserEntity userEntity) {
                return UserMapper.transform(userEntity);
            }
        });
    }

    @Override
    public Observable<User> loginByEmail(final String phone, final String password) {
        return mUserApis.loginUserByEmail(phone,password);
    }

    @Override
    public Observable<User> registerWithFacebook(final String email,final String facebookToken, final long facebookUserId) {
        return mUserApis.signUpWithFacebook(email,facebookToken,facebookUserId);
    }

    @Override
    public Observable<User> registerWithGooglePlus(final String email, final String userName, final String googlePlusToken) {
        return mUserApis.signUpWithGooglePlus(email,userName,googlePlusToken);
    }

    public Observable<Void> logout() {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            public void call(Subscriber<? super Void> subscriber) {
                subscriber.onNext(null);
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<Map<String, Object>> updateUserPassword(final String authKey, final Map<String, Object> updateMap, final String id, final String userType) {
        GenericEntity genericEntity = new GenericEntity();
        genericEntity.putAll(updateMap);
        return mUserApis.updateUserPassword(authKey, genericEntity, id, userType).map(new Func1<GenericEntity, Map<String, Object>>() {
            @Override
            public Map<String, Object> call(GenericEntity genericEntity) {
                return genericEntity;
            }
        });
    }

    @Override
    public Observable<Void> forgotPassword(final Map<String, Object> map, final String userType) {
        GenericEntity genericEntity = new GenericEntity();
        genericEntity.putAll(map);
        return mUserApis.forgotPassword(genericEntity, userType).map(new Func1<GenericEntity, Void>() {
            @Override
            public Void call(GenericEntity genericEntity) {
                return null;
            }
        });
    }

    public Observable<MsgFromServer> updatePassword(final String authkey, final String userId, final String password) {
        final UserEntity userEntity = new UserEntity();
        userEntity.setPassword(password);
        return mUserApis.updatePassword(authkey, userId, userEntity).map(new Func1<MsgEntity, MsgFromServer>() {
            @Override
            public MsgFromServer call(MsgEntity msgEntity) {
                return MsgMapper.transform(msgEntity);
            }
        });
    }

    @Override
    public Observable<MsgFromServer> verifyPhone(final String authKey, final String otp) {
        final UserEntity user = new UserEntity();
        user.setOtp(otp);
        return mUserApis.verifyNumber(authKey, user).map(new Func1<MsgEntity, MsgFromServer>() {
            @Override
            public MsgFromServer call(MsgEntity msgEntity) {
                return MsgMapper.transform(msgEntity);
            }
        });
    }

    @Override
    public Observable<MsgFromServer> resendOtp(String authKey) {
        return mUserApis.resendOtp(authKey).map(new Func1<MsgEntity, MsgFromServer>() {
            @Override
            public MsgFromServer call(MsgEntity msgEntity) {
                return MsgMapper.transform(msgEntity);
            }
        });
    }

    @Override
    public Observable<MsgFromServer> getForgotPasswordOtp(String email) {
        return mUserApis.getForgotPasswordOtp(email).map(new Func1<MsgEntity, MsgFromServer>() {
            @Override
            public MsgFromServer call(final MsgEntity msgEntity) {
                return MsgMapper.transform(msgEntity);
            }
        });
    }

    @Override
    public Observable<User> verifyForgotPasswordOtp(final String otp, final String email) {
        final UserEntity userEntity = new UserEntity();
        userEntity.setOtp(otp);
        return mUserApis.verifyForgotPasswordOtp(email, userEntity).map(new Func1<UserEntity, User>() {
            @Override
            public User call(final UserEntity userEntity) {
                return UserMapper.transform(userEntity);
            }
        });
    }
}
