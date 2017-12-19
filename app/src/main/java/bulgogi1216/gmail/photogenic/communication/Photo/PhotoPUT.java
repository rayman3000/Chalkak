package bulgogi1216.gmail.photogenic.communication.Photo;

import android.net.Uri;

import java.io.UnsupportedEncodingException;

import bulgogi1216.gmail.photogenic.communication.PUTMethod;
import bulgogi1216.gmail.photogenic.communication.RestfulMethod;
import bulgogi1216.gmail.photogenic.communication.Tool;

/**
 * Created by dbsrh on 2017-10-26.
 */

/*
    Header Parameter
    Content-Type : application/x-www-form-urlencoded
 */
public class PhotoPUT extends RestfulMethod implements PUTMethod, PhotoSetting{
    private String _id;

    //-1 : 사용 안함 , 0 : false, 1 : true
    private int raters = -1;
    private int likes = -1;

    //-1 : 사용 안함
    private int atmosphere = -1;
    private int background = -1;
    private int person = -1;
    private int creativity = -1;


    public PhotoPUT(String scheme, String id){
        super(scheme);
        this._id = id;
        setHeader("Content-Type", "application/x-www-form-urlencoded");

    }

    public String get_id() {
        return _id;
    }

    public int getRaters() {
        return raters;
    }

    public void setRaters(Boolean raters) {
        this.raters = (raters)?1:0;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(Boolean likes) {
        this.likes = (likes)?1:0;
    }

    public int getAtmosphere() {
        return atmosphere;
    }

    public void setAtmosphere(int atmosphere) {
        this.atmosphere = atmosphere;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public int getPerson() {
        return person;
    }

    public void setPerson(int person) {
        this.person = person;
    }

    public int getCreativity() {
        return creativity;
    }

    public void setCreativity(int creativity) {
        this.creativity = creativity;
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
        if(atmosphere != -1)Tool.appendValue(data, "atmosphere", String.valueOf(atmosphere), char_set, false);
        if(person != -1) Tool.appendValue(data, "person", String.valueOf(person), char_set, false);
        if(creativity != -1)Tool.appendValue(data, "creativity", String.valueOf(creativity), char_set, false);
        if(background != -1)Tool.appendValue(data, "background", String.valueOf(background), char_set, false);
        if(raters != -1)Tool.appendValue(data, "raters", (raters==1)?"true":"false", char_set, false);
        if(likes != -1)Tool.appendValue(data, "likes", (likes==1)?"true":"false", char_set, false);
        Tool.appendValue(data, "_id", _id, char_set, true);
        return data.toString();
    }


}
