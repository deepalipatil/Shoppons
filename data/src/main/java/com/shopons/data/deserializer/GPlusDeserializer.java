package com.shopons.data.deserializer;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.shopons.data.entities.UserEntity;

import java.lang.reflect.Type;

/**
 * Created by komal on 21/3/16.
 */
public class GPlusDeserializer implements JsonDeserializer<UserEntity> {
    @Override
    public UserEntity deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonElement base_obj=json.getAsJsonObject().get("model");
        return new Gson().fromJson(base_obj,UserEntity.class);
    }
}
