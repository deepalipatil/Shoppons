package com.shopons.domain.interactors;

import com.shopons.domain.User;
import com.shopons.domain.executors.PostExecutionThread;
import com.shopons.domain.executors.ThreadExecutor;
import com.shopons.domain.repositories.UserRepository;
import rx.Observable;

/**
 * Use case for user login.
 *
 * @author : Kaustubh Deshmukh
 * @date : 01/10/15 : 8:29 PM
 * @email : akshay@betacraft.co
 */
public final class LoginUser extends UseCase<User> {

    private UserRepository mUserRepository;
    private String mPhone;
    private String mPassword;
    private User mUser;

    public LoginUser(final String phone, final String password,
                            final ThreadExecutor threadExecutor, final PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.mPhone=phone;
        this.mPassword=password;
    }

    @Override
    public Observable<User> buildUseCaseObservable() {
        return mUserRepository.loginByEmail(mPhone,mPassword);
    }
}
