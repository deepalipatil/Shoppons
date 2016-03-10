package com.shopons.domain.interactors;

import com.shopons.domain.MsgFromServer;
import com.shopons.domain.executors.PostExecutionThread;
import com.shopons.domain.executors.ThreadExecutor;
import com.shopons.domain.repositories.UserRepository;

import rx.Observable;

/**
 * Created by deepali on 8/3/16.
 */
public class OtpForForgotPassword extends UseCase<MsgFromServer>{

    private UserRepository mUserRepository;

    private String mEmail;

    public OtpForForgotPassword(final UserRepository userRepository, final String email,
                                final ThreadExecutor threadExecutor, final PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mEmail = email;
        mUserRepository = userRepository;
    }

    /**
     * Builds an {@link Observable} which will be used when executing the current {@link UseCase}.
     */
    @Override
    public Observable<MsgFromServer> buildUseCaseObservable() {
        return mUserRepository.getForgotPasswordOtp(mEmail);
    }
}

