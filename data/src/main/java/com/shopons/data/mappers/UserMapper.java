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
        return  user;
    }

}
