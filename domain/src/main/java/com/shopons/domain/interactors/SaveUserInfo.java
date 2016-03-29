package com.shopons.domain.interactors;

import com.shopons.domain.User;
import com.shopons.domain.executors.PostExecutionThread;
import com.shopons.domain.executors.ThreadExecutor;
import com.shopons.domain.repositories.UserRepository;

import rx.Observable;

/**
 * Created by deepali on 28/3/16.
 */
public class SaveUserInfo extends UseCase<Void> {

    private UserRepository mUserRepository;
    private User mUser;

    public SaveUserInfo(final User user, final UserRepository userRepository,
                           final ThreadExecutor threadExecutor, final PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mUser = user;
        mUserRepository = userRepository;
    }

    @Override
    public Observable<Void> buildUseCaseObservable() {
        return mUserRepository.saveUserInfo(mUser);
    }
}
