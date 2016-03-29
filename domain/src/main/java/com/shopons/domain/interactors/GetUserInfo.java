package com.shopons.domain.interactors;

import com.shopons.domain.User;
import com.shopons.domain.executors.PostExecutionThread;
import com.shopons.domain.executors.ThreadExecutor;
import com.shopons.domain.repositories.UserRepository;

import rx.Observable;

/**
 * Created by deepali on 28/3/16.
 */
public class GetUserInfo extends UseCase<User> {

    private UserRepository mUserRepository;

    public GetUserInfo( final UserRepository userRepository,
                               final ThreadExecutor threadExecutor, final PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mUserRepository = userRepository;
    }

    @Override
    public Observable<User> buildUseCaseObservable() {
        return mUserRepository.getUserInfo();
    }
}
