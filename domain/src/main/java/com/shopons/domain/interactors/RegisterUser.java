package com.shopons.domain.interactors;

import com.shopons.domain.User;
import com.shopons.domain.executors.PostExecutionThread;
import com.shopons.domain.executors.ThreadExecutor;
import com.shopons.domain.repositories.UserRepository;

import rx.Observable;

/**
 * Created by deepali on 8/3/16.
 */
public class RegisterUser extends UseCase<User> {

    private UserRepository mUserRepository;
    private String mEmail;
    private String mPhone;
    private String mPassword;
    private User mUser;

    public RegisterUser(final String email, final String phone, final String password,
                               final ThreadExecutor threadExecutor,
                               final PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.mEmail=email;
        this.mPhone=phone;
        this.mPassword=password;
    }

    @Override
    public Observable<User> buildUseCaseObservable() {
        return mUserRepository.registerByEmail(mEmail,mPhone,mPassword);
    }

}