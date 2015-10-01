package co.betacraft.android.domain.repositories;

import java.util.List;

import co.betacraft.android.domain.User;
import rx.Observable;

/**
 * Repository for the user realted data.
 *
 * @author : Akshay Deo
 * @date : 01/10/15 : 8:18 PM
 * @email : akshay@betacraft.co
 */
public interface UserRepository {
    Observable<User> loginUser(final String email, final String password);
    Observable<User> getUserById(final long userId);
    Observable<List<User>> getFriendsOfUserById(final long userId);
}
