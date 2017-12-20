package bulgogi1216.gmail.photogenic.Sungmin;

/**
 * Created by Sungmin on 2017-12-21.
 */

public class FeedBean {
    private String mProfilePhoto;
    private String mNickName;
    private String mPlace;
    private String mDescription;
    private String mPhoto;

    public FeedBean(){}

    public FeedBean(String mProfilePhoto, String mNickName, String mPlace, String mDescription, String mPhoto) {
        this.mProfilePhoto = mProfilePhoto;
        this.mNickName = mNickName;
        this.mPlace = mPlace;
        this.mDescription = mDescription;
        this.mPhoto = mPhoto;
    }

    public String getmProfilePhoto() {
        return mProfilePhoto;
    }

    public void setmProfilePhoto(String mProfilePhoto) {
        this.mProfilePhoto = mProfilePhoto;
    }

    public String getmNickName() {
        return mNickName;
    }

    public void setmNickName(String mNickName) {
        this.mNickName = mNickName;
    }

    public String getmPlace() {
        return mPlace;
    }

    public void setmPlace(String mPlace) {
        this.mPlace = mPlace;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmPhoto() {
        return mPhoto;
    }

    public void setmPhoto(String mPhoto) {
        this.mPhoto = mPhoto;
    }
}
