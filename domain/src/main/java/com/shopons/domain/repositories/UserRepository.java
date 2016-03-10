package com.shopons.domain.repositories;

import com.shopons.domain.MsgFromServer;
import com.shopons.domain.User;

import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Repository for the user realted data.
 *
 * @author : Kaustubh Deshmukh
 * @date : 01/10/15 : 8:18 PM
 * @email : akshay@betacraft.co
 */
public interface UserRepository {
    Observable<User> loginUser(final String email, final String password);
    Observable<User> getUserById(final String userId);
    Observable<List<User>> getFriendsOfUserById(final long userId);

    Observable<User> registerByEmail(final String email, final String phone, final String password);
    Observable<User> registerWithFacebook(final String email,final String facebookToken, final long facebookUserId);
    Observable<User> registerWithGooglePlus(final String email, final String userName, final String googlePlusToken);

    Observable<User> loginWithFacebook(final User user);
    Observable<User> loginByEmail(final String phone, final String password);

    Observable<MsgFromServer> updatePassword(final String authKey, final String userId, final String password);

    Observable<MsgFromServer> verifyPhone(final String authKey, final String otp);
    Observable<MsgFromServer> resendOtp(final String authKey);

    Observable<User> updateUser(final User user);

    Observable<MsgFromServer> getForgotPasswordOtp(final String email);
    Observable<User> verifyForgotPasswordOtp(final String otp, final String email);

    Observable<Map<String, Object>> updateUserPassword(final String authKey,
                                                       final Map<String, Object> updateMap, final String id,
                                                       final String userType);
    Observable<Void> forgotPassword(final Map<String, Object> map, String userType);

    Observable<Void> logout();
}
