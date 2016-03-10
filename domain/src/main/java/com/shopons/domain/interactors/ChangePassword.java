package com.shopons.domain.interactors;

import com.shopons.domain.MsgFromServer;
import com.shopons.domain.User;
import com.shopons.domain.executors.PostExecutionThread;
import com.shopons.domain.executors.ThreadExecutor;
import com.shopons.domain.repositories.UserRepository;

import rx.Observable;

/**
 * Created by deepali on 8/3/16.
 */
public class ChangePassword extends UseCase<MsgFromServer> {

    private UserRepository mUserRepository;
    private User mUser;

    public ChangePassword(final User user, final UserRepository userRepository,
                          final ThreadExecutor threadExecutor, final PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mUserRepository = userRepository;
        mUser = user;
    }

    @Override
    public Observable<MsgFromServer> buildUseCaseObservable() {
        return mUserRepository.updatePassword(mUser.getAuthKey(), mUser.getId(), mUser.getPassword());
    }
}

