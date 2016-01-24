package com.shopons.mapper;

import com.shopons.model.UserModel;
import com.shopons.domain.User;

/**
 * Mapper for UserModel
 *
 * @author : Kaustubh Deshmukh
 * @date : 01/10/15 : 9:05 PM
 * @email : akshay@betacraft.co
 */
public final class UserMapper {

    public UserModel transform(final User user) {
        return new UserModel(user.getId(), user.getEmailAddress(), user.getName(), user
                .getProfilePic());
    }
}
