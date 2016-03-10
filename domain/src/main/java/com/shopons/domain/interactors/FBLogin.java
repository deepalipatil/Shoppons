package com.shopons.domain.interactors;

import com.shopons.domain.User;
import com.shopons.domain.executors.PostExecutionThread;
import com.shopons.domain.executors.ThreadExecutor;
import com.shopons.domain.repositories.UserRepository;

import rx.Observable;

/**
 * Created by deepali on 5/3/16.
 */
public class FBLogin extends UseCase<User> {

    private UserRepository mUserRepository;
    private String mEmail;
    private String mFacebookToken;
    private long mFacebookUserId;

    public FBLogin(final String email,final String facebookToken, final long facebookUserId,
                                 final ThreadExecutor threadExecutor,
                                 final PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.mEmail=email;
        this.mFacebookToken=facebookToken;
        this.mFacebookUserId=facebookUserId;
    }

    @Override
    public Observable<User> buildUseCaseObservable() {
        return mUserRepository.registerWithFacebook(mEmail,mFacebookToken,mFacebookUserId);
    }

}
