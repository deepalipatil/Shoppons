package com.shopons.domain.interactors;

import com.shopons.domain.User;
import com.shopons.domain.executors.PostExecutionThread;
import com.shopons.domain.executors.ThreadExecutor;
import com.shopons.domain.repositories.UserRepository;

import rx.Observable;

/**
 * Created by deepali on 5/3/16.
 */
public class GPlusLogin extends UseCase<User> {

    private UserRepository mUserRepository;
    User user;
    private User mUser;

    public GPlusLogin(User user,UserRepository userRepository,
                      final ThreadExecutor threadExecutor,
                      final PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mUserRepository=userRepository;
        this.user=user;
    }

    @Override
    public Observable<User> buildUseCaseObservable() {
        return mUserRepository.loginWithGooglePlus(user);
    }
}