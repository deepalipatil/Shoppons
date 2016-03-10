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
    private String mEmail;
    private String mUserName;
    private String mGooglePlusToken;

    private User mUser;

    public GPlusLogin(final String email, final String userName, final String googlePlusToken,
                      final ThreadExecutor threadExecutor,
                      final PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.mEmail=email;
        this.mUserName=userName;
        this.mGooglePlusToken=googlePlusToken;
    }

    @Override
    public Observable<User> buildUseCaseObservable() {
        return mUserRepository.registerWithGooglePlus(mEmail,mUserName,mGooglePlusToken);
    }
}