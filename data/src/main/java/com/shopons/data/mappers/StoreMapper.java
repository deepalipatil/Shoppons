package com.shopons.data.mappers;

import com.shopons.data.entities.AppVersionEntity;
import com.shopons.data.entities.StoreEntity;
import com.shopons.domain.AppVersion;
import com.shopons.domain.Store;

/**
 * Created by komal on 24/2/16.
 */
public class StoreMapper {
    public static Store transform(final StoreEntity storeEntity) {
        return new Store(storeEntity.getId(),storeEntity.getName(),storeEntity.getAddress(),
                        storeEntity.getCity(),storeEntity.getContact(),storeEntity.getRating());
    }

}
