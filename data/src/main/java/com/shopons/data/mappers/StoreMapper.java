package com.shopons.data.mappers;

import com.shopons.data.entities.AppVersionEntity;
import com.shopons.data.entities.StoreEntity;
import com.shopons.data.entities.StoreInfo;
import com.shopons.domain.AppVersion;
import com.shopons.domain.Store;

/**
 * Created by komal on 24/2/16.
 */
public class StoreMapper {
    public static Store transform(final StoreInfo storeInfo) {
        return new Store(storeInfo.getId(),storeInfo.getName(),storeInfo.getAddress(),
                storeInfo.getCity(),storeInfo.getContact(),storeInfo.getRating());
    }

}
