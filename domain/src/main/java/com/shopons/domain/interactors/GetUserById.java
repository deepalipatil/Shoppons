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
    private long mUserId;

    /**
     * Constructor
     *
     * @param userId id of the user to be retrieved
     * @param userRepository user repository implementation
     * @param threadExecutor thread executor for network/db task
     * @param postExecutionThread post execution thread
     */
    public GetUserById(final long userId, final UserRepository userRepository,final ThreadExecutor
            threadExecutor, final PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mUserId = userId;
        mUserRepository = userRepository;
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
