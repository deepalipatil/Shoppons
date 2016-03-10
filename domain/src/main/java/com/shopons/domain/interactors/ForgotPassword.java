package com.shopons.domain.interactors;

import com.shopons.domain.executors.PostExecutionThread;
import com.shopons.domain.executors.ThreadExecutor;
import com.shopons.domain.repositories.UserRepository;

import java.util.Map;

import rx.Observable;

/**
 * Created by deepali on 8/3/16.
 */
public class ForgotPassword extends UseCase<Void> {

    private UserRepository mUserRepository;
    private Map<String, Object> map;
    private String userType;

    public ForgotPassword(final ThreadExecutor threadExecutor,final PostExecutionThread postExecutionThread,
                              final UserRepository mUserRepository,final Map<String, Object> map,
                              final String userType) {
        super(threadExecutor, postExecutionThread);
        this.mUserRepository = mUserRepository;
        this.map = map;
        this.userType = userType;
    }

    @Override
    public Observable<Void> buildUseCaseObservable() {
        return mUserRepository.forgotPassword(map, userType);
    }
}
