package com.shopons.domain.interactors;

import com.shopons.domain.executors.PostExecutionThread;
import com.shopons.domain.executors.ThreadExecutor;
import com.shopons.domain.repositories.UserRepository;

import rx.Observable;

/**
 * Created by deepali on 28/3/16.
 */
public class DeleteUserInfo extends UseCase<Void> {

    private UserRepository mUserRepository;

    public DeleteUserInfo(final ThreadExecutor threadExecutor,
                             final PostExecutionThread postExecutionThread,
                             final UserRepository mUserRepository) {
        super(threadExecutor, postExecutionThread);
        this.mUserRepository = mUserRepository;
    }

    @Override
    public Observable<Void> buildUseCaseObservable() {
        return mUserRepository.deleteUserInfo();
    }
}