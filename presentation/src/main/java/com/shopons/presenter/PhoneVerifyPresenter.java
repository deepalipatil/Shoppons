package com.shopons.presenter;

import com.shopons.domain.MsgFromServer;
import com.shopons.domain.interactors.VerifyPhoneNumber;
import com.shopons.domain.repositories.UserRepository;

import rx.Subscriber;

/**
 * Created by deepali on 9/3/16.
 */
public class PhoneVerifyPresenter extends Presenter {

    private UserRepository mUserRepository;

    public PhoneVerifyPresenter() {
        mUserRepository = new com.shopons.data.repository.UserRepository();
    }

    public void vertifyNumber(final String authKey, final String otp,
                              final Subscriber<MsgFromServer> subscriber) {
        mSubscriptions.add(new VerifyPhoneNumber(authKey, otp, mUserRepository, mThreadExecutor,
                mPostExecutionThread).execute(subscriber));
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

    }
}
