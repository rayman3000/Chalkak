package bulgogi1216.gmail.photogenic.communication.Member;

import android.net.Uri;

import java.io.UnsupportedEncodingException;

import bulgogi1216.gmail.photogenic.communication.PUTMethod;
import bulgogi1216.gmail.photogenic.communication.RestfulMethod;
import bulgogi1216.gmail.photogenic.communication.Tool;

/**
 * Created by dbsrh on 2017-10-27.
 */

/*
    Header Parameter
    Content-Type : application/x-www-form-urlencoded
 */
public class MemberPUT extends RestfulMethod implements PUTMethod, MemberSetting{
    private String user_id;
    private String liked_photo = "";
    private String reported_photo = "";
    private String estimated_photo = "";
    private String operation = "";

    public String getOperation() {
        return operation;
    }

    public void setOperation(boolean operation) {
        this.operation = (operation)?"true":"false";
    }

    public MemberPUT(String scheme, String user_id){
        super(scheme);

        this.user_id = user_id;
        setHeader("Content-Type", "application/x-www-form-urlencoded");
    }

    @Override
    public String getW3FormEncodedData(String char_set) throws UnsupportedEncodingException {
        StringBuilder data = new StringBuilder();
        if(!liked_photo.equals(""))Tool.appendValue(data, "liked_photo", liked_photo, char_set, false);
        if(!reported_photo.equals(""))Tool.appendValue(data, "reported_photo", reported_photo, char_set, false);
        if(!estimated_photo.equals(""))Tool.appendValue(data, "estimated_photo", estimated_photo, char_set, false);
        if(!operation.equals(""))Tool.appendValue(data, "operation", operation, char_set, false);
        Tool.appendValue(data, "user_id", user_id, char_set, true);
        return data.toString();
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

    public String getLiked_photo() {
        return liked_photo;
    }

    public void setLiked_photo(String liked_photo) {
        if(this.liked_photo.equals("")){
            this.liked_photo = liked_photo;
        }else{
            this.liked_photo = ","+liked_photo;
        }

    }

    public String getReported_photo() {
        return reported_photo;
    }

    public void setReported_photo(String reported_photo){
        if(this.reported_photo.equals("")){
            this.reported_photo = reported_photo;
        }else{
            this.reported_photo = ","+reported_photo;
        }

    }
    public String getUser_id() {
        return user_id;
    }

    public String getEstimated_photo() {
        return estimated_photo;
    }

    public void setEstimated_photo(String estimated_photo) {
        if(this.estimated_photo.equals("")){
            this.estimated_photo = estimated_photo;
        }else{
            this.estimated_photo = "," + estimated_photo;
        }

    }
}
