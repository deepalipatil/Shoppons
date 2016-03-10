package com.shopons.domain.interactors;

import com.shopons.domain.MsgFromServer;
import com.shopons.domain.executors.PostExecutionThread;
import com.shopons.domain.executors.ThreadExecutor;
import com.shopons.domain.repositories.UserRepository;

import rx.Observable;

/**
 * Created by deepali on 8/3/16.
 */
public class VerifyOtp extends UseCase<MsgFromServer>{

    private UserRepository mUserRepository;

    private String mAuthKey;

    public VerifyOtp(final UserRepository userRepository, final String authKey,
                     final ThreadExecutor threadExecutor, final PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mAuthKey = authKey;
        mUserRepository = userRepository;
    }

    /**
     * Builds an {@link Observable} which will be used when executing the current {@link UseCase}.
     */
    @Override
    public Observable<MsgFromServer> buildUseCaseObservable() {
        return mUserRepository.resendOtp(mAuthKey);
    }
}
