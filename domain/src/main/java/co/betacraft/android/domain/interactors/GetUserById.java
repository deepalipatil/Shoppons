package co.betacraft.android.domain.interactors;

import co.betacraft.android.domain.User;
import co.betacraft.android.domain.executors.PostExecutionThread;
import co.betacraft.android.domain.executors.ThreadExecutor;
import co.betacraft.android.domain.repositories.UserRepository;
import rx.Observable;

/**
 * Get user use case
 *
 * @author : Akshay Deo
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
