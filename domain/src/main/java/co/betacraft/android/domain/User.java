package co.betacraft.android.domain;

/**
 * User model
 *
 * @author : Akshay Deo
 * @date : 01/10/15 : 3:42 PM
 * @email : akshay@betacraft.co
 */
public class User {
    private long mId;
    private String mName;
    private String mEmailAddress;
    private String mProfilePic;

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getEmailAddress() {
        return mEmailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        mEmailAddress = emailAddress;
    }

    public String getProfilePic() {
        return mProfilePic;
    }

    public void setProfilePic(String profilePic) {
        mProfilePic = profilePic;
    }

    public User(long id, String name, String emailAddress, String profilePic) {
        mId = id;
        mName = name;
        mEmailAddress = emailAddress;
        mProfilePic = profilePic;
    }
}
