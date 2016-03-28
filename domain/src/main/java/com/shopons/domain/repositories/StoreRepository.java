package com.shopons.domain.repositories;

import com.shopons.domain.Store;
import com.shopons.domain.StoreDetails;

import java.util.List;

import rx.Observable;

/**
 * Created by komal on 24/2/16.
 */
public interface StoreRepository {
    Observable<List<Store>> getStoreListing(double latitude, double longitude,int pageNo);
    Observable<StoreDetails> getStoreDetails(String storeId);
}
