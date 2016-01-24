package com.shopons.domain.interactors;

import com.shopons.domain.User;
import com.shopons.domain.executors.PostExecutionThread;
import com.shopons.domain.executors.ThreadExecutor;
import com.shopons.domain.repositories.UserRepository;
import rx.Observable;

/**
 * Use case for user login.
 *
 * @author : Kaustubh Deshmukh
 * @date : 01/10/15 : 8:29 PM
 * @email : akshay@betacraft.co
 */
public final class LoginUser extends UseCase<User> {

    private UserRepository mUserRepository;
    private String mEmail;
    private String mPassword;

    /**
     * Constructor
     *
     * @param email user email
     * @param password user password
     * @param userRepository user repository implementation {@link UserRepository}
     * @param threadExecutor thread executor for network actions {@link ThreadExecutor}
     * @param postExecutionThread post network task executor {@link PostExecutionThread}
     */
    public LoginUser(final String email, final String password, final UserRepository
            userRepository, final ThreadExecutor threadExecutor, final PostExecutionThread
                             postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mUserRepository = userRepository;
    }

    /**
     * Overridden method from UseCase {@link UseCase}
     *
     * @return Observable {@link Observable} of user
     */
    @Override
    protected Observable<User> buildUseCaseObservable() {
        return mUserRepository.loginUser(mEmail, mPassword);
    }
}
