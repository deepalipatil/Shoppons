package com.shopons.domain.interactors;

import com.shopons.domain.MsgFromServer;
import com.shopons.domain.executors.PostExecutionThread;
import com.shopons.domain.executors.ThreadExecutor;
import com.shopons.domain.repositories.UserRepository;

import rx.Observable;

/**
 * Created by deepali on 8/3/16.
 */
public class VerifyPhoneNumber extends UseCase<MsgFromServer> {

    private UserRepository mUserRepository;
    private String mAuthKey;
    private String mOtp;

    public VerifyPhoneNumber(final String authKey, final String otp, final UserRepository userRepository,
                             final ThreadExecutor threadExecutor, final PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mUserRepository = userRepository;
        mAuthKey = authKey;
        mOtp = otp;
    }

    @Override
    public Observable<MsgFromServer> buildUseCaseObservable() {
        return mUserRepository.verifyPhone(mAuthKey, mOtp);
    }
}