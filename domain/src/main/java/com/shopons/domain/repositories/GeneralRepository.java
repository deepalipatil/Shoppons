package com.shopons.domain.repositories;

import com.shopons.domain.AppVersion;

import rx.Observable;

/**
 * Created by komal on 12/2/16.
 */
public interface GeneralRepository {
    Observable<AppVersion> getAppVersion(int version);
}
