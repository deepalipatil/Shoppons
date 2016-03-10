package com.shopons.domain.interactors;

import com.shopons.domain.User;
import com.shopons.domain.executors.PostExecutionThread;
import com.shopons.domain.executors.ThreadExecutor;
import com.shopons.domain.repositories.UserRepository;
import rx.Observable;

/**
 * Get user use case
 *
 * @author : Kaustubh Deshmukh
 * @date : 01/10/15 : 8:33 PM
 * @email : akshay@betacraft.co
 */
public final class GetUserById extends UseCase<User> {

    private UserRepository mUserRepository;
    private String mUserId;
    private String mAuthKey;

    /**
     * Constructor
     *
     * @param mUserId id of the user to be retrieved
     * @param mUserRepository user repository implementation
     * @param threadExecutor thread executor for network/db task
     * @param postExecutionThread post execution thread
     */
    public GetUserById(final String mUserId, final String mAuthKey,
                       final UserRepository mUserRepository, final ThreadExecutor threadExecutor,
                       final PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.mUserRepository = mUserRepository;
        this.mUserId = mUserId;
        this.mAuthKey = mAuthKey;
    }
    /**
     * Overridden method from UseCase {@link UseCase}
     *
     * @return Observable {@link Observable} of user
     */
    @Override
    protected Observable<User> buildUseCaseObservable() {
        return mUserRepository.getUserById(mUserId);
    }
}
