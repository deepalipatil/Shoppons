package com.shopons.data.deserializer;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.shopons.data.entities.StoreEntity;
import com.shopons.data.entities.StoreInfo;

import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * Created by komal on 2/3/16.
 */
public class StoreEntityDeserializer implements JsonDeserializer<StoreEntity> {
    @Override
    public StoreEntity deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonElement base_obj=json.getAsJsonObject().get("list");
        JsonArray stores=base_obj.getAsJsonArray();
        StoreEntity storeEntity=new StoreEntity();


        for(JsonElement element:stores)
        {
            StoreInfo store=context.deserialize(element, StoreInfo.class);
            storeEntity.add(store);
        }

        return storeEntity;
    }
}
