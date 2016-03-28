package com.shopons.data.net;

import com.shopons.data.entities.GenericEntity;
import com.shopons.data.entities.MsgEntity;
import com.shopons.data.entities.UserEntity;
import com.shopons.domain.User;

import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Path;
import rx.Observable;

/**
 * Apis related with User
 *
 * @author : Kaustubh Deshmukh
 * @date : 01/10/15 : 8:42 PM
 * @email : akshay@betacraft.co
 */
public interface UserApis {
    @GET("/users/login")
    Observable<User> login(@Field("email") String email, @Field("password") String password);

    @Headers({
            "Content-Type : application/json",
            "Accept : application/json"
    })
    @POST(" https://shopons.herokuapp.com/api/v1/user/register")
    Observable<User> registerUserByEmail(@Path("email") String email,@Body String phone,@Body String password);

    @Headers({
            "Content-Type : application/json",
            "Accept : application/json"
    })
    @POST("https://shopons.herokuapp.com/api/v1/user/login")
    Observable<User> loginUserByEmail(@Body String phone,@Body String password);

    @Headers({
            "Content-Type : application/json",
            "Accept : application/json"
    })

    @POST("")
    Observable<MsgEntity> updatePassword(@Header("Auth-Key") String authKey,
                                             @Path("id") String userId,
                                             @Body UserEntity userEntity);

    @POST("")
    Observable<UserEntity> updateUser(@Header("Auth-Key") String authKey,
                                      @Body UserEntity userEntity, @Path("id") String id);

    @POST("")
    Observable<MsgEntity> verifyNumber(@Header("Auth-Key") String authKey,
                                           @Body UserEntity userEntity);

    @POST("")
    Observable<MsgEntity> resendOtp(@Header("Auth-Key") String authKey);

    @Headers({
            "Content-Type : application/json",
            "Accept : application/json"
    })
    @POST("")
    Observable<GenericEntity> updateUserPassword(@Header("Auth-Key") String authKey,
                                                 @Body GenericEntity genericEntity, @Path("user_id") String id,
                                                 @Path("user_type") String userType);


    @Headers({
            "Content-Type : application/json",
            "Accept : application/json"
    })
    @POST("")
    Observable<GenericEntity> forgotPassword(@Body GenericEntity genericEntity,
                                             @Path("user_type") String userType);

    @Headers({
            "Content-Type : application/json",
            "Accept : application/json"
    })
    @GET("")
    Observable<MsgEntity> getForgotPasswordOtp(@Path("email") String email);


    @Headers({
            "Content-Type : application/json",
            "Accept : application/json"
    })
    @POST("")
    Observable<UserEntity> verifyForgotPasswordOtp(@Path("email") String email,
                                                   @Body UserEntity userEntity);


    @Headers({
            "Content-Type : application/json",
            "Accept : application/json"
    })
    @POST("user/facebook")
    Observable<UserEntity> loginWithFacebook(@Body User user);

    @Headers({
            "Content-Type : application/json",
            "Accept : application/json"
    })
    @POST("/user/gplus")
    Observable<User> signUpWithGooglePlus(@Path("email") String email,@Body String userName, @Body String googlePlusToken);

    @Headers({
            "Content-Type : application/json",
            "Accept : application/json"
    })
    @POST("user/gplus")
    Observable<UserEntity> loginWithGooglePlus(@Body User user);



}

