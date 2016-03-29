package com.shopons.domain.repositories;

import com.shopons.domain.User;

import rx.Observable;

/**
 * Repository for the user realted data.
 *
 * @author : Kaustubh Deshmukh
 * @date : 01/10/15 : 8:18 PM
 * @email : akshay@betacraft.co
 */
public interface UserRepository {

    Observable<User> registerWithGooglePlus(final String email, final String userName, final String googlePlusToken);
    Observable<User> loginWithGooglePlus(final User user);
    Observable<User> loginWithFacebook(final User user);
    Observable<Void> logout();
    Observable<Void> saveUserInfo(final User user);
    Observable<User> getUserInfo();
    Observable<Void> deleteUserInfo();
}
