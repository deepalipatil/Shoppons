package com.shopons.data.mappers;


import com.shopons.data.entities.StoreDetailsEntity;
import com.shopons.domain.BrandInfo;
import com.shopons.domain.StoreDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by komal on 20/3/16.
 */
public class StoreDetailsMapper {
    public static StoreDetails transform(StoreDetailsEntity storeDetailsEntity)
    {
        List<BrandInfo> mappedList=new ArrayList<>();

        for(com.shopons.data.entities.BrandInfo element:storeDetailsEntity.getBrand_info())
        {
            mappedList.add(new BrandInfo(element.getPerson_type()));
        }
        return new StoreDetails(storeDetailsEntity.getId(),mappedList,storeDetailsEntity.getName(),
                storeDetailsEntity.getAddress(),storeDetailsEntity.getRating(),storeDetailsEntity.getReviews(),
                storeDetailsEntity.getCity());
    }
}
