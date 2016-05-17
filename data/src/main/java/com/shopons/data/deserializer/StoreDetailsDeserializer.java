package com.shopons.data.deserializer;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.shopons.data.entities.BrandInfo;
import com.shopons.data.entities.DealsInfo;
import com.shopons.data.entities.LocationEntity;
import com.shopons.data.entities.PhoneNumberEntity;
import com.shopons.data.entities.StoreDetailsEntity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by komal on 20/3/16.
 */
public class StoreDetailsDeserializer implements JsonDeserializer<StoreDetailsEntity> {
    @Override
    public StoreDetailsEntity deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject baseObj=json.getAsJsonObject().get("model").getAsJsonObject();
        StoreDetailsEntity store=new StoreDetailsEntity();

        store.setId(baseObj.get("id").getAsString());
        store.setName(baseObj.get("name").getAsString());
        store.setAddress(baseObj.get("address").getAsString());
        store.setCity(baseObj.get("city").getAsString());
        store.setRating(baseObj.get("comp_rating").getAsDouble());
        store.setContact(baseObj.get("contact").getAsString());
        store.setThumbnail(baseObj.get("thumbnail").getAsString());

        JsonElement location=baseObj.get("location");
        store.setLocation(new Gson().fromJson(location, LocationEntity.class));

        JsonElement phone_numbers=baseObj.get("phone_numbers");
        store.setPhone_numbers(new Gson().fromJson(phone_numbers, PhoneNumberEntity.class));


        JsonArray arry_json=baseObj.get("brand_info").getAsJsonArray();
        List<BrandInfo> brandInfoList=new ArrayList<>();

        for(JsonElement element:arry_json)
        {
            brandInfoList.add(new Gson().fromJson(element,BrandInfo.class));
        }

        store.setBrandInfoList(brandInfoList);

        arry_json=baseObj.get("deals").getAsJsonArray();
        List<DealsInfo> dealsInfoList=new ArrayList<>();

        for(JsonElement element:arry_json)
        {
            dealsInfoList.add(new Gson().fromJson(element,DealsInfo.class));
        }

        store.setDealsInfoList(dealsInfoList);

        return store;

        //return new Gson().fromJson(baseObj,StoreDetailsEntity.class);
    }
}
