package bulgogi1216.gmail.photogenic.model;

import android.graphics.drawable.Drawable;

import java.util.UUID;

public class HomeMenuCategory {
    private UUID mUUID;
    private String mTitle;
    private Drawable mImageBg;

    public HomeMenuCategory(Drawable _imageBg, String _title) {
        mImageBg = _imageBg;
        mTitle = _title;
        mUUID = UUID.randomUUID();
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String _title) {
        mTitle = _title;

    }

    public Drawable getImageBg() {
        return mImageBg;
    }

    public void setImageBg(Drawable _imageBg) {
        mImageBg = _imageBg;
    }

    public UUID getUUID() {
        return mUUID;
    }
}
