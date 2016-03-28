package com.shopons.data.mappers;

import android.util.Log;

import com.shopons.data.entities.AppVersionEntity;
import com.shopons.data.entities.BrandInfo;
import com.shopons.data.entities.StoreEntity;
import com.shopons.data.entities.StoreInfo;
import com.shopons.domain.AppVersion;
import com.shopons.domain.Location;
import com.shopons.domain.PhoneNumber;
import com.shopons.domain.Store;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by komal on 24/2/16.
 */
public class StoreMapper {
    public static Store transform(final StoreInfo storeInfo) {
        List<com.shopons.domain.BrandInfo> mappedBrandInfo=new ArrayList<>();
        Location location;
        List<BrandInfo> brandInfoList=storeInfo.getBrandInfoList();
        PhoneNumber mappedPhoneNumber=new PhoneNumber(storeInfo.getPhone_numbers().getTelephone(),
                storeInfo.getPhone_numbers().getMobile(),storeInfo.getPhone_numbers().getLandline());

        if(storeInfo.getLocation()!=null)
             location=new Location(storeInfo.getLocation().getLat(),storeInfo.getLocation().getLng());
        else
            location=new Location(-1,-1);


        for(BrandInfo element:brandInfoList)
        {
            mappedBrandInfo.add(new com.shopons.domain.BrandInfo(element.getPerson_type()));
        }

        return new Store(storeInfo.getId(),storeInfo.getName(),storeInfo.getAddress(),
                storeInfo.getCity(),storeInfo.getContact(),storeInfo.getRating(),
                storeInfo.getThumbnail(),mappedBrandInfo,location,storeInfo.getReviews(),mappedPhoneNumber);
    }

}
