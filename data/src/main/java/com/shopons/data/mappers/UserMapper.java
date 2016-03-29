package com.shopons.data.mappers;

import com.shopons.data.entities.UserEntity;
import com.shopons.domain.User;

/**
 * Created by deepali on 4/3/16.
 */
public class UserMapper {
    public static User transform(UserEntity userEntity)
    {
        User user= new User();
        user.setId(userEntity.getId());
        user.setName(userEntity.getName());
        user.setAuth_key(userEntity.getAuth_key());
        user.setMobile_number(userEntity.getMobile_number());
        user.setEmail(userEntity.getEmail());
        user.setPhoto_url(userEntity.getPhoto_url());
        user.setFb_token(userEntity.getFb_token());
        user.setFb_id(userEntity.getFb_id());
        user.setGoogle_token(userEntity.getGoogle_token());
        user.setIs_info_pushed(userEntity.getIs_info_pushed());
        user.set_is_logged_in(userEntity.getis_logged_in());
        return  user;
    }

    public static UserEntity transform(final User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId((user.getId()));
        userEntity.setName(user.getName());
        userEntity.setAuth_key(user.getAuth_key());
        userEntity.setIs_info_pushed(user.getIs_info_pushed());
        userEntity.setEmail(user.getEmail());
        userEntity.setPhoto_url(user.getPhoto_url());
        userEntity.setFb_token(user.getFb_token());
        userEntity.setGoogle_token(user.getGoogle_token());
        userEntity.setis_logged_in(user.get_is_logged_in());
        return userEntity;
    }

}
