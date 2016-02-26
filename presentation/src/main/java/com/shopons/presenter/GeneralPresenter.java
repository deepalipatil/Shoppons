package com.shopons.presenter;


import com.shopons.domain.repositories.GeneralRepository;
import com.shopons.domain.interactors.GetAppVersionInfo;

import rx.Subscriber;

/**
 * Created by komal on 11/2/16.
 */
public class GeneralPresenter extends Presenter {
    GeneralRepository mGeneralRepository =new com.shopons.data.repository.GeneralRepository();

    public void checkForUpdates(int version, Subscriber subscriber)
    {
        mSubscriptions.add(new GetAppVersionInfo(version,mGeneralRepository,
                mThreadExecutor,mPostExecutionThread).execute(subscriber));
    }
    @Override
    public void resume() {
        cancelRunningUseCases();
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
