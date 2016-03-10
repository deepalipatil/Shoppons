package com.shopons.domain.interactors;

import com.shopons.domain.executors.PostExecutionThread;
import com.shopons.domain.executors.ThreadExecutor;
import com.shopons.domain.repositories.UserRepository;

import java.util.Map;

import rx.Observable;

/**
 * Created by deepali on 8/3/16.
 */
public class UpdatePassword extends UseCase<Map<String, Object>>{

    private UserRepository mUserRepository;
    private String mAuthKey;
    private Map<String, Object> mUpdateMap;
    private String mUserId;
    private String mUserType;

    public UpdatePassword(final UserRepository mUserRepository,
                              final Map<String, Object> mUpdateMap, final String mUserId,
                              final String mUserType,
                              final String mAuthKey, final ThreadExecutor threadExecutor,
                              final PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.mUserRepository = mUserRepository;
        this.mAuthKey = mAuthKey;
        this.mUpdateMap = mUpdateMap;
        this.mUserId = mUserId;
        this.mUserType = mUserType;
    }

    @Override
    public Observable<Map<String, Object>> buildUseCaseObservable() {
        return mUserRepository.updateUserPassword(mAuthKey,mUpdateMap, mUserId, mUserType);
    }
}

