package co.betacraft.android.data.mapper;

import co.betacraft.android.data.entity.UserEntity;
import co.betacraft.android.domain.User;

/**
 * This mapper maps UserEntity to User.
 *
 * @author : Akshay Deo
 * @date : 01/10/15 : 8:59 PM
 * @email : akshay@betacraft.co
 */
public final class UserMapper {

    public User transform(final UserEntity userEntity) {
        return new User(userEntity.getId(), userEntity.getName(), userEntity.getEmail(),
                userEntity.getProfilePic());
    }
}
