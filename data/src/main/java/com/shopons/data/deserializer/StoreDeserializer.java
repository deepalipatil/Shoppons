package com.shopons.data.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.shopons.data.entities.StoreInfo;

import java.lang.reflect.Type;

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
        return store;



    }
}
