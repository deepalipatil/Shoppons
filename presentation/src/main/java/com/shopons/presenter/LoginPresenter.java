package com.shopons.presenter;

import com.shopons.domain.MsgFromServer;
import com.shopons.domain.User;

import com.shopons.domain.interactors.FBLogin;
import com.shopons.domain.interactors.GPlusLogin;
import com.shopons.domain.repositories.UserRepository;

import rx.Subscriber;

/**
 * Login Screen Presenter
 *
 * @author : Kaustubh Deshmukh
 * @date : 05/10/15 : 5:39 PM
 * @email : akshay@betacraft.co
 */
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
