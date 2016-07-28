package com.shopons.mapper;

import com.shopons.model.UserModel;
import com.shopons.domain.User;

public final class UserMapper {

    public static UserModel transform(final User user) {
        return new UserModel(user.getAuth_key(), user.getName(), user.getEmail(), user.getId());
    }

    public static User transform(final UserModel userModel){
        return new User(userModel.getAuthKey(),userModel.getFullName(), userModel.getEmailAddress(), userModel.getId());
    }
}
