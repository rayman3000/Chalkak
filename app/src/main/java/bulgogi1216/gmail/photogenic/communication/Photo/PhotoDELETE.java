package bulgogi1216.gmail.photogenic.communication.Photo;

import android.net.Uri;

import java.io.UnsupportedEncodingException;

import bulgogi1216.gmail.photogenic.communication.DELETEMethod;
import bulgogi1216.gmail.photogenic.communication.RestfulMethod;
import bulgogi1216.gmail.photogenic.communication.Tool;

/**
 * Created by dbsrh on 2017-10-26.
 */

/*
    Header Parameter
    Content-Type : application/x-www-form-urlencoded
 */
public class PhotoDELETE extends RestfulMethod implements DELETEMethod, PhotoSetting {
    private String _id;


    public PhotoDELETE(String scheme, String _id){
        super(scheme);
        this._id = _id;
        setHeader("Content-Type", "application/x-www-form-urlencoded");
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    @Override
    public String buildUri(String end_point, String... paths) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(scheme)
                .encodedAuthority(end_point);
        for(String path : paths){
            builder.appendPath(path);
        }
        return builder.build().toString();
    }

    @Override
    public String[] getPath() {
        return path;
    }

    @Override
    public String getW3FormEncodedData(String char_set) throws UnsupportedEncodingException {
        StringBuilder data = new StringBuilder();
        Tool.appendValue(data, "_id", _id, char_set, true);
        return data.toString();
    }

}
