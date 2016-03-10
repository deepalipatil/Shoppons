package com.shopons.domain.interactors;

import com.shopons.domain.executors.PostExecutionThread;
import com.shopons.domain.executors.ThreadExecutor;
import com.shopons.domain.repositories.UserRepository;

import rx.Observable;

/**
 * Created by deepali on 5/3/16.
 */
public class LogoutUser extends UseCase<Void> {

    private UserRepository mUserRepository;

    public LogoutUser(final UserRepository userRepository,
                      final ThreadExecutor threadExecutor, final PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mUserRepository = userRepository;
    }

    @Override
    public Observable<Void> buildUseCaseObservable() {
        return mUserRepository.logout();
    }
}
