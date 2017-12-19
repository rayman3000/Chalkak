package bulgogi1216.gmail.photogenic.model;

import java.util.UUID;

/**
 * Created by bulgo on 2017-10-29.
 */

public class User {
    private UUID mUUID;
    private String mProfileImgUrl;
    private String mEmail;
    private String mPasswd;
    private String mName;

    public UUID getUUID() {
        return mUUID;
    }

    public String getProfileImgUrl() {
        return mProfileImgUrl;
    }

    public void setProfileImgUrl(String _profileImgUrl) {
        mProfileImgUrl = _profileImgUrl;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String _email) {
        mEmail = _email;
    }

    public String getPasswd() {
        return mPasswd;
    }

    public void setPasswd(String _passwd) {
        mPasswd = _passwd;
    }

    public String getName() {
        return mName;
    }

    public void setName(String _name) {
        mName = _name;
    }
}
