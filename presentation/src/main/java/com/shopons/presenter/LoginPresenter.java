package com.shopons.presenter;

import com.shopons.domain.User;
import com.shopons.domain.interactors.FBLogin;
import com.shopons.domain.interactors.GPlusLogin;
import com.shopons.domain.interactors.GetUserInfo;
import com.shopons.domain.interactors.LogoutUser;
import com.shopons.domain.interactors.SaveUserInfo;
import com.shopons.domain.repositories.UserRepository;

import rx.Subscriber;

public final class LoginPresenter extends Presenter {

    private UserRepository mUserRepository;
    private User mUser;

    public LoginPresenter() {
        mUserRepository = new com.shopons.data.repository.UserRepository();
    }

    public void loginWithGooglePlus(User user, Subscriber<User> subscriber)
    {
        mSubscriptions.add(new GPlusLogin(user,mUserRepository,mThreadExecutor,mPostExecutionThread).execute(subscriber));
    }

    public void loginWithFacebook(User user, Subscriber<User> subscriber)
    {
        mSubscriptions.add(new FBLogin(user,mUserRepository,mThreadExecutor,mPostExecutionThread).execute(subscriber));
    }

    public void saveUserInfo(final User user, final Subscriber<User> subscriber) {
        mSubscriptions.add(new SaveUserInfo(user, mUserRepository,
                mThreadExecutor, mPostExecutionThread).execute(subscriber));
    }

    public void getUserInfo(final Subscriber<User> subscriber) {
        mSubscriptions.add(new GetUserInfo(mUserRepository,
                mThreadExecutor, mPostExecutionThread).execute(subscriber));
    }

    public void logoutUser(final Subscriber<Void> subscriber){
        mSubscriptions.add(new LogoutUser(mUserRepository,
                mThreadExecutor, mPostExecutionThread).execute(subscriber));
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {
        cancelRunningUseCases();
    }

    @Override
    public void destroy() {
        cancelRunningUseCases();
    }
}
