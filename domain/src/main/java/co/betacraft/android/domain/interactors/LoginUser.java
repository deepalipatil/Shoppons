package co.betacraft.android.domain.interactors;

import co.betacraft.android.domain.User;
import co.betacraft.android.domain.executors.PostExecutionThread;
import co.betacraft.android.domain.executors.ThreadExecutor;
import co.betacraft.android.domain.repositories.UserRepository;
import rx.Observable;

/**
 * Use case for user login.
 *
 * @author : Akshay Deo
 * @date : 01/10/15 : 8:29 PM
 * @email : akshay@betacraft.co
 */
public final class LoginUser extends UseCase<User> {

    private UserRepository mUserRepository;
    private String mEmail;
    private String mPassword;

    public LoginUser(final UserRepository userRepository, final String email, final String
            password, final ThreadExecutor threadExecutor, final PostExecutionThread
                             postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mUserRepository = userRepository;
    }

    @Override
    protected Observable<User> buildUseCaseObservable() {
        return mUserRepository.loginUser(mEmail, mPassword);
    }
}
