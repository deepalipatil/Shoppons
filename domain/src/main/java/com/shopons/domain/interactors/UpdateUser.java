package com.shopons.domain.interactors;

import com.shopons.domain.User;
import com.shopons.domain.executors.PostExecutionThread;
import com.shopons.domain.executors.ThreadExecutor;
import com.shopons.domain.repositories.UserRepository;

import rx.Observable;

/**
 * Created by deepali on 8/3/16.
 */
public class UpdateUser extends UseCase<User>{

    private UserRepository mUserRepository;
    private User mUser;

    public UpdateUser(final User mUser, final UserRepository mUserRepository,
                      final ThreadExecutor threadExecutor,
                      final PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.mUser = mUser;
        this.mUserRepository = mUserRepository;
    }

    @Override
    public Observable<User> buildUseCaseObservable() {
        return mUserRepository.updateUser(mUser);
    }
}

