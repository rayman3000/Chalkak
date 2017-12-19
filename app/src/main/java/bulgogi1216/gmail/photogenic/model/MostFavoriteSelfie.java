package bulgogi1216.gmail.photogenic.model;

import android.graphics.drawable.Drawable;

import java.util.UUID;

public class MostFavoriteSelfie {
    private UUID mUUID;
    private int mImage;
    private Drawable mImageDrw;
    private String mName;
    private String mBrief;
    private Integer mCounter = null;

    public UUID getUUID() {
        return mUUID;
    }

    public int getImage() {
        return mImage;
    }

    public void setImage(int _image) {
        mImage = _image;
    }

    public Drawable getImageDrw() {
        return mImageDrw;
    }

    public void setImageDrw(Drawable _imageDrw) {
        mImageDrw = _imageDrw;
    }

    public String getName() {
        return mName;
    }

    public void setName(String _name) {
        mName = _name;
    }

    public String getBrief() {
        return mBrief;
    }

    public void setBrief(String _brief) {
        mBrief = _brief;
    }

    public Integer getCounter() {
        return mCounter;
    }

    public void setCounter(Integer _counter) {
        mCounter = _counter;
    }
}
