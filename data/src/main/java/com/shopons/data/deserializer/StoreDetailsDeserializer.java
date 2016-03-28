package com.shopons.data.deserializer;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.shopons.data.entities.StoreDetailsEntity;

import java.lang.reflect.Type;

/**
 * Created by komal on 20/3/16.
 */
public class StoreDetailsDeserializer implements JsonDeserializer<StoreDetailsEntity> {
    @Override
    public StoreDetailsEntity deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonElement baseObj=json.getAsJsonObject().get("model");
        return new Gson().fromJson(baseObj,StoreDetailsEntity.class);
    }
}
