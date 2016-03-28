package com.shopons.presenter;

import android.util.Log;

import com.shopons.domain.User;
import com.shopons.domain.interactors.GPlusLogin;
import com.shopons.domain.repositories.UserRepository;

import rx.Subscriber;

/**
 * Created by deepali on 9/3/16.
 */
public class UserPresenter extends Presenter {

    private UserRepository mUserRepository;
    private static final String TAG = "##UserPresenter";

    public UserPresenter() {
        mUserRepository = new com.shopons.data.repository.UserRepository();
    }




    @Override
    public void resume() {
    }

    @Override
    public void pause() {
        Log.d(TAG, "cancelling running tasks...");
        cancelRunningUseCases();
    }

    @Override
    public void destroy() {
        Log.d(TAG, "cancelling running tasks...");
        cancelRunningUseCases();
    }
}
