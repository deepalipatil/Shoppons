package com.shopons.data.deserializer;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.shopons.data.entities.SearchResultEntity;
import com.shopons.data.entities.StoreDetailsEntity;
import com.shopons.data.entities.StoreEntity;
import com.shopons.data.entities.StoreInfo;
import com.shopons.data.entities.UserEntity;
import com.shopons.domain.StoreDetails;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by komal on 29/3/16.
 */
public class SearchResultDeserializer implements JsonDeserializer<SearchResultEntity> {
    @Override
    public SearchResultEntity deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonElement base_obj=json.getAsJsonObject().get("Stores");
        if(base_obj.isJsonNull())
            return null;
        JsonArray stores=base_obj.getAsJsonArray();
        SearchResultEntity searchResult=new SearchResultEntity();


        for(JsonElement element:stores)
        {
            StoreDetailsEntity store=context.deserialize(element, StoreDetailsEntity.class);
            searchResult.add(store);
        }

        return searchResult;
    }
}
