package com.shopons.data.utils;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by deepali on 3/3/16.
 */
public class RealmFactory {
    public static void Init(final Context context) {
        Realm.setDefaultConfiguration(new RealmConfiguration.Builder(context).build());
    }
}
