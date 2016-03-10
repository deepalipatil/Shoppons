package com.shopons.data.deserializer;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.shopons.data.entities.BrandInfo;
import com.shopons.data.entities.StoreInfo;


import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by komal on 2/3/16.
 */
public class StoreDeserializer implements JsonDeserializer<StoreInfo> {
    @Override
    public StoreInfo deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        StoreInfo store= new StoreInfo();
        JsonObject jsonObject=json.getAsJsonObject();

        store.setId(jsonObject.get("id").getAsString());
        store.setName(jsonObject.get("name").getAsString());
        store.setAddress(jsonObject.get("address").getAsString());
        store.setCity(jsonObject.get("city").getAsString());
        store.setRating(jsonObject.get("comp_rating").getAsDouble());
        store.setContact(jsonObject.get("contact").getAsString());
        store.setThumbnail(jsonObject.get("thumbnail").getAsString());

        JsonArray brand_info=jsonObject.get("brand_info").getAsJsonArray();
        List<BrandInfo> brandInfoList=new ArrayList<>();

        for(JsonElement element:brand_info)
        {
            brandInfoList.add(new Gson().fromJson(element,BrandInfo.class));
        }

        store.setBrand_info(brandInfoList);
        return store;



    }
}
