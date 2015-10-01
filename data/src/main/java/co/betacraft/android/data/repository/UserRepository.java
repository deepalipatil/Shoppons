package co.betacraft.android.data.repository;

import java.util.List;

import co.betacraft.android.data.net.UserApis;
import co.betacraft.android.domain.User;
import rx.Observable;

/**
 * User repository implementation
 *
 * @author : Akshay Deo
 * @date : 01/10/15 : 8:47 PM
 * @email : akshay@betacraft.co
 */
public final class UserRepository implements co.betacraft.android.domain.repositories.UserRepository {

    private UserApis mUserApis;

    public UserRepository(final UserApis userApis) {
        mUserApis = userApis;
    }

    @Override
    public Observable<User> loginUser(final String email, final String password) {
        return mUserApis.login(email, password);
    }

    @Override
    public Observable<User> getUserById(long userId) {
        return null;
    }

    @Override
    public Observable<List<User>> getFriendsOfUserById(long userId) {
        return null;
    }
}
