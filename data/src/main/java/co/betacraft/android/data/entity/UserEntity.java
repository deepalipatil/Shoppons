package co.betacraft.android.data.entity;

import io.realm.RealmObject;

/**
 * User data entity. This handles caching by extending the class using Realm
 *
 * @author : Akshay Deo
 * @date : 01/10/15 : 8:36 PM
 * @email : akshay@betacraft.co
 */
public final class UserEntity extends RealmObject {

    private long mId;
    private String mName;
    private String mEmail;
    private String mPassword;
    private String mPhoneNumber;
    private String mProfilePic;

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    public String getProfilePic() {
        return mProfilePic;
    }

    public void setProfilePic(String profilePic) {
        mProfilePic = profilePic;
    }

    public UserEntity() {
    }

    public UserEntity(long id, String name, String email, String password, String profilePic) {
        mId = id;
        mName = name;
        mEmail = email;
        mPassword = password;
        mProfilePic = profilePic;
    }

    public String getName() {
        return mName;
    }
}
