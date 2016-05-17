package com.shopons.data.mappers;


import com.shopons.data.entities.DealsInfo;
import com.shopons.data.entities.StoreDetailsEntity;
import com.shopons.domain.BrandInfo;
import com.shopons.domain.Deals;
import com.shopons.domain.Location;
import com.shopons.domain.PhoneNumber;
import com.shopons.domain.StoreDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by komal on 20/3/16.
 */
public class StoreDetailsMapper {
    public static StoreDetails transform(StoreDetailsEntity storeDetailsEntity)
    {
        List<BrandInfo> mappedBrandInfo=new ArrayList<>();
        List<Deals> mappedDealsList=new ArrayList<>();
        Location location;


        if(storeDetailsEntity.getLocation()!=null)
            location=new Location(storeDetailsEntity.getLocation().getLat(),storeDetailsEntity.getLocation().getLng());
        else
            location=new Location(-1,-1);

        PhoneNumber mappedPhoneNumber=new PhoneNumber(storeDetailsEntity.getPhone_numbers().getTelephone(),
                storeDetailsEntity.getPhone_numbers().getMobile(),storeDetailsEntity.getPhone_numbers().getLandline());

        for(com.shopons.data.entities.BrandInfo element:storeDetailsEntity.getBrandInfoList())
        {
            mappedBrandInfo.add(new BrandInfo(element.getPerson_type(),element.getCategory()));
        }


        for(DealsInfo element:storeDetailsEntity.getDealsInfoList())
        {
            Deals obj=new com.shopons.domain.Deals();
            obj.setInfo(element.getInfo());
            mappedDealsList.add(obj);
        }

        StoreDetails storeDetails=new StoreDetails();

        storeDetails.setId(storeDetailsEntity.getId());
        storeDetails.setName(storeDetailsEntity.getName());
        storeDetails.setAddress(storeDetailsEntity.getAddress());
        storeDetails.setCity(storeDetailsEntity.getCity());
        storeDetails.setContact(storeDetailsEntity.getContact());
        storeDetails.setRating(storeDetailsEntity.getRating());
        storeDetails.setThumbnail(storeDetailsEntity.getThumbnail());
        storeDetails.setBrandInfoList(mappedBrandInfo);
        storeDetails.setLocality(storeDetailsEntity.getLocality());
        storeDetails.setLocation(location);
        storeDetails.setReviews(storeDetailsEntity.getReviews());
        storeDetails.setPhone_numbers(mappedPhoneNumber);
        storeDetails.setDealsInfoList(mappedDealsList);
        return storeDetails;


    }
}
