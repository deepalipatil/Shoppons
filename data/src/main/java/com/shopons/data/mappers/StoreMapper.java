package com.shopons.data.mappers;

import android.util.Log;

import com.shopons.data.entities.AppVersionEntity;
import com.shopons.data.entities.BrandInfo;
import com.shopons.data.entities.DealsInfo;
import com.shopons.data.entities.StoreEntity;
import com.shopons.data.entities.StoreInfo;
import com.shopons.domain.AppVersion;
import com.shopons.domain.Deals;
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

        List<Deals> mappedDealsList=new ArrayList<>();

        if(storeInfo.getLocation()!=null)
             location=new Location(storeInfo.getLocation().getLat(),storeInfo.getLocation().getLng());
        else
            location=new Location(-1,-1);


        for(BrandInfo element:brandInfoList)
        {
            mappedBrandInfo.add(new com.shopons.domain.BrandInfo(element.getPerson_type(),element.getCategory()));
        }

        for(DealsInfo element:storeInfo.getDealsInfoList())
        {
            Deals obj=new com.shopons.domain.Deals();
            obj.setInfo(element.getInfo());
            mappedDealsList.add(obj);
        }
        Store store=new Store();
        store.setId(storeInfo.getId());
        store.setName(storeInfo.getName());
        store.setAddress(storeInfo.getAddress());
        store.setLocality(storeInfo.getLocality());
        store.setCity(storeInfo.getCity());
        store.setContact(storeInfo.getContact());
        store.setRating(storeInfo.getRating());
        store.setThumbnail(storeInfo.getThumbnail());
        store.setBrandInfoList(mappedBrandInfo);
        store.setLocation(location);
        store.setReviews(storeInfo.getReviews());
        store.setPhone_numbers(mappedPhoneNumber);
        store.setDeals(mappedDealsList);

        return store;

        //return new Store(storeInfo.getId(),storeInfo.getName(),storeInfo.getAddress(),storeInfo.getLocality(),
          //      storeInfo.getCity(),storeInfo.getContact(),storeInfo.getRating(),
            //    storeInfo.getThumbnail(),mappedBrandInfo,location,storeInfo.getReviews(),mappedPhoneNumber);
    }

}
