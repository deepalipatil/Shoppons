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

    Observable<User> registerWithGooglePlus(final String email, final String userName, final String googlePlusToken);
    Observable<User> loginWithGooglePlus(final User user);
    Observable<User> loginWithFacebook(final User user);
    Observable<Void> logout();
}
