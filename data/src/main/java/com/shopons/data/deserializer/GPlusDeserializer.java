package com.shopons.data.deserializer;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.shopons.data.entities.UserEntity;

import java.lang.reflect.Type;

import io.realm.RealmObject;

/**
 * Created by komal on 21/3/16.
 */
public class GPlusDeserializer implements JsonDeserializer<UserEntity> {
    @Override
    public UserEntity deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonElement base_obj=json.getAsJsonObject().get("model");

        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getDeclaringClass().equals(RealmObject.class);
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create();

        return  gson.fromJson(base_obj,UserEntity.class);
    }
}
