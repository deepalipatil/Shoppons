package co.betacraft.android.data.net;

import co.betacraft.android.domain.User;
import retrofit.http.Field;
import retrofit.http.GET;
import rx.Observable;

/**
 * Apis related with User
 *
 * @author : Akshay Deo
 * @date : 01/10/15 : 8:42 PM
 * @email : akshay@betacraft.co
 */
public interface UserApis {
    @GET("/users/login")
    Observable<User> login(@Field("email") String email, @Field("password") String password);
}
