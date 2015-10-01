package co.betacraft.android.app.mapper;

import co.betacraft.android.app.model.UserModel;
import co.betacraft.android.domain.User;

/**
 * Mapper for UserModel
 *
 * @author : Akshay Deo
 * @date : 01/10/15 : 9:05 PM
 * @email : akshay@betacraft.co
 */
public final class UserMapper {

    public UserModel transform(final User user) {
        return new UserModel(user.getId(), user.getEmailAddress(), user.getName(), user
                .getProfilePic());
    }
}
