package com.shopons.data.mappers;

import com.shopons.data.entities.AppVersionEntity;
import com.shopons.data.entities.BrandInfo;
import com.shopons.data.entities.StoreEntity;
import com.shopons.data.entities.StoreInfo;
import com.shopons.domain.AppVersion;
import com.shopons.domain.Store;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by komal on 24/2/16.
 */
public class StoreMapper {
    public static Store transform(final StoreInfo storeInfo) {
        List<com.shopons.domain.BrandInfo> mappedBrandInfo=new ArrayList<>();

        List<BrandInfo> brandInfoList=storeInfo.getBrand_info();


        for(BrandInfo element:brandInfoList)
        {
            mappedBrandInfo.add(new com.shopons.domain.BrandInfo(element.getPerson_type()));
        }

        return new Store(storeInfo.getId(),storeInfo.getName(),storeInfo.getAddress(),
                storeInfo.getCity(),storeInfo.getContact(),storeInfo.getRating(),storeInfo.getThumbnail(),mappedBrandInfo);
    }

}
