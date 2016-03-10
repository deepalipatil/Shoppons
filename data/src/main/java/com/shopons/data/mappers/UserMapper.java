package com.shopons.data.mappers;

import com.shopons.data.entities.UserEntity;
import com.shopons.domain.User;

/**
 * Created by deepali on 4/3/16.
 */
public class UserMapper {

    public static UserEntity transform(final User user) {
        final UserEntity userEntity = new UserEntity();
        userEntity.setId(user.getId());
        userEntity.setName(user.getFullName());
        userEntity.setEmail(user.getEmailAddress());
        userEntity.setAuthKey(user.getAuthKey());
        userEntity.setFacebookToken(user.getFacebookToken());
        userEntity.setFacebookUserId(user.getFacebookId());
        userEntity.setIsGplus(user.isGplus());
        userEntity.setGooglePlusToken(user.getGooglePlusToken());
        userEntity.setId(user.getId());
        return userEntity;
    }


    public static User transform(final UserEntity userEntity) {
        final User user = new com.shopons.domain.User();
        user.setId(userEntity.getId());
        user.setFullName(userEntity.getName());
        user.setEmailAddress(userEntity.getEmail());
        user.setAuthKey(userEntity.getAuthKey());
        user.setFacebookToken(userEntity.getFacebookToken());
        user.setFacebookId(userEntity.getFacebookUserId());
        user.setIsGplus(userEntity.isGplus());
        user.setGooglePlusToken(userEntity.getGooglePlusToken());
        user.setEmailAddress(userEntity.getEmail());
        user.setId(userEntity.getId());
        return user;
    }
}
