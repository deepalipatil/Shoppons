package com.shopons.presenter;

import com.shopons.domain.User;
import com.shopons.domain.interactors.LoginUser;
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


    public LoginPresenter() {
        mUserRepository = new com.shopons.data.repository.UserRepository(null);
    }


    public void loginUserUsingEmailAddressAndPassword(final String email, final String password,
                                                      final Subscriber<User> subscriber) {
        mSubscriptions.add(new LoginUser(email, password, mUserRepository, mThreadExecutor,
                mPostExecutionThread)
                .execute(subscriber));
    }

    @Override
    void resume() {

    }

    @Override
    void pause() {
        cancelRunningUseCases();
    }

    @Override
    void destroy() {
        cancelRunningUseCases();
    }
}
