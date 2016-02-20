package com.shopons.domain.repositories;

import com.shopons.domain.Location;

import rx.Observable;

/**
 * Created by komal on 19/2/16.
 */
public interface LocationRepository {
    Observable<Location> getUserLocation();

}
