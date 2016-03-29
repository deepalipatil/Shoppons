package com.shopons.data.realmdb;

import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.shopons.data.entities.UserEntity;
import com.shopons.domain.User;

import io.realm.Realm;

/**
 * Created by deepali on 28/3/16.
 */
public class UserInfo {
    /**
     * helper function which accept domain User model and returns UserEntity which is realm compatible
     *
     * @param user
     * @return user
     */
    public static void SaveUserInfo(final User user) {
        final Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        try {
            realm.clear(UserEntity.class);
        } catch (final Exception e) {
            Crashlytics.logException(e);
        } finally {
            realm.commitTransaction();
        }
        realm.beginTransaction();
        final UserEntity userEntity = realm.createObject(UserEntity.class);
        userEntity.setName(user.getName());
        if (user.getEmail() != null)
            userEntity.setEmail(user.getEmail());
        if (user.getAuth_key() != null)
            userEntity.setAuth_key(user.getAuth_key());
        userEntity.setFb_token(user.getFb_token());
        userEntity.setGoogle_token(user.getGoogle_token());
        userEntity.setFb_id(user.getFb_id());
        if (user.getPhoto_url() != null)
            userEntity.setPhoto_url(user.getPhoto_url());
        userEntity.setId(user.getId());
        userEntity.setis_logged_in(user.get_is_logged_in());
        userEntity.setIs_info_pushed(user.getIs_info_pushed());
        Log.d("#########Realm",userEntity.getEmail());
        realm.commitTransaction();
        realm.close();
    }


    public static void clearUserInfo() {
        final Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        try {
            realm.clear(UserEntity.class);
        } catch (final Exception e){
            Crashlytics.logException(e);
        }
        finally {
            realm.commitTransaction();
        }
        realm.close();

    }

}