package com.shopons.domain.interactors;

import com.shopons.domain.AppVersion;
import com.shopons.domain.repositories.GeneralRepository;
import com.shopons.domain.executors.PostExecutionThread;
import com.shopons.domain.executors.ThreadExecutor;

import rx.Observable;

/**
 * Created by komal on 11/2/16.
 */
public class GetAppVersionInfo extends UseCase<AppVersion> {

    GeneralRepository mGeneralRepository;
    int mVersionInfo;

    public GetAppVersionInfo(final int versionInfo,
                             final GeneralRepository generalRepository,
                             final ThreadExecutor threadExecutor, final PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mVersionInfo = versionInfo;
        mGeneralRepository = generalRepository;
    }
    @Override
    protected Observable<AppVersion> buildUseCaseObservable() {

        return mGeneralRepository.getAppVersion(mVersionInfo);

    }
}
