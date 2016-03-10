package com.shopons.domain.interactors;

import com.shopons.domain.User;
import com.shopons.domain.executors.PostExecutionThread;
import com.shopons.domain.executors.ThreadExecutor;
import com.shopons.domain.repositories.UserRepository;

import rx.Observable;

/**
 * Created by deepali on 8/3/16.
 */
public class VerifyOtpForForgotPassword extends UseCase<User> {

    private UserRepository mUserRepository;

    private String mOtp;
    private String mEmail;

    public VerifyOtpForForgotPassword(final UserRepository userRepository, final String otp,
                                   final String email,
                                   final ThreadExecutor threadExecutor, final PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mOtp = otp;
        mEmail = email;
        mUserRepository = userRepository;
    }

    /**
     * Builds an {@link Observable} which will be used when executing the current {@link UseCase}.
     */
    @Override
    public Observable<User> buildUseCaseObservable() {
        return mUserRepository.verifyForgotPasswordOtp(mOtp, mEmail);
    }
}
